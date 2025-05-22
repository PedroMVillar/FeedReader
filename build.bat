@echo off
setlocal EnableDelayedExpansion

REM === CONFIG ===
set SRC_DIR=src
set BIN_DIR=bin
set LIB_JAR=lib\json-20230618.jar
set MAIN_CLASS=FeedReaderMain

REM === CLEAN ===
echo [CLEAN] Eliminando archivos .class en %BIN_DIR%...
if exist %BIN_DIR% (
    del /s /q %BIN_DIR%\*.class >nul 2>&1
) else (
    mkdir %BIN_DIR%
)

REM === RECOLECTAR FUENTES ===
echo [BUILD] Recolectando archivos fuente...
dir /s /b %SRC_DIR%\*.java > sources.txt

REM === COMPILAR ===
echo [BUILD] Compilando todos los archivos...
javac -cp "%LIB_JAR%" -d %BIN_DIR% @sources.txt
if errorlevel 1 (
    echo ❌ Error de compilación
    del sources.txt
    pause
    exit /b
)

del sources.txt

REM === EJECUTAR ===
echo [RUN] Ejecutando %MAIN_CLASS%...
java -cp "%BIN_DIR%;%LIB_JAR%" %MAIN_CLASS% %*

pause
