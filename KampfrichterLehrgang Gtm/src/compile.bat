@echo off

del /F /S /Q *.class
javac -Djava.ext.dirs=src/main/libs src/main/Kampfrichterlehrgang.java

