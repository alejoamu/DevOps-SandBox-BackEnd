# Documentación del backend — DevOps SandBox

Documentación técnica del API REST en `DevOps-SandBox-BackEnd`.

| Documento | Contenido |
|-----------|-----------|
| [DOCUMENTACION.md](./DOCUMENTACION.md) | Arquitectura, stack, seguridad, modelo de datos, flujos de implementación, configuración, despliegue y pruebas |
| [CLASES.md](./CLASES.md) | Catálogo de las **58 clases Java** (entidades, DTOs, controllers, services, security, config, mappers, repos, enums) |
| [REFERENCIA-METODOS.md](./REFERENCIA-METODOS.md) | Descripción breve de **cada método público** (controladores, servicios, seguridad, mappers, excepciones) |

## Inicio rápido

```bash
./gradlew bootRun          # desarrollo (puerto 8080)
./gradlew bootJar -x test  # generar JAR
./newman/run-newman-tests.ps1  # pruebas E2E + coverage (ver newman/README.md)
```

- **Swagger UI:** `http://localhost:8080/api/docs/ui`
- **Health:** `GET /api/health`
