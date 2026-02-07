# BBS Software Launcher
Write-Host "Starting BBS Software..." -ForegroundColor Green

# Check if Java is installed
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "Java found: $javaVersion" -ForegroundColor Yellow
} catch {
    Write-Host "ERROR: Java is not installed or not found in PATH" -ForegroundColor Red
    Write-Host "Please install Java 17 or later and try again" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

# Set classpath
$env:CLASSPATH = "BBS_Software.jar;target/lib/*"

# Run the application
try {
    java -cp $env:CLASSPATH BBS_Software
} catch {
    Write-Host "Error starting application: $_" -ForegroundColor Red
    Read-Host "Press Enter to exit"
}