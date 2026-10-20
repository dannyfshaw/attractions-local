@echo off
if exist data\attractions.mv.db del /q data\attractions.mv.db
if exist data\attractions.trace.db del /q data\attractions.trace.db
echo Database reset. Start the application again to recreate the H2 database and demo data.
pause
