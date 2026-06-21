# Referencia de métodos — DevOps SandBox Backend

Descripción breve de cada método público del paquete `com.icesi.devopssandboxbackend`.

**Leyenda de auth:** 🌐 público · 🔐 JWT · 🔒 ADMIN

---

## Aplicación

### `DevopsSandboxBackendApplication`

| Método | Descripción |
|--------|-------------|
| `main(String[] args)` | Inicia el contexto Spring Boot y levanta el servidor embebido Tomcat. |

---

## Controladores REST

### `AuthController` — `/api/auth`

| Método | HTTP | Auth | Descripción |
|--------|------|------|-------------|
| `login(LoginRequest body)` | POST `/login` | 🌐 | Valida usuario/contraseña contra la BD; devuelve JWT Bearer y tiempo de expiración. |
| `me(Principal principal)` | GET `/me` | 🔐 | Devuelve username y rol del admin autenticado por el token. |

---

### `HealthController` — `/api/health`

| Método | HTTP | Auth | Descripción |
|--------|------|------|-------------|
| `health()` | GET `/` | 🌐 | Comprueba que la aplicación está viva; responde `{"status":"UP"}`. |

---

### `MethodologyController` — `/api/methodologies`

| Método | HTTP | Auth | Descripción |
|--------|------|------|-------------|
| `getAll()` | GET `/` | 🌐 | Lista todas las metodologías (guías). |
| `getById(UUID id)` | GET `/{id}` | 🌐 | Obtiene una metodología por UUID. |
| `create(MethodologyDTO dto)` | POST `/` | 🔒 | Crea una metodología nueva. |
| `update(UUID id, MethodologyDTO dto)` | PUT `/{id}` | 🔒 | Actualiza slug, nombre, intro, summary, estado, versión e idioma. |
| `delete(UUID id)` | DELETE `/{id}` | 🔒 | Elimina la metodología por ID. |

---

### `PhaseController` — `/api/phases`

| Método | HTTP | Auth | Descripción |
|--------|------|------|-------------|
| `getAll()` | GET `/` | 🌐 | Lista todas las fases de todas las guías. |
| `getById(UUID id)` | GET `/{id}` | 🌐 | Obtiene una fase por UUID. |
| `getByMethodology(UUID methodologyId)` | GET `/methodology/{methodologyId}` | 🌐 | Lista fases de una metodología, ordenadas por servicio/repo. |
| `create(PhaseDTO dto)` | POST `/` | 🔒 | Crea fase; exige `methodologyId` válido; asigna `orderIndex` al final. |
| `update(UUID id, PhaseDTO dto)` | PUT `/{id}` | 🔒 | Actualiza título, código, descripción; no cambia orden. |
| `delete(UUID id)` | DELETE `/{id}` | 🔒 | Borra la fase y todas sus subfases (cascada). |
| `reorder(ReorderRequest body)` | POST `/reorder` | 🔒 | Reordena fases de una metodología según `orderedIds`; devuelve lista actualizada. |

**`PhaseController.ReorderRequest`**

| Método | Descripción |
|--------|-------------|
| `getMethodologyId()` / `setMethodologyId(UUID)` | ID de la guía cuyas fases se reordenan. |
| `getOrderedIds()` / `setOrderedIds(List<UUID>)` | Lista completa de IDs de fases en el orden deseado. |

---

### `SubphaseController` — `/api/subphases`

| Método | HTTP | Auth | Descripción |
|--------|------|------|-------------|
| `getAll()` | GET `/` | 🌐 | Lista todos los pasos (subfases). |
| `getById(UUID id)` | GET `/{id}` | 🌐 | Obtiene un paso por UUID. |
| `getByPhase(UUID phaseId)` | GET `/phase/{phaseId}` | 🌐 | Lista pasos de una fase. |
| `create(SubphaseDTO dto)` | POST `/` | 🔒 | Crea paso; exige `phaseId` válido; `orderIndex` al final. |
| `update(UUID id, SubphaseDTO dto)` | PUT `/{id}` | 🔒 | Actualiza título, código, contenido; puede mover a otra fase. |
| `delete(UUID id)` | DELETE `/{id}` | 🔒 | Borra enlaces recurso y luego el paso. |
| `reorder(ReorderRequest body)` | POST `/reorder` | 🔒 | Reordena pasos dentro de una fase; devuelve lista actualizada. |

