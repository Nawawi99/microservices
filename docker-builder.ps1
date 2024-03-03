param (
    [string]$projectsDirectory = $PWD.Path  # Set the default path to the current directory
)

# Get a list of directories in the projects directory that contain a pom.xml file
$projectDirectories = Get-ChildItem -Path $projectsDirectory -Directory | Where-Object { Test-Path (Join-Path $_.FullName "pom.xml") }

# Check if any project directories were found
if ($projectDirectories.Count -eq 0) {
    Write-Host "No projects found with a pom.xml file in the specified directory."
    exit
}

# Iterate through each project directory
foreach ($projectDir in $projectDirectories) {
    Write-Host "Building project in $($projectDir.Name)..."

    # Change the current directory to the project directory
    Set-Location -Path $projectDir.FullName

    # Execute Maven commands
    mvn clean compile jib:build

    # Change back to the original directory
    Set-Location -Path $projectsDirectory
}

Write-Host "All projects built successfully."
