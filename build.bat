@echo off

REM Remove old builds and rebuild
RM target/*.jar
mvn package
mvn assembly:assembly