**`SubphaseController.ReorderRequest`**

| Método | Descripción |
|--------|-------------|
| `getPhaseId()` / `setPhaseId(UUID)` | ID de la fase cuyos pasos se reordenan. |
| `getOrderedIds()` / `setOrderedIds(List<UUID>)` | IDs de subfases en orden final. |

---

### `ResourceController` — `/api/resources`

| Método | HTTP | Auth | Descripción |
|--------|------|------|-------------|
| `getAll()` | GET `/` | 🌐 | Lista todos los recursos (videos, PDFs, etc.). |
| `getById(UUID id)` | GET `/{id}` | 🌐 | Obtiene un recurso por UUID. |
| `create(ResourceDTO dto)` | POST `/` | 🔒 | Crea recurso global (URL única). |
| `update(UUID id, ResourceDTO dto)` | PUT `/{id}` | 🔒 | Actualiza tipo, título, URL, metadata, etc. |
| `delete(UUID id)` | DELETE `/{id}` | 🔒 | Elimina el recurso por ID. |

---

### `SubphaseResourceController` — `/api/subphase-resources`

| Método | HTTP | Auth | Descripción |
|--------|------|------|-------------|
| `getAll()` | GET `/` | 🌐 | Lista todos los enlaces paso↔recurso. |
| `getBySubphase(UUID subphaseId)` | GET `/subphase/{subphaseId}` | 🌐 | Enlaces de un paso concreto. |
| `getByResource(UUID resourceId)` | GET `/resource/{resourceId}` | 🌐 | Enlaces que usan un recurso dado. |
| `getById(UUID subphaseId, UUID resourceId)` | GET `/{subphaseId}/{resourceId}` | 🌐 | Un enlace por clave compuesta. |
| `create(SubphaseResourceDTO dto)` | POST `/` | 🔒 | Vincula recurso existente a un paso. |
| `update(UUID subphaseId, UUID resourceId, SubphaseResourceDTO dto)` | PUT `/{subphaseId}/{resourceId}` | 🔒 | Actualiza `orderIndex` y `note` del enlace. |
| `delete(UUID subphaseId, UUID resourceId)` | DELETE `/{subphaseId}/{resourceId}` | 🔒 | Desvincula recurso del paso (no borra el recurso global). |

---

## Servicios — interfaces

### `MethodologyService`

| Método | Descripción |
|--------|-------------|
| `findById(UUID id)` | Busca metodología; retorna `null` si no existe. |
| `findAll()` | Retorna todas las metodologías. |
| `save(Methodology incoming)` | Crea (defaults + timestamps) o actualiza campos editables. |
| `deleteById(UUID id)` | Elimina por ID. |

### `PhaseService`

| Método | Descripción |
|--------|-------------|
| `findById(UUID id)` | Busca fase; `null` si no existe. |
| `findAll()` | Todas las fases. |
| `findByMethodologyId(UUID methodologyId)` | Fases de una guía. |
| `save(Phase incoming)` | Create con auto-orden al final; update sin cambiar `orderIndex`. |
| `deleteById(UUID id)` | Cascada: borra subfases hijas y luego la fase. |
| `reorder(UUID methodologyId, List<UUID> orderedIds)` | Reordenamiento atómico en dos pasadas. |

### `SubphaseService`

| Método | Descripción |
|--------|-------------|
| `findById(UUID id)` | Busca subfase; `null` si no existe. |
| `findAll()` | Todas las subfases. |
| `findByPhaseId(UUID phaseId)` | Pasos de una fase. |
| `save(Subphase incoming)` | Create al final; update puede mover de fase. |
| `deleteById(UUID id)` | Borra enlaces recurso y subfase. |
| `reorder(UUID phaseId, List<UUID> orderedIds)` | Reordenamiento atómico de pasos. |

### `ResourceService`

