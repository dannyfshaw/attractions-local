@echo off
setlocal
where java >nul 2>nul
if errorlevel 1 (
  echo Java was not found. Install JDK 21 and ensure JAVA_HOME/PATH are configured.
  pause
  exit /b 1
)
where mvn >nul 2>nul
if errorlevel 1 (
  echo Maven was not found. Install Maven 3.9+ and add mvn to PATH.
  pause
  exit /b 1
)
call mvn clean spring-boot:run
