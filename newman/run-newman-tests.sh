#!/usr/bin/env bash
# -----------------------------------------------------------------------------
# Orquesta la suite Newman + coverage con JaCoCo (equivalente a la versión
# PowerShell). Pensado para Linux/macOS o CI (GitHub Actions, etc.).
# -----------------------------------------------------------------------------
set -euo pipefail

PORT="${PORT:-8080}"
STARTUP_TIMEOUT="${STARTUP_TIMEOUT:-90}"
SKIP_BUILD="${SKIP_BUILD:-0}"
SKIP_VERIFY="${SKIP_VERIFY:-0}"

NEWMAN_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$NEWMAN_DIR/.." && pwd)"
BUILD_DIR="$REPO_ROOT/build"
JACOCO_DIR="$BUILD_DIR/jacoco"
EXEC_FILE="$JACOCO_DIR/newman.exec"
LIBS_DIR="$BUILD_DIR/libs"
REPORTS_DIR="$BUILD_DIR/reports/newman"
COLLECTION="$NEWMAN_DIR/devops-sandbox.postman_collection.json"
ENVFILE="$NEWMAN_DIR/devops-sandbox.postman_environment.json"

mkdir -p "$JACOCO_DIR" "$REPORTS_DIR"

gradle() { "$REPO_ROOT/gradlew" "$@"; }

if [[ "$SKIP_BUILD" != "1" ]]; then
  echo "==> Building jar"
  gradle clean bootJar -x test
fi

JAR="$(ls "$LIBS_DIR"/*.jar 2>/dev/null | grep -v -- '-plain\.jar' | head -n1 || true)"
if [[ -z "$JAR" ]]; then echo "No jar encontrado en $LIBS_DIR"; exit 1; fi
echo "Jar: $JAR"

echo "==> Resolviendo jacocoagent.jar"
AGENT="$(gradle printJacocoAgentPath -q | grep 'org\.jacoco\.agent.*\.jar$' | tail -n1 | tr -d '\r' )"
[[ -f "$AGENT" ]] || { echo "jacocoagent no encontrado: $AGENT"; exit 1; }
echo "Agent: $AGENT"

rm -f "$EXEC_FILE"

echo "==> Arrancando backend con JaCoCo agent"
java \
  "-javaagent:${AGENT}=destfile=${EXEC_FILE},append=false" \
  "-Dspring.profiles.active=test" \
  "-Dserver.port=${PORT}" \
  -jar "$JAR" \
  >"$BUILD_DIR/newman-server.out.log" 2>"$BUILD_DIR/newman-server.err.log" &
PID=$!
echo "PID backend: $PID"

cleanup() {
  echo "==> Apagando backend"
  curl -s -X POST -H 'Content-Type: application/json' --data '{}' \
       "http://localhost:${PORT}/actuator/shutdown" >/dev/null || true
  # Espera hasta 15s
  for _ in $(seq 1 30); do
    kill -0 "$PID" 2>/dev/null || return 0
    sleep 0.5
  done
  kill -9 "$PID" 2>/dev/null || true
}
trap cleanup EXIT

echo "==> Esperando /api/health (timeout ${STARTUP_TIMEOUT}s)"
READY=0
for _ in $(seq 1 "$STARTUP_TIMEOUT"); do
  if ! kill -0 "$PID" 2>/dev/null; then
    echo "La JVM terminó antes de levantarse."
    tail -n 60 "$BUILD_DIR/newman-server.err.log" || true
    exit 1
  fi
  if curl -fsS "http://localhost:${PORT}/api/health" >/dev/null 2>&1; then
    READY=1; break
  fi
  sleep 1
done
[[ "$READY" == "1" ]] || { echo "Backend no respondió"; exit 1; }
echo "Backend OK"

STAMP="$(date +%Y%m%d-%H%M%S)"
echo "==> Ejecutando Newman"
set +e
newman run "$COLLECTION" \
  --environment "$ENVFILE" \
  --env-var "baseUrl=http://localhost:${PORT}" \
  --reporters cli,json,junit \
  --reporter-json-export "$REPORTS_DIR/newman-$STAMP.json" \
  --reporter-junit-export "$REPORTS_DIR/newman-$STAMP.xml" \
  --color on
NEWMAN_EXIT=$?
set -e
echo "Newman exit: $NEWMAN_EXIT"

# trap cleanup() corre antes del reporte, lo invocamos manualmente y deshabilitamos trap
cleanup
trap - EXIT

[[ -f "$EXEC_FILE" ]] || { echo "No se generó $EXEC_FILE"; exit 1; }

echo "==> Generando reporte JaCoCo"
gradle jacocoNewmanReport

if [[ "$SKIP_VERIFY" != "1" ]]; then
  echo "==> Verificando coverage >= 90%"
  gradle jacocoNewmanCoverageVerification
fi

echo "Reporte HTML: $BUILD_DIR/reports/jacoco/jacocoNewmanReport/html/index.html"
exit "$NEWMAN_EXIT"
