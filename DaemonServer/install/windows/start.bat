@echo off

cd..
set BASEDIR=%CD%
set SERVICE_NAME=prunmgr
set MONITOR_PATH=%BASEDIR%\bin\prunmgr.exe

echo start %SERVICE_NAME% 

%MONITOR_PATH% //MR//%SERVICE_NAME%

:end