| Método | Descripción |
|--------|-------------|
| `findById(UUID id)` | Busca recurso; `null` si no existe. |
| `findAll()` | Todos los recursos. |
| `save(Resource incoming)` | Create con metadata default; update merge de campos. |
| `deleteById(UUID id)` | Elimina recurso. |

### `SubphaseResourceService`

| Método | Descripción |
|--------|-------------|
| `findById(UUID subphaseId, UUID resourceId)` | Enlace por PK compuesta; `null` si no existe. |
| `findAll()` | Todos los enlaces. |
| `findBySubphaseId(UUID subphaseId)` | Enlaces de un paso. |
| `findByResourceId(UUID resourceId)` | Enlaces que referencian un recurso. |
| `save(SubphaseResource entity)` | Inserta o actualiza enlace. |
| `deleteById(UUID subphaseId, UUID resourceId)` | Elimina enlace. |

---

## Servicios — implementaciones

### `MethodologyServiceImpl`

Implementa `MethodologyService`. En create aplica defaults (`draft`, versión 1, `es`, timestamps). En update hace merge y lanza `ResourceNotFoundException` si el ID no existe.

### `PhaseServiceImpl`

Implementa `PhaseService`. Valida metodología en create; calcula `next orderIndex`; reorder con índices temporales 1000+; delete en cascada vía `SubphaseService`.

### `SubphaseServiceImpl`

Implementa `SubphaseService`. Auto-orden en create; al cambiar de fase reasigna al final del destino; delete limpia `subphase_resources` primero.

### `ResourceServiceImpl`

Implementa `ResourceService`. Normaliza `metadata` a `{}` en create; update parcial de metadata.

### `SubphaseResourceServiceImpl`

Implementa `SubphaseResourceService`. Delegación directa al repositorio JPA.

### `AdminAuthService`

| Método | Descripción |
|--------|-------------|
| `matches(String username, String password)` | Comprueba credenciales en tabla `users` (BCrypt o texto plano). Retorna `false` si usuario nulo, no encontrado o contraseña incorrecta. |

---

## Seguridad

### `JwtService`

| Método | Descripción |
|--------|-------------|
| `generateAccessToken(String username)` | Emite JWT con `sub`, claim `role=ADMIN`, `iat` y `exp`. |
| `extractUsername(String token)` | Lee el subject del token. |
| `isTokenValid(String token)` | Verifica firma y expiración; captura excepciones y retorna boolean. |
| `extractRoles(String token)` | Devuelve lista con prefijo `ROLE_` (p. ej. `ROLE_ADMIN`). |

### `JwtAuthenticationFilter`

| Método | Descripción |
|--------|-------------|
| `doFilterInternal(...)` | Intercepta requests; si hay Bearer válido, establece `SecurityContext`; si no, continúa sin autenticación. |

### `JwtAuthenticationEntryPoint`

| Método | Descripción |
|--------|-------------|
| `commence(...)` | Responde JSON 401 cuando falta o es inválida la autenticación. |

### `JwtAccessDeniedHandler`

| Método | Descripción |
|--------|-------------|
| `handle(...)` | Responde JSON 403 cuando el usuario autenticado no tiene permiso. |

---

## Configuración

### `SecurityConfig`

| Método / Bean | Descripción |
|---------------|-------------|
| `securityFilterChain(HttpSecurity http)` | Define reglas de autorización, CORS, JWT filter, sesión stateless. |
| `passwordEncoder()` | Bean `BCryptPasswordEncoder`. |
| `corsConfigurationSource()` | Orígenes permitidos desde `app.security.cors.allowed-origins`. |

### `AdminSecurityProperties`

| Método | Descripción |
|--------|-------------|
| `getJwt()` | Acceso a secret y expiración JWT. |
| `getCors()` | Acceso a orígenes CORS permitidos. |

**Clase interna `Jwt`:** `getSecret()`, `setSecret()`, `getExpirationMs()`, `setExpirationMs()`

**Clase interna `Cors`:** `getAllowedOrigins()`, `setAllowedOrigins()`

### `CorsConfig`

