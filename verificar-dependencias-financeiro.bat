@echo off
echo ============================================
echo   VERIFICANDO DEPENDÊNCIAS DO PROJETO
echo ============================================

REM Verifica Java
java -version >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Java não está instalado.
    echo Abrindo site para download do Java JDK...
    start https://adoptium.net/pt-br/temurin/releases/?version=17
) ELSE (
    echo [OK] Java instalado.
)

REM Verifica Maven
mvn -version >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Maven não está instalado.
    echo Abrindo site para download do Maven...
    start https://maven.apache.org/download.cgi
) ELSE (
    echo [OK] Maven instalado.
)

REM Verifica Node.js
node -v >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Node.js não está instalado.
    echo Abrindo site para download do Node.js...
    start https://nodejs.org/
) ELSE (
    echo [OK] Node.js instalado.
)

REM Verifica npm
npm -v >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo [ERRO] npm não está instalado.
    echo Abrindo site para download do Node.js (npm incluso)...
    start https://nodejs.org/
) ELSE (
    echo [OK] npm instalado.
)

REM Verifica Git
git --version >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Git não está instalado.
    echo Abrindo site para download do Git...
    start https://git-scm.com/downloads
) ELSE (
    echo [OK] Git instalado.
)

echo.
echo ============================================
echo  Se apareceram erros acima, instale as dependências
echo  e rode novamente este script.
echo ============================================
pause
