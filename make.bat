@echo off
rem About maven package,
rem see https://blog.csdn.net/u014163312/article/details/127330574

if "%~1" == "clean" mvn clean & exit /b

mvn assembly:assembly -Dmaven.test.skip=true