| Método | Descripción |
|--------|-------------|
| `corsFilter()` | Bean `CorsFilter` permisivo (`*`) — complementario a SecurityConfig. |

### `OpenApiConfig`

| Método | Descripción |
|--------|-------------|
| `backendOpenApi()` | Bean OpenAPI con título y versión para Swagger UI. |

---

## Excepciones

### `GlobalExceptionHandler`

| Método | Descripción |
|--------|-------------|
| `handleMethodArgumentNotValid(...)` | 400 con mensajes de validación `@Valid`. |
| `handleResourceNotFound(ResourceNotFoundException)` | 404 con mensaje de la excepción. |
| `handleIllegalArgument(IllegalArgumentException)` | 400 (p. ej. FK faltante en body). |
| `handleResponseStatus(ResponseStatusException)` | Mapea status HTTP de la excepción (401 login, etc.). |
| `handleGeneric(Exception)` | 500 con mensaje de error. |

### `ResourceNotFoundException`

| Constructor | Descripción |
|-------------|-------------|
| `ResourceNotFoundException(String message)` | Excepción de dominio para entidades no encontradas. |

### `ApiError`

DTO de error JSON: getters/setters de `timestamp`, `status`, `error`, `message`, `details`.

---

## Mappers (estáticos)

Cada mapper expone:

| Método | Descripción |
|--------|-------------|
| `toDTO(Entity entity)` | Entidad JPA → DTO JSON; `null` → `null`. |
| `toEntity(DTO dto)` | DTO → entidad; relaciones padre no se resuelven aquí. |

Clases: `MethodologyMapper`, `PhaseMapper`, `SubphaseMapper`, `ResourceMapper`, `SubphaseResourceMapper`.

---

## Repositorios (Spring Data JPA)

Además de métodos heredados de `JpaRepository` (`save`, `findById`, `findAll`, `deleteById`, …):

| Repositorio | Método custom | Descripción |
|-------------|---------------|-------------|
| `PhaseRepository` | `findByMethodologyId(UUID)` | Fases de una metodología. |
| `SubphaseRepository` | `findByPhaseId(UUID)` | Subfases de una fase. |
| `SubphaseResourceRepository` | `findBySubphaseId(UUID)` | Enlaces por subfase. |
| `SubphaseResourceRepository` | `findByResourceId(UUID)` | Enlaces por recurso. |
| `UserRepository` | `findByUsername(String)` | Usuario admin para login. |

`MethodologyRepository` y `ResourceRepository` solo usan métodos estándar JPA.

---

## DTOs (resumen de campos)

| DTO | Campos principales |
|-----|-------------------|
| `LoginRequest` | `username`, `password` (@NotBlank) |
| `AuthResponse` | `accessToken`, `tokenType`, `expiresIn`, `role` |
| `AdminMeResponse` | `username`, `role` |
| `MethodologyDTO` | id, slug, name, introduction, summary, status, version, languageCode, timestamps |
| `PhaseDTO` | id, methodologyId, code, title, description, orderIndex, timestamps |
| `SubphaseDTO` | id, phaseId, code, title, content, orderIndex, timestamps |
| `ResourceDTO` | id, type, title, url, description, provider, thumbnailUrl, metadata, timestamps |
| `SubphaseResourceDTO` | subphaseId, resourceId, orderIndex, note |

---

## Entidades JPA — callbacks

| Entidad | Callback | Descripción |
|---------|----------|-------------|
| `Methodology`, `Phase`, `Subphase`, `Resource` | `@PrePersist` / `@PreUpdate` | Timestamps UTC y defaults (status, metadata, etc.). |
| `SubphaseResource` | `@PrePersist` | Default `orderIndex = 1` si es null. |

Getters/setters estándar en todas las entidades para cada campo documentado en [DOCUMENTACION.md](./DOCUMENTACION.md#5-modelo-de-datos).

---

## Enums

| Enum | Valores |
|------|---------|
| `MethodologyStatus` | `draft`, `published`, `archived` |
| `ResourceType` | `video`, `image`, `reading`, `article`, `book`, `tool`, `other` |
