# copy_franktime_files_for_upload_to_github.ps1
# Creates a clean GitHub upload folder while preserving the Android folder structure.
# Excludes .gitignore, README.md, build output, signing files, IDE files, and local settings.

$ErrorActionPreference = 'Stop'

$ProjectRoot = $PSScriptRoot
$UploadRoot = Join-Path -Path $ProjectRoot -ChildPath 'github_upload_files'

if (Test-Path -LiteralPath $UploadRoot) {
    Remove-Item -LiteralPath $UploadRoot -Recurse -Force
}

New-Item -ItemType Directory -Path $UploadRoot | Out-Null

$ExcludedDirectories = @(
    '.gradle',
    '.idea',
    'build',
    'captures',
    '.externalNativeBuild',
    '.cxx'
)

$ExcludedFilePatterns = @(
    '*.jks',
    '*.keystore',
    '*.keystore.*',
    'keystore.properties',
    'signing.properties',
    'local.properties',
    '.gitignore',
    'README.md',
    '*.apk',
    '*.aab',
    '*.iml',
    '.DS_Store',
    'Thumbs.db'
)

function Test-ExcludedPath {
    param(
        [string]$FullPath,
        [string]$RelativePath
    )

    foreach ($Directory in $ExcludedDirectories) {
        $Pattern = '(^|[\\/])' + [regex]::Escape($Directory) + '([\\/]|$)'
        if ($RelativePath -match $Pattern) {
            return $true
        }
    }

    foreach ($Pattern in $ExcludedFilePatterns) {
        if ($RelativePath -like $Pattern) {
            return $true
        }

        $FileName = Split-Path -Path $FullPath -Leaf
        if ($FileName -like $Pattern) {
            return $true
        }
    }

    return $false
}

$AllFiles = Get-ChildItem -LiteralPath $ProjectRoot -Recurse -File -Force |
    Where-Object {
        $RelativePath = $_.FullName.Substring($ProjectRoot.Length).TrimStart('\')
        -not (Test-ExcludedPath -FullPath $_.FullName -RelativePath $RelativePath)
    }

foreach ($File in $AllFiles) {
    $RelativePath = $File.FullName.Substring($ProjectRoot.Length).TrimStart('\')
    $DestinationPath = Join-Path -Path $UploadRoot -ChildPath $RelativePath
    $DestinationDirectory = Split-Path -Path $DestinationPath -Parent

    if (-not (Test-Path -LiteralPath $DestinationDirectory)) {
        New-Item -ItemType Directory -Path $DestinationDirectory -Force | Out-Null
    }

    Copy-Item -LiteralPath $File.FullName -Destination $DestinationPath -Force
}

$CopiedFileCount = (Get-ChildItem -LiteralPath $UploadRoot -Recurse -File).Count

Write-Host ''
Write-Host "Created upload folder: $UploadRoot"
Write-Host "Copied $CopiedFileCount file(s)."
Write-Host ''
Write-Host 'Intentionally excluded:'
Write-Host '- .gitignore'
Write-Host '- README.md'
Write-Host '- Build output and temporary Gradle files'
Write-Host '- Android Studio / IntelliJ files'
Write-Host '- Signing keystores and signing-property files'
Write-Host '- APK and AAB files'
