@echo off

del /F /S /Q *.class

REM http://techdem.centerkey.com/2009/05/javahome-command-script.html
for /R "%ProgramFiles%" %%I in (javac.exe*) do set JAVA_HOME=%%~dpI
set PATH=%PATH%;%JAVA_HOME%
javac -encoding UTF-8 -Djava.ext.dirs=src/main/libs src/main/Kampfrichterlehrgang.java


del ..\..\"KampfrichterLehrgang Gtm.zip"
for /R "%ProgramFiles%" %%I in (7z.exe*) do set SEVENZIP_HOME=%%~dpI
set PATH=%PATH%;%SEVENZIP_HOME%
7z a -tzip ..\..\"KampfrichterLehrgang Gtm.zip" ..\..\"KampfrichterLehrgang Gtm"
