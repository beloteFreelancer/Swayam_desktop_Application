@echo off
title BBS Software
echo Starting BBS Software...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not found in PATH
    echo Please install Java 17 or later and try again
    pause
    exit /b 1
)

REM Set classpath to include the main JAR and all dependencies
set CLASSPATH=BBS_Software.jar;target/lib/*

REM Run the application
java -cp "%CLASSPATH%" BBS_Software

REM Keep window open if there's an error
if errorlevel 1 (
    echo.
    echo Application exited with an error
    pause
)