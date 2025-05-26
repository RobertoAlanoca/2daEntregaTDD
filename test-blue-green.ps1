# ===============================================
# BLUE-GREEN DEPLOYMENT - VALIDACION LOCAL
# Pipeline completo para Windows PowerShell
# ===============================================

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "🔵🟢 BLUE-GREEN DEPLOYMENT - VALIDACION LOCAL" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Configurar variables de entorno para CI
$env:CI = "true"

try {
    # ========================================
    # FASE 1: 🔵 BLUE PHASE - Pre-Deploy Tests
    # ========================================
    Write-Host "[FASE 1] 🔵 BLUE PHASE - Pre-Deploy Tests" -ForegroundColor Blue
    Write-Host "-------------------------------------------" -ForegroundColor Blue
    Write-Host "⏱️ Ejecutando tests unitarios..." -ForegroundColor Yellow
    
    $result1 = & .\mvnw.cmd clean test -Punit-tests -q
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Blue Phase FAILED - Tests unitarios fallaron" -ForegroundColor Red
        Read-Host "Presiona Enter para continuar"
        exit 1
    }
    Write-Host "✅ Blue Phase PASSED - Tests unitarios exitosos" -ForegroundColor Green
    Write-Host ""

    # ========================================
    # FASE 2: 📦 DEPLOY PHASE - Build & Package
    # ========================================
    Write-Host "[FASE 2] 📦 DEPLOY PHASE - Build & Package" -ForegroundColor Magenta
    Write-Host "-------------------------------------------" -ForegroundColor Magenta
    Write-Host "⏱️ Construyendo aplicación..." -ForegroundColor Yellow
    
    $result2 = & .\mvnw.cmd clean package -DskipTests -q
    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Deploy Phase FAILED - Error en build" -ForegroundColor Red
        Read-Host "Presiona Enter para continuar"
        exit 1
    }
    
    # Verificar que el JAR existe
    $jarFile = Get-ChildItem -Path "target\*.jar" | Where-Object { $_.Name -notlike "*original*" } | Select-Object -First 1
    if (-not $jarFile) {
        Write-Host "❌ Deploy Phase FAILED - JAR no encontrado" -ForegroundColor Red
        Read-Host "Presiona Enter para continuar"
        exit 1
    }
    Write-Host "✅ Deploy Phase PASSED - Aplicación construida: $($jarFile.Name)" -ForegroundColor Green
    Write-Host ""

    # ========================================
    # FASE 3: 🟢 GREEN PHASE - Post-Deploy Tests
    # ========================================
    Write-Host "[FASE 3] 🟢 GREEN PHASE - Post-Deploy Tests" -ForegroundColor Green
    Write-Host "-------------------------------------------" -ForegroundColor Green
    Write-Host "⏱️ Iniciando aplicación Spring Boot..." -ForegroundColor Yellow

    # Iniciar aplicación en background
    $appProcess = Start-Process -FilePath "java" -ArgumentList @("-jar", $jarFile.FullName) -PassThru -WindowStyle Minimized
    Write-Host "🚀 Aplicación iniciada con PID: $($appProcess.Id)" -ForegroundColor Cyan

    # Esperar a que arranque la aplicación
    Write-Host "⏱️ Esperando a que la aplicación arranque..." -ForegroundColor Yellow
    $maxAttempts = 30
    $attempts = 0
    $appReady = $false

    do {
        Start-Sleep -Seconds 2
        $attempts++
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:8080" -TimeoutSec 3 -ErrorAction Stop
            if ($response.StatusCode -eq 200) {
                $appReady = $true
                Write-Host "✅ Aplicación corriendo en http://localhost:8080" -ForegroundColor Green
            }
        }
        catch {
            Write-Host "⏳ Intento $attempts/$maxAttempts - Aplicación aún arrancando..." -ForegroundColor Yellow
        }
    } while (-not $appReady -and $attempts -lt $maxAttempts)

    if (-not $appReady) {
        Write-Host "❌ Green Phase FAILED - Aplicación no arrancó en tiempo esperado" -ForegroundColor Red
        Stop-Process -Id $appProcess.Id -Force -ErrorAction SilentlyContinue
        Read-Host "Presiona Enter para continuar"
        exit 1
    }

    # Ejecutar tests de integración
    Write-Host "⏱️ Ejecutando tests de integración (Selenium)..." -ForegroundColor Yellow
    $result3 = & .\mvnw.cmd test -Pintegration-tests -Dheadless=true -q
    $seleniumResult = $LASTEXITCODE

    # Detener aplicación Spring Boot
    Write-Host "⏱️ Deteniendo aplicación Spring Boot..." -ForegroundColor Yellow
    Stop-Process -Id $appProcess.Id -Force -ErrorAction SilentlyContinue
    Write-Host "🛑 Aplicación detenida" -ForegroundColor Gray

    if ($seleniumResult -ne 0) {
        Write-Host "❌ Green Phase FAILED - Tests de integración fallaron" -ForegroundColor Red
        Read-Host "Presiona Enter para continuar"
        exit 1
    }
    Write-Host "✅ Green Phase PASSED - Tests de integración exitosos" -ForegroundColor Green
    Write-Host ""

    # ========================================
    # RESUMEN FINAL
    # ========================================
    Write-Host "============================================" -ForegroundColor Cyan
    Write-Host "🎉 BLUE-GREEN DEPLOYMENT SUCCESSFUL" -ForegroundColor Green
    Write-Host "============================================" -ForegroundColor Cyan
    Write-Host "✅ Blue Phase (Pre-Deploy): PASSED" -ForegroundColor Green
    Write-Host "✅ Deploy Phase (Build): PASSED" -ForegroundColor Green
    Write-Host "✅ Green Phase (Post-Deploy): PASSED" -ForegroundColor Green
    Write-Host ""
    Write-Host "🚀 Tu aplicación está lista para deploy a GitHub!" -ForegroundColor Yellow
    Write-Host "📁 Archivos actualizados:" -ForegroundColor Cyan
    Write-Host "   - .github/workflows/maven-publish.yml (Pipeline Blue-Green)" -ForegroundColor White
    Write-Host "   - pom.xml (Perfiles Maven)" -ForegroundColor White
    Write-Host "   - SeleniumTest.java (Compatibilidad CI/CD)" -ForegroundColor White
    Write-Host "   - BLUE-GREEN-DEPLOYMENT.md (Documentación)" -ForegroundColor White
    Write-Host ""
}
catch {
    Write-Host "❌ ERROR INESPERADO: $($_.Exception.Message)" -ForegroundColor Red
    # Intentar limpiar procesos
    Get-Process | Where-Object {$_.ProcessName -eq "java" -and $_.MainWindowTitle -eq ""} | Stop-Process -Force -ErrorAction SilentlyContinue
}
finally {
    Write-Host ""
    Read-Host "Presiona Enter para continuar"
}
