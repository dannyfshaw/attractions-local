@echo off
if exist data\attractions.mv.db del /q data\attractions.mv.db
if exist data\attractions.trace.db del /q data\attractions.trace.db
echo H2 database files removed. Restart the application to recreate and repopulate the database.
pause
