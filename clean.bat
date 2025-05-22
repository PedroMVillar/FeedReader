@echo off
set BIN_DIR=bin

echo [CLEAN] Eliminando archivos .class en %BIN_DIR%...
if exist %BIN_DIR% (
    del /s /q %BIN_DIR%\*.class >nul 2>&1
    echo  [OK]  Archivos .class eliminados.
) else (
    echo [INFO] No existe la carpeta %BIN_DIR%.
)
pause
