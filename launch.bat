@echo off

REM TODO: check if jar doesn't exist prompt user to run
REM build.bat first

java -jar target/uya-medius-tool-*-jar-with-dependencies.jar $@

PAUSE
