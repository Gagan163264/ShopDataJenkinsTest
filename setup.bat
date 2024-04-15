@echo off
schtasks /create /sc minute /mo 2 /tn "Compose script" /tr "%~dp0deploy.bat"
