@echo off
echo ========================================
echo  SIMULANDO PIPELINE DE CI/CD
echo ========================================

echo.
echo [1/3] Ejecutando tests unitarios...
call mvnw.cmd clean test -Punit-tests
if %errorlevel% neq 0 (
    echo ERROR: Tests unitarios fallaron!
    exit /b 1
)

echo.
echo [2/3] Compilando aplicacion...
call mvnw.cmd compile
if %errorlevel% neq 0 (
    echo ERROR: Compilacion fallo!
    exit /b 1
)

echo.
echo [3/3] Ejecutando tests de integracion (Selenium)...
call mvnw.cmd test -Pintegration-tests -Dheadless=true
if %errorlevel% neq 0 (
    echo ERROR: Tests de integracion fallaron!
    exit /b 1
)

echo.
echo ========================================
echo  ✅ TODOS LOS TESTS PASARON!
echo ========================================
