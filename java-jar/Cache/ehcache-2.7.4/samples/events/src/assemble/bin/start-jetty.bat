@echo off

if not defined JAVA_HOME (
  echo JAVA_HOME is not defined
  exit /b 1
)

setlocal

set jetty_instance=%1

if  "%jetty_instance%" == "" ( 
  echo Need to specify which instance of Jetty: 9081 or 9082
  exit /b 1
)

pushd "%~d0%~p0"
for /f "tokens=*" %%a in ('call relative-paths.bat tc_install_dir') do set tc_install_dir=%%a
pushd
cd %tc_install_dir%
set tc_install_dir=%CD%
set tc_install_dir="%tc_install_dir:"=%"
popd
popd

set JAVA_HOME="%JAVA_HOME:"=%"
set root=%~d0%~p0..
set root="%root:"=%"
set jetty_work_dir=%root%\jetty6.1\%jetty_instance%
set jetty_home=%tc_install_dir%\third-party\jetty-6.1.15
set start_jar=%jetty_home%\start.jar
set /a stop_port=jetty_instance + 2

cd %jetty_work_dir%
mkdir logs
echo Starting Jetty %jetty_instance%...
start "Jetty %jetty_instance%" %JAVA_HOME%\bin\java -Dtc.install-root=%tc_install_dir% -Xmx256m -XX:MaxPermSize=128m -Dconfig.home=%jetty_work_dir% -Djetty.home=%jetty_home% -DSTOP.PORT=%stop_port% -DSTOP.KEY=secret -jar %start_jar% conf.xml 
echo.

endlocal
