<#
.SYNOPSIS
    Orquesta la suite de pruebas Newman + coverage con JaCoCo.

.DESCRIPTION
    1. Compila el backend (bootJar).
    2. Resuelve la ruta de jacocoagent.jar a partir del cache de Gradle.
    3. Arranca la aplicación con el agente de JaCoCo apuntando a build/jacoco/newman.exec.
    4. Espera a que /api/health responda 200.
    5. Ejecuta la colección Postman con Newman.
    6. Apaga el servidor vía POST /actuator/shutdown (esto vuelca el .exec a disco).
    7. Genera el reporte HTML/XML y valida coverage >= 90%.

.NOTES
    Diseñado para Windows + PowerShell 5.1+. Requiere `newman` en PATH.
    Equivalente bash: newman/run-newman-tests.sh
#>

param(
    [int]$Port = 8080,
    [int]$StartupTimeoutSeconds = 90,
    [switch]$SkipBuild,
    [switch]$SkipVerify
)

$ErrorActionPreference = 'Stop'

# Rutas base
$repoRoot      = Resolve-Path (Join-Path $PSScriptRoot '..')
$buildDir      = Join-Path $repoRoot 'build'
$jacocoDir     = Join-Path $buildDir 'jacoco'
$execFile      = Join-Path $jacocoDir 'newman.exec'
$libsDir       = Join-Path $buildDir 'libs'
$reportsDir    = Join-Path $buildDir 'reports'
$newmanDir     = $PSScriptRoot
$collection    = Join-Path $newmanDir 'devops-sandbox.postman_collection.json'
$environment   = Join-Path $newmanDir 'devops-sandbox.postman_environment.json'
$newmanReports = Join-Path $reportsDir 'newman'

# Crea carpetas necesarias
New-Item -ItemType Directory -Force -Path $jacocoDir | Out-Null
New-Item -ItemType Directory -Force -Path $newmanReports | Out-Null

function Invoke-Gradle {
    param([Parameter(ValueFromRemainingArguments = $true)]$Args)
    $gradlew = Join-Path $repoRoot 'gradlew.bat'
    if (-not (Test-Path $gradlew)) { throw "No se encontró gradlew.bat en $repoRoot" }
    Write-Host "==> gradlew $($Args -join ' ')" -ForegroundColor Cyan
    & $gradlew @Args
    if ($LASTEXITCODE -ne 0) { throw "gradlew $($Args -join ' ') falló (exit $LASTEXITCODE)" }
}

# ----------------------------------------------------------------------------
# 1) Build del jar (a menos que -SkipBuild)
# ----------------------------------------------------------------------------
if (-not $SkipBuild) {
    Invoke-Gradle 'clean', 'bootJar', '-x', 'test'
}

$jar = Get-ChildItem -Path $libsDir -Filter '*.jar' -ErrorAction SilentlyContinue |
       Where-Object { $_.Name -notmatch '-plain\.jar$' } |
       Select-Object -First 1
if (-not $jar) { throw "No se encontró el jar de Spring Boot en $libsDir" }
Write-Host "Jar localizado: $($jar.FullName)" -ForegroundColor Green

# ----------------------------------------------------------------------------
# 2) Localiza jacocoagent.jar a partir de la tarea Gradle
# ----------------------------------------------------------------------------
Write-Host "==> Resolviendo ruta de jacocoagent.jar" -ForegroundColor Cyan
$agentOutput = & (Join-Path $repoRoot 'gradlew.bat') 'printJacocoAgentPath' '-q'
if ($LASTEXITCODE -ne 0) { throw "No se pudo resolver jacocoagent (exit $LASTEXITCODE)" }
$agentPath = ($agentOutput | Where-Object { $_ -match 'org\.jacoco\.agent.*\.jar$' } | Select-Object -Last 1).Trim()
if (-not (Test-Path $agentPath)) { throw "jacocoagent no encontrado en '$agentPath'" }
Write-Host "Agent: $agentPath" -ForegroundColor Green

# Limpia exec previo
if (Test-Path $execFile) { Remove-Item $execFile -Force }

# ----------------------------------------------------------------------------
# 3) Arranca la aplicación con el agente de JaCoCo
# ----------------------------------------------------------------------------
$javaArgs = @(
    "-javaagent:$agentPath=destfile=$execFile,append=false",
    '-Dspring.profiles.active=test',
    "-Dserver.port=$Port",
    '-jar',
    $jar.FullName
)

$stdoutLog = Join-Path $buildDir 'newman-server.out.log'
$stderrLog = Join-Path $buildDir 'newman-server.err.log'
'' | Out-File -FilePath $stdoutLog -Encoding UTF8
'' | Out-File -FilePath $stderrLog -Encoding UTF8

