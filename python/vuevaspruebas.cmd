@ECHO OFF

TITLE QR Assistance
if exist "../scripts/env_for_icons.bat" (
	::ECHO Creating Python3 Virtual Environment
	call "../scripts/env_for_icons.bat")

ECHO Starting software

python Sistema_Ausencias.py 
PAUSE