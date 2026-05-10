# Pruebas E2E con Newman + Coverage con JaCoCo

Esta carpeta contiene la suite de pruebas funcionales del backend escrita como
una colección Postman, ejecutable headless con
[Newman](https://www.npmjs.com/package/newman). La cobertura del backend se
mide adjuntando el agente de **JaCoCo** a la JVM mientras Newman ejecuta los
requests contra `http://localhost:8080`.

## Contenido

| Archivo                                       | Propósito                                              |
| --------------------------------------------- | ------------------------------------------------------ |
| `devops-sandbox.postman_collection.json`      | Colección Postman v2.1.0 con todos los escenarios.     |
| `devops-sandbox.postman_environment.json`     | Variables de entorno (baseUrl, credenciales, seed IDs).|
| `run-newman-tests.ps1`                        | Orquestador para Windows (PowerShell).                 |
| `run-newman-tests.sh`                         | Orquestador para Linux/macOS/CI.                       |

## Qué cubre la suite

La colección está organizada en 8 folders:

1. **00 - Health** – `/api/health`.
2. **01 - Auth** – login (success / validación / credenciales inválidas /
   usuario inexistente), `/auth/me` con y sin token, encabezados Authorization
   malformados, token inválido.
3. **02 - Methodologies** – CRUD completo, GET sin auth (permitAll), POST sin
   token (401).
4. **03 - Phases** – CRUD, `GET /methodology/{id}`, `POST /reorder` (éxito y
   validaciones), error 400 sin `methodologyId`, error 404 con `methodologyId`
   inexistente.
5. **04 - Subphases** – CRUD, `GET /phase/{id}`, `POST /reorder`, cambio de
   `phaseId` en PUT (mueve la subfase a otra fase), error 404 con `phaseId`
   inexistente.
6. **05 - Resources** – CRUD con varios tipos de `ResourceType` (`video`,
   `article`, `image`).
7. **06 - Subphase Resources** – CRUD por clave compuesta, errores 404 cuando
   subfase o recurso no existen.
8. **07 - Cleanup** – DELETE en cascada (resources → subphases → phases →
   methodologies) para dejar la BD limpia.

Cada request incluye al menos un `pm.test(...)` que valida el código de
estado y, cuando aplica, el contenido relevante del body.

## Cómo se mide el coverage

`run-newman-tests.ps1` / `.sh` orquesta los siguientes pasos:

1. `./gradlew clean bootJar -x test` – produce el JAR ejecutable.
2. Resuelve la ruta a `jacocoagent.jar` usando la tarea Gradle
   `printJacocoAgentPath` (definida en `build.gradle`).
3. Lanza `java -javaagent:jacocoagent.jar=destfile=build/jacoco/newman.exec
   -Dspring.profiles.active=test -jar build/libs/<app>.jar`.
4. Espera a `GET /api/health` 200 (timeout 90 s).
5. Ejecuta `newman run` con la colección y el environment.
6. Apaga la JVM vía `POST /actuator/shutdown` (esto vuelca el `.exec`).
7. `./gradlew jacocoNewmanReport` – HTML + XML en
   `build/reports/jacoco/jacocoNewmanReport/`.
8. `./gradlew jacocoNewmanCoverageVerification` – falla si **< 90% de líneas**
   o **< 85% de instrucciones**.

## Requisitos

- Java 17 (configurado por Gradle Toolchains).
- Node.js + `newman` global o vía `npx`. Instala con:
  ```bash
  npm install -g newman
  ```
- En Windows usa PowerShell 5.1+ o PowerShell Core.

## Uso

### Windows / PowerShell

```powershell
cd DevOps-SandBox-BackEnd
.\newman\run-newman-tests.ps1
```

Flags útiles:

| Flag             | Descripción                                          |
| ---------------- | ---------------------------------------------------- |
| `-SkipBuild`     | No recompila el JAR (reutiliza `build/libs/*.jar`).  |
| `-SkipVerify`    | Genera el reporte pero no aborta si coverage < 90%.  |
| `-Port 9090`     | Cambia el puerto (default 8080).                     |

### Linux / macOS / CI

```bash
chmod +x newman/run-newman-tests.sh
./newman/run-newman-tests.sh
```

Variables de entorno:

| Var              | Default | Descripción                                  |
| ---------------- | ------- | -------------------------------------------- |
| `PORT`           | `8080`  | Puerto del backend.                          |
| `STARTUP_TIMEOUT`| `90`    | Tiempo máximo de espera al startup (s).      |
| `SKIP_BUILD`     | `0`     | Si es `1`, omite la fase de build.           |
| `SKIP_VERIFY`    | `0`     | Si es `1`, no valida el umbral de coverage.  |

## Perfil de test

El runner arranca la JVM con `spring.profiles.active=test` lo que activa
`src/main/resources/application-test.properties`. Este perfil:

- Usa una base de datos **H2 en memoria** (`MODE=PostgreSQL`).
- Crea el esquema con `src/main/resources/test-data/schema.sql`.
- Hace seed con `src/main/resources/test-data/data.sql` (incluye un usuario
  `admin` / `admin123` y una metodología/fase/subfase/recurso semilla).
- Habilita `POST /actuator/shutdown` para que el runner pueda apagar la JVM.

**La base de datos real de Neon nunca se toca durante las pruebas.**

## Reportes

Tras una ejecución exitosa encontrarás:

- `build/reports/jacoco/jacocoNewmanReport/html/index.html` – Reporte HTML
  navegable de coverage.
- `build/reports/jacoco/jacocoNewmanReport/jacocoNewmanReport.xml` – XML para
  integraciones (SonarQube, Codecov).
- `build/reports/newman/newman-<timestamp>.json` – Resumen de la ejecución
  Newman.
- `build/reports/newman/newman-<timestamp>.xml` – Resultados en formato JUnit
  para integrar con CI.

### Ejemplo de resultado actual

Ejecutando contra el código actual la suite obtiene:

```
Coverage (toda la app):
  CLASS         39 / 39    (100%)
  METHOD       238 / 261   (91.19%)
  LINE         717 / 781   (91.81%)
  INSTRUCTION 2891 / 3236  (89.34%)
  BRANCH       144 / 244   (59.02%)
```

Esto sobrepasa el umbral configurado (`LINE >= 90%`, `INSTRUCTION >= 85%`).

## Integración con CI

Ejemplo GitHub Actions:

```yaml
- uses: actions/setup-java@v4
  with: { java-version: '17', distribution: 'temurin' }
- uses: actions/setup-node@v4
  with: { node-version: '20' }
- run: npm i -g newman
- run: ./newman/run-newman-tests.sh
- uses: actions/upload-artifact@v4
  with:
    name: jacoco-newman-report
    path: build/reports/jacoco/jacocoNewmanReport/html
```
