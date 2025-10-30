@echo off
echo ===============================================
echo      INICIANDO PROJETO FINANCEIRO (FULLSTACK)
echo ===============================================

:: Verifica se o usuário está na pasta certa
cd /d %~dp0

echo.
echo [1/5] Acessando a pasta do backend...
cd backend

echo [2/5] Instalando dependências do Maven...
call mvn clean install

echo [3/5] Iniciando backend Spring Boot...
start cmd /k "mvn spring-boot:run"

cd ..

echo.
echo [4/5] Acessando a pasta do frontend...
cd frontend

echo [5/5] Instalando dependências do React...
call npm install

echo Iniciando frontend (Vite)...
start cmd /k "npm run dev"

echo.
echo Projeto iniciado! Acesse:
echo - Backend: http://localhost:8080
echo - Frontend: http://localhost:5173
echo.
pause
