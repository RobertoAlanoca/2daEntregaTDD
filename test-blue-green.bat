@echo off
echo ===========================================
echo BLUE-GREEN DEPLOYMENT - VALIDACION LOCAL
echo ===========================================
echo.

:: Configurar variables de entorno para CI
set CI=true
set DISPLAY=:99

echo [FASE 1] 🔵 BLUE PHASE - Pre-Deploy Tests
echo -------------------------------------------
echo ⏱️ Ejecutando tests unitarios...
call mvn clean test -Punit-tests
if %errorlevel% neq 0 (
    echo ❌ Blue Phase FAILED - Tests unitarios fallaron
    pause
    exit /b 1
)
echo ✅ Blue Phase PASSED - Tests unitarios exitosos
echo.

echo [FASE 2] 📦 DEPLOY PHASE - Build & Package
echo -------------------------------------------
echo ⏱️ Construyendo aplicación...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ❌ Deploy Phase FAILED - Error en build
    pause
    exit /b 1
)
echo ✅ Deploy Phase PASSED - Aplicación construida
echo.

echo [FASE 3] 🟢 GREEN PHASE - Post-Deploy Tests
echo -------------------------------------------
echo ⏱️ Iniciando aplicación Spring Boot...

:: Iniciar aplicación en background
start /min "Spring Boot App" java -jar target\*.jar
timeout /t 10 /nobreak > nul

:: Esperar a que arranque la aplicación
echo ⏱️ Esperando a que la aplicación arranque...
:wait_loop
curl -f http://localhost:8080 > nul 2>&1
if %errorlevel% neq 0 (
    timeout /t 2 /nobreak > nul
    goto wait_loop
)
echo ✅ Aplicación corriendo en http://localhost:8080

echo ⏱️ Ejecutando tests de integración (Selenium)...
call mvn test -Pintegration-tests -Dheadless=true
set selenium_result=%errorlevel%

:: Detener aplicación Spring Boot
echo ⏱️ Deteniendo aplicación Spring Boot...
for /f "tokens=5" %%a in ('netstat -aon ^| find ":8080"') do taskkill /PID %%a /F > nul 2>&1

if %selenium_result% neq 0 (
    echo ❌ Green Phase FAILED - Tests de integración fallaron
    pause
    exit /b 1
)
echo ✅ Green Phase PASSED - Tests de integración exitosos
echo.

echo ===========================================
echo 🎉 BLUE-GREEN DEPLOYMENT SUCCESSFUL
echo ===========================================
echo ✅ Blue Phase (Pre-Deploy): PASSED
echo ✅ Deploy Phase (Build): PASSED
echo ✅ Green Phase (Post-Deploy): PASSED
echo.
echo 🚀 Tu aplicación está lista para deploy a GitHub!
echo.
pause
