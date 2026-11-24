param(
    [Parameter(Mandatory = $true)]
    [ValidateSet("Debug", "Release", "Signed-Release")]
    [string]$BuildType,

    [string]$KeystorePath,

    [string]$KeyAlias,

    [string]$KeystorePassword,

    [string]$KeyPassword
)

$ErrorActionPreference = "Stop"

Set-Location -Path $PSScriptRoot

if ($BuildType -eq "Signed-Release") {
    if (-not $KeystorePath -or -not $KeyAlias -or -not $KeystorePassword -or -not $KeyPassword) {
        throw "Signed-Release requires -KeystorePath, -KeyAlias, -KeystorePassword and -KeyPassword."
    }

    if (-not (Test-Path $KeystorePath)) {
        throw "Keystore file was not found: $KeystorePath"
    }
}

if ($BuildType -eq "Debug") {
    .\gradlew.bat clean assembleDebug

    $apkPath = ".\app\build\outputs\apk\debug\app-debug.apk"
    $deviceFolder = "/sdcard/FrankTimeApp/Debug Version"
    $deviceFileName = "FrankTime-debug.apk"
}
else {
    .\gradlew.bat clean assembleRelease

    $unsignedApkPath = ".\app\build\outputs\apk\release\app-release-unsigned.apk"

    if (-not (Test-Path $unsignedApkPath)) {
        throw "Unsigned release APK was not found after build: $unsignedApkPath"
    }

    if ($BuildType -eq "Signed-Release") {
        $buildToolsRoot = "$env:LOCALAPPDATA\Android\Sdk\build-tools"
        $apksigner = Get-ChildItem -Path $buildToolsRoot -Filter "apksigner.bat" -Recurse |
            Sort-Object FullName -Descending |
            Select-Object -First 1

        if (-not $apksigner) {
            throw "apksigner.bat was not found under: $buildToolsRoot"
        }

        $signedApkPath = ".\app\build\outputs\apk\release\FrankTime-signed-release.apk"

        if (Test-Path $signedApkPath) {
            Remove-Item $signedApkPath -Force
        }

        $signArguments = @(
            "sign"
            "--ks"
            $KeystorePath
            "--ks-key-alias"
            $KeyAlias
            "--ks-pass"
            "pass:$KeystorePassword"
            "--key-pass"
            "pass:$KeyPassword"
            "--out"
            $signedApkPath
            $unsignedApkPath
        )

        & $apksigner.FullName @signArguments

        if ($LASTEXITCODE -ne 0) {
            throw "APK signing failed."
        }

        & $apksigner.FullName verify $signedApkPath

        if ($LASTEXITCODE -ne 0) {
            throw "Signed APK verification failed."
        }

        $apkPath = $signedApkPath
    }
    else {
        $apkPath = $unsignedApkPath
    }

    $deviceFolder = "/sdcard/FrankTimeApp/Signed Release Version"
    $deviceFileName = "FrankTime-release.apk"
}

if (-not (Test-Path $apkPath)) {
    throw "APK was not found after build: $apkPath"
}

$resolvedApkPath = (Resolve-Path $apkPath).Path
$apkSizeMb = [math]::Round((Get-Item $resolvedApkPath).Length / 1MB, 2)

Write-Host ""
Write-Host "BUILD COMPLETE"
Write-Host "Build type: $BuildType"
Write-Host "Output APK: $resolvedApkPath"
Write-Host "APK size: $apkSizeMb MB"

Write-Host ""
Write-Host "COPYING APK TO DEVICE..."
adb shell mkdir -p "$deviceFolder"
adb push $resolvedApkPath "$deviceFolder/$deviceFileName"

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "COPY COMPLETE"
    Write-Host "Device folder: $deviceFolder"
    Write-Host "Device file: $deviceFolder/$deviceFileName"
    Write-Host "Source APK: $resolvedApkPath"
}
else {
    throw "ADB copy failed."
}
