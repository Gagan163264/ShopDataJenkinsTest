@echo off
setlocal enabledelayedexpansion
set log_file=deploy.log
cd %~dp0
echo ---------------------------------------- >> %log_file%
echo Running docker compose: %date% %time% >> %log_file%
echo LOG:>> %log_file%
@docker compose up -d 2>>%log_file%
if %errorlevel% neq 0 (
    echo [ERROR] Docker compose failed %errorlevel%>> %log_file%
    echo ---------------------------------------- >> %log_file%
    exit /b %errorlevel%
)

echo [INFO] docker compose completed successfully >> %log_file%
echo ---------------------------------------- >> %log_file%

endlocal