Write-Host "==> Arrancando backend con JaCoCo agent (logs: $stdoutLog)" -ForegroundColor Cyan
$proc = Start-Process -FilePath 'java' -ArgumentList $javaArgs `
    -PassThru -NoNewWindow `
    -RedirectStandardOutput $stdoutLog `
    -RedirectStandardError $stderrLog

if (-not $proc) { throw "No se pudo arrancar la JVM" }
Write-Host "PID backend: $($proc.Id)" -ForegroundColor Green

# Asegura kill del proceso si algo falla a partir de aquí
try {
    # ------------------------------------------------------------------------
    # 4) Espera a /api/health
    # ------------------------------------------------------------------------
    $healthUrl = "http://localhost:$Port/api/health"
    Write-Host "==> Esperando a $healthUrl (timeout ${StartupTimeoutSeconds}s)" -ForegroundColor Cyan
    $deadline = (Get-Date).AddSeconds($StartupTimeoutSeconds)
    $ready = $false
    while ((Get-Date) -lt $deadline) {
        if ($proc.HasExited) {
            $errTail = if (Test-Path $stderrLog) { Get-Content $stderrLog -Tail 60 -ErrorAction SilentlyContinue } else { @() }
            $outTail = if (Test-Path $stdoutLog) { Get-Content $stdoutLog -Tail 60 -ErrorAction SilentlyContinue } else { @() }
            throw ("La JVM terminó antes de levantarse (exit $($proc.ExitCode)).`n" +
                   "--- stderr (tail) ---`n" + ($errTail -join "`n") + "`n" +
                   "--- stdout (tail) ---`n" + ($outTail -join "`n"))
        }
        try {
            $r = Invoke-WebRequest -UseBasicParsing -Uri $healthUrl -TimeoutSec 2
            if ($r.StatusCode -eq 200) { $ready = $true; break }
        } catch {
            Start-Sleep -Milliseconds 800
        }
    }
    if (-not $ready) { throw "El backend no respondió 200 en $healthUrl tras ${StartupTimeoutSeconds}s" }
    Write-Host "Backend OK" -ForegroundColor Green

    # ------------------------------------------------------------------------
    # 5) Newman
    # ------------------------------------------------------------------------
    $stamp = Get-Date -Format 'yyyyMMdd-HHmmss'
    $htmlReport = Join-Path $newmanReports "newman-$stamp.html"
    $jsonReport = Join-Path $newmanReports "newman-$stamp.json"
    $junitReport = Join-Path $newmanReports "newman-$stamp.xml"

    Write-Host "==> Ejecutando Newman" -ForegroundColor Cyan
    # OJO: pasamos los args como cadena única para evitar que PowerShell parta
    # "cli,json,junit" como array al invocar el ejecutable nativo.
    $newmanArgs = @(
        'run', "$collection",
        '--environment', "$environment",
        '--env-var', "baseUrl=http://localhost:$Port",
        '--reporters', 'cli,json,junit',
        '--reporter-json-export', "$jsonReport",
        '--reporter-junit-export', "$junitReport",
        '--color', 'on'
    )
    & newman @newmanArgs
    $newmanExit = $LASTEXITCODE
    Write-Host "Newman exit: $newmanExit" -ForegroundColor Yellow

} finally {
    # ------------------------------------------------------------------------
    # 6) Apagar la app limpiamente (esto vuelca el .exec)
    # ------------------------------------------------------------------------
    Write-Host "==> Apagando backend vía /actuator/shutdown" -ForegroundColor Cyan
    try {
        Invoke-WebRequest -UseBasicParsing `
            -Uri "http://localhost:$Port/actuator/shutdown" `
            -Method Post `
            -ContentType 'application/json' `
            -Body '{}' `
            -TimeoutSec 10 `
            -ErrorAction Stop | Out-Null
        Write-Host "shutdown OK" -ForegroundColor Green
    } catch {
        Write-Host "shutdown endpoint no respondió: $($_.Exception.Message)" -ForegroundColor DarkYellow
    }
    # Espera a que el proceso termine de forma natural (JaCoCo escribe el .exec
    # en el shutdown hook de la JVM).
    if ($proc -and -not $proc.HasExited) {
        if (-not $proc.WaitForExit(20000)) {
            Write-Host "Forzando kill del proceso $($proc.Id)" -ForegroundColor Red
            try { Stop-Process -Id $proc.Id -Force } catch { }
        }
    }
    Write-Host "Backend detenido." -ForegroundColor Green
}

# ----------------------------------------------------------------------------
# 7) Reporte de JaCoCo + verificación
# ----------------------------------------------------------------------------
if (-not (Test-Path $execFile)) {
    throw "No se generó $execFile - el agente no escribió el dump (¿shutdown no se invocó?)"
}
Write-Host "==> Generando reporte JaCoCo" -ForegroundColor Cyan
Invoke-Gradle 'jacocoNewmanReport'

if (-not $SkipVerify) {
    Write-Host "==> Verificando coverage >= 90% líneas" -ForegroundColor Cyan
    Invoke-Gradle 'jacocoNewmanCoverageVerification'
}

$htmlIndex = Join-Path $buildDir 'reports\jacoco\jacocoNewmanReport\html\index.html'
$xmlReport = Join-Path $buildDir 'reports\jacoco\jacocoNewmanReport\jacocoNewmanReport.xml'

# Imprime un resumen del coverage si el XML está disponible.
if (Test-Path $xmlReport) {
    try {
        [xml]$jr = Get-Content $xmlReport
        Write-Host ""
        Write-Host "===== Coverage (toda la app) =====" -ForegroundColor Cyan
        foreach ($c in $jr.report.counter) {
            $missed  = [int]$c.missed
            $covered = [int]$c.covered
            $total   = $missed + $covered
            $pct = if ($total -gt 0) { [math]::Round(($covered / $total) * 100, 2) } else { 0 }
            ("  {0,-12} {1,5}/{2,-5}  ({3}%)" -f $c.type, $covered, $total, $pct) | Write-Host
        }
    } catch { }
}

if (Test-Path $htmlIndex) {
    Write-Host ""
    Write-Host "Reporte HTML: $htmlIndex" -ForegroundColor Green
}
Write-Host "Listo." -ForegroundColor Green

if ($newmanExit -ne 0) {
    exit $newmanExit
}
