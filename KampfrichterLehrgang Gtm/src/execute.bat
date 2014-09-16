@echo off

del /F /S /Q *.class
java -classpath src/main/libs/*;. src.main.Kampfrichterlehrgang

