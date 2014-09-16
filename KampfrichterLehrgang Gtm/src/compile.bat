@echo off

del /F /S /Q *.class

for /R %ProgramFiles% %%I in (javac.exe*) do set JAVA_HOME=%%~dpI
set PATH=%PATH%;%JAVA_HOME%
javac -Djava.ext.dirs=src/main/libs src/main/Kampfrichterlehrgang.java

