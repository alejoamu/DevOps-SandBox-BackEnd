# DevOps SandBox — Backend

API REST (Spring Boot 3 + PostgreSQL) para guías metodológicas, fases, pasos y recursos.

## Documentación

Toda la documentación técnica está en **[docs/](./docs/README.md)**:

- [DOCUMENTACION.md](./docs/DOCUMENTACION.md) — arquitectura, seguridad, modelo de datos, reglas de negocio, configuración y despliegue
- [CLASES.md](./docs/CLASES.md) — catálogo de las 58 clases Java del backend
- [REFERENCIA-METODOS.md](./docs/REFERENCIA-METODOS.md) — descripción breve de cada método público

## Comandos útiles

```bash
./gradlew bootRun              # desarrollo
./gradlew bootJar -x test      # JAR
./newman/run-newman-tests.ps1  # pruebas E2E + coverage
```

- Health: `GET /api/health`
- Swagger: `/api/docs/ui`
