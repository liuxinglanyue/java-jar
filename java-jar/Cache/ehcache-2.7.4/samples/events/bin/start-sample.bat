@echo off

setlocal

set root=%~d0%~p0..
set root="%root:"=%"

cd %root%
call bin\package.bat

if not %errorlevel% == 0 (
  exit /b 1
)

set appname=events
call bin\start-jetty.bat 9081
echo Go to: http://localhost:9081/%appname%
echo off

call bin\start-jetty.bat 9082
echo Go to: http://localhost:9082/%appname%
echo off

endlocal
