@ECHO OFF

TITLE QR Assistance - Sistema de ausencias y QRS
if exist "../scripts/env_for_icons.bat" (
	::ECHO Creating Python3 Virtual Environment
	call "../scripts/env_for_icons.bat")

ECHO Starting software

python TodoJunto.py 
PAUSE