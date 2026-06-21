# DevOps SandBox Backend — Documentación técnica

API REST para gestionar guías metodológicas (methodologies), fases, pasos (subphases), recursos multimedia y autenticación de administradores. El frontend Next.js consume estos endpoints bajo el prefijo `/api`.

---

## Tabla de contenidos

1. [Visión general](#1-visión-general)
2. [Stack tecnológico](#2-stack-tecnológico)
3. [Estructura del proyecto](#3-estructura-del-proyecto)
4. [Arquitectura en capas](#4-arquitectura-en-capas)
5. [Modelo de datos](#5-modelo-de-datos)
6. [Seguridad y autenticación](#6-seguridad-y-autenticación)
7. [Reglas de negocio](#7-reglas-de-negocio)
8. [API REST (resumen)](#8-api-rest-resumen)
9. [Manejo de errores](#9-manejo-de-errores)
10. [Configuración](#10-configuración)
11. [Despliegue](#11-despliegue)
12. [Pruebas](#12-pruebas)
13. [Referencia de métodos](#13-referencia-de-métodos)

---

## 1. Visión general

El backend expone un CRUD completo sobre el contenido educativo de las guías DevOps:

- **Lectura pública:** cualquier cliente puede hacer `GET` sobre `/api/**` (guías, fases, pasos, recursos).
- **Escritura protegida:** crear, editar, borrar y reordenar requiere JWT de administrador (`ROLE_ADMIN`).
- **Persistencia:** PostgreSQL (Neon en producción; H2 en perfil `test` para Newman).
- **Autenticación admin:** exclusivamente contra la tabla `users` (BCrypt o texto plano legacy).

```text
Cliente (Next.js / Postman)
        │
        ▼ HTTP /api/*
┌───────────────────┐
│  Controllers      │  ← validación HTTP, DTOs, códigos de estado
├───────────────────┤
│  Services         │  ← lógica de negocio, transacciones, ordenamiento
├───────────────────┤
│  Repositories     │  ← Spring Data JPA
├───────────────────┤
│  PostgreSQL       │
└───────────────────┘
```

---

## 2. Stack tecnológico

| Componente | Tecnología |
|------------|------------|
| Runtime | Java 17 |
| Framework | Spring Boot 3.5.7 |
| Persistencia | Spring Data JPA + Hibernate 6 |
| Base de datos | PostgreSQL (prod), H2 (tests Newman) |
| Seguridad | Spring Security + JWT (jjwt 0.12) |
| Validación | Jakarta Validation |
| API docs | SpringDoc OpenAPI 2.6 |
| Build | Gradle 8.x |
| Contenedor | Docker (Render) |
| Pruebas E2E | Newman + JaCoCo |

---

## 3. Estructura del proyecto

```text
src/main/java/com/icesi/devopssandboxbackend/
├── DevopsSandboxBackendApplication.java   # Punto de entrada Spring Boot
├── config/          # Security, CORS, OpenAPI, propiedades JWT
├── controller/      # REST endpoints (7 controladores)
├── dto/             # Objetos de transferencia JSON
├── domain/
│   ├── model/       # Entidades JPA
│   ├── repository/  # Interfaces Spring Data
│   └── enums/       # MethodologyStatus, ResourceType
├── exception/       # ApiError, GlobalExceptionHandler
├── mapper/          # Conversión Entity ↔ DTO (estáticos)
├── security/        # JWT filter, handlers, JwtService
└── service/         # Interfaces + AdminAuthService
    └── impl/        # Implementaciones de servicios

src/main/resources/
├── application.properties      # Config por defecto (desarrollo)
├── application-test.properties # Perfil Newman (H2)
└── test-data/                  # schema.sql + data.sql para H2

newman/                         # Colección Postman + scripts de prueba
docs/                           # Esta documentación
Dockerfile                      # Imagen multi-stage para producción
```

---

## 4. Arquitectura en capas

### 4.1 Controllers

Reciben HTTP, delegan en servicios y devuelven DTOs. No contienen lógica de persistencia directa (salvo validaciones simples de FK en create).

- Usan **mappers estáticos** para convertir entidades ↔ DTOs.
- Las relaciones padre (`Methodology` en `Phase`, `Phase` en `Subphase`, etc.) se resuelven en el controller o service antes de `save`.

### 4.2 Services (`*ServiceImpl`)

Contienen la lógica de negocio:

- Asignación automática de `orderIndex` al crear fases/pasos.
- Reordenamiento atómico en dos pasadas (evita violar unique constraints).
- Borrado en cascada manual (fase → subfases → enlaces recurso).
- Timestamps UTC en create/update.

### 4.3 Repositories

Interfaces `JpaRepository` con consultas derivadas (`findByMethodologyId`, `findByPhaseId`, etc.).

### 4.4 Mappers

Clases con métodos `toDTO` / `toEntity` sin inyección de dependencias. Los FK (`methodologyId`, `phaseId`) en DTOs se mapean desde/asociaciones `@ManyToOne` en `toDTO`; en `toEntity` las relaciones se establecen en controller/service.

---

## 5. Modelo de datos

### 5.1 Diagrama de relaciones

```text
methodologies (1) ──< phases (1) ──< subphases
                                        │
                                        │ subphase_resources (N:M con order_index)
                                        ▼
                                   resources

users  (independiente — solo autenticación admin)
```

### 5.2 Tablas principales

| Tabla | Descripción |
|-------|-------------|
| `methodologies` | Guía (slug, nombre, intro, estado, versión, idioma) |
| `phases` | Fase de una guía; `order_index` único por metodología |
| `subphases` | Paso dentro de una fase; contenido rich-text |
| `resources` | Video, PDF, imagen, etc.; URL única; metadata JSONB |
| `subphase_resources` | Enlace subfase↔recurso con `order_index` y nota |
| `users` | Credenciales admin (`username`, `password`) |

### 5.3 Enums

**`MethodologyStatus`:** `draft`, `published`, `archived`

**`ResourceType`:** `video`, `image`, `reading`, `article`, `book`, `tool`, `other`

### 5.4 Claves e índices relevantes

- `phases`: UNIQUE `(methodology_id, order_index)`
- `subphases`: UNIQUE `(phase_id, order_index)`
- `subphase_resources`: PK compuesta `(subphase_id, resource_id)`; UNIQUE `(subphase_id, order_index)`
- `resources`: UNIQUE `url`

---

## 6. Seguridad y autenticación

### 6.1 Flujo de login

```text
POST /api/auth/login  { username, password }
        │
        ▼
AdminAuthService.matches()  → consulta tabla users
        │
        ▼ (OK)
JwtService.generateAccessToken(username)
        │
        ▼
{ accessToken, tokenType: "Bearer", expiresIn, role: "ADMIN" }
```

### 6.2 Flujo de peticiones autenticadas

```text
Request + Header: Authorization: Bearer <token>
        │
        ▼
JwtAuthenticationFilter
        │ parsea JWT, valida firma y expiración
        ▼
SecurityContext (username + ROLE_ADMIN)
        │
        ▼
Controller / @PreAuthorize implícito vía SecurityFilterChain
```

### 6.3 Matriz de permisos (`SecurityConfig`)

| Ruta | Método | Acceso |
|------|--------|--------|
| `/api/auth/login` | POST | Público |
| `/api/auth/**` | GET (p. ej. `/me`) | JWT requerido |
| `/api/health/**` | GET | Público |
| `/api/**` | GET | Público |
| `/api/**` | POST, PUT, DELETE | `ROLE_ADMIN` |
| `/api/docs/**`, `/actuator/**` | * | Público |
| `OPTIONS /**` | * | Público (CORS preflight) |

### 6.4 JWT — requisitos del secret

`APP_SECURITY_JWT_SECRET` debe tener **≥ 256 bits (32 caracteres UTF-8)** para HMAC-SHA256. Secretos cortos (p. ej. 14 caracteres) provocan error al firmar tokens.

Formato alternativo: prefijo `base64:` + clave codificada en Base64.

### 6.5 Almacenamiento de contraseñas admin

`AdminAuthService` consulta **solo** la tabla `users`:

- Hash BCrypt (`$2a$`, `$2b$`, `$2y$`) → `PasswordEncoder.matches`
- Texto plano legacy → comparación constant-time (`MessageDigest.isEqual`)

No hay fallback a variables de entorno ni properties para credenciales.

---

## 7. Reglas de negocio

### 7.1 Orden de fases y pasos

- **Create:** el cliente **no** envía `orderIndex`. El backend asigna `max(order_index) + 1` dentro del padre (metodología o fase).
- **Update:** el formulario de edición **no** modifica `orderIndex`.
- **Reorder:** único mecanismo para cambiar orden → `POST /api/phases/reorder` o `POST /api/subphases/reorder` con lista completa de IDs ordenados.

**Algoritmo de reorder (dos pasadas):**

1. Asignar índices temporales altos (1000, 1001, …) y `flush`.
2. Asignar índices finales (0, 1, 2, …) y `flush`.

Evita colisiones en constraints UNIQUE durante updates concurrentes.

### 7.2 Borrado en cascada

| Operación | Efecto |
|-----------|--------|
| `DELETE /api/phases/{id}` | Borra todas las subfases de la fase (y sus enlaces recurso) y luego la fase |
| `DELETE /api/subphases/{id}` | Borra enlaces `subphase_resources` y luego la subfase |
| `DELETE /api/methodologies/{id}` | Solo la metodología (FK en DB pueden restringir si hay fases) |

### 7.3 Recursos

- `metadata` se almacena como JSON serializado en columna `jsonb` (Postgres).
- En create, si `metadata` viene vacío → default `"{}"`.
- `url` debe ser única en toda la tabla.

### 7.4 Metodologías

- Create: defaults `status=draft`, `version=1`, `languageCode=es`, timestamps UTC.
- Update: merge parcial de campos enviados.

---

## 8. API REST (resumen)

Base URL: `http://localhost:8080` (dev) o URL de Render (prod).

| Prefijo | Recurso |
|---------|---------|
| `/api/health` | Health check |
| `/api/auth` | Login y perfil admin |
| `/api/methodologies` | Guías |
| `/api/phases` | Fases |
| `/api/subphases` | Pasos |
| `/api/resources` | Recursos globales |
| `/api/subphase-resources` | Enlaces paso↔recurso |

Descripción detallada de **cada endpoint y método de servicio** → [REFERENCIA-METODOS.md](./REFERENCIA-METODOS.md).

### Ejemplo: obtener una guía completa (frontend)

El frontend compone la guía con varias llamadas GET públicas:

1. `GET /api/methodologies/{id}`
2. `GET /api/phases/methodology/{id}`
3. Por cada fase: `GET /api/subphases/phase/{phaseId}`
4. Por cada paso: `GET /api/subphase-resources/subphase/{subphaseId}`
5. Por cada enlace: `GET /api/resources/{resourceId}`

---

## 9. Manejo de errores

`GlobalExceptionHandler` (`@RestControllerAdvice`) unifica respuestas en JSON `ApiError`:

```json
{
  "timestamp": "2026-06-11T01:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "methodologyId es obligatorio.",
  "details": null
}
```

| Excepción | HTTP |
|-----------|------|
| `MethodArgumentNotValidException` | 400 (lista en `details`) |
| `IllegalArgumentException` | 400 |
| `ResourceNotFoundException` | 404 |
| `ResponseStatusException` | según status (p. ej. 401 login) |
| Cualquier otra `Exception` | 500 |

**401 / 403 de Spring Security** (sin JWT o sin rol) los manejan `JwtAuthenticationEntryPoint` y `JwtAccessDeniedHandler` con JSON propio.

---

## 10. Configuración

### 10.1 Propiedades principales

| Propiedad | Descripción | Default local |
|-----------|-------------|---------------|
| `server.port` | Puerto HTTP | `8080` |
| `spring.datasource.*` | Conexión PostgreSQL | Neon en `application.properties` |
| `spring.jpa.hibernate.ddl-auto` | Schema auto-update | `update` |
| `app.security.jwt.secret` | Clave firma JWT | ver properties |
| `app.security.jwt.expiration-ms` | Vida del token (ms) | `86400000` (24 h) |
| `app.security.cors.allowed-origins` | Orígenes CORS (coma) | `http://localhost:3000` |

### 10.2 Variables de entorno (producción / Render)

Spring Boot mapea automáticamente:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://...
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
APP_SECURITY_JWT_SECRET=<mínimo 32 caracteres>
APP_SECURITY_JWT_EXPIRATION_MS=86400000
APP_SECURITY_CORS_ALLOWED_ORIGINS=https://tu-app.vercel.app
SPRING_JPA_SHOW_SQL=false
```

El contenedor Docker usa `PORT` de Render: `java -Dserver.port=${PORT} -jar app.jar`.

### 10.3 Perfil `test`

Activado por Newman (`-Dspring.profiles.active=test`):

- H2 in-memory, schema/data en `classpath:test-data/`.
- `/actuator/shutdown` habilitado para apagado limpio post-pruebas.

---

## 11. Despliegue

### 11.1 Local

```bash
./gradlew bootRun
```

### 11.2 Docker + Render

1. Build multi-stage en `Dockerfile` (JDK 17 → JAR → JRE 17).
2. Render: **Web Service**, runtime **Docker**, sin build/start command manual.
3. Variables de entorno (Neon + JWT + CORS).
4. Plan free: spin-down tras **15 min** sin tráfico; cold start ~1 min.

Ver también despliegue frontend: Vercel con proxy:

```env
NEXT_PUBLIC_API_BASE_URL=/api
NEXT_PUBLIC_API_PROXY_TARGET=https://tu-backend.onrender.com
```

---

## 12. Pruebas

### 12.1 Newman (E2E + coverage)

```powershell
.\newman\run-newman-tests.ps1
```

- Arranca app con JaCoCo agent + perfil `test`.
- Ejecuta colección Postman (62 requests, auth, CRUD, reorder, errores).
- Genera reporte: `build/reports/jacoco/jacocoNewmanReport/html/index.html`.
- Umbral: ≥ 90 % líneas.

Documentación detallada: [newman/README.md](../newman/README.md).

### 12.2 Tests unitarios Gradle

```bash
./gradlew test
```

*(Suite JUnit mínima; la cobertura principal del proyecto está en Newman.)*

---

## 13. Referencia de métodos

La lista completa con descripción breve de cada método público está en:

**→ [REFERENCIA-METODOS.md](./REFERENCIA-METODOS.md)**

Incluye: controladores, servicios, seguridad, configuración, mappers, excepciones y aplicación principal.

---

## OpenAPI / Swagger

Con el servidor en marcha:

- JSON: `/api/docs`
- UI: `/api/docs/ui`

Útil para explorar schemas de DTOs e probar endpoints desde el navegador.
