# FrankTime / FrankZeit

FrankTime is an Android app that displays the current time and converts a selected 24-hour time into **FrankTime**. It also provides a German-language mode, **FrankZeit**.

The app was inspired by Francis “Frank” Hodsman.

## Features

- Displays the current digital time, updating every second.
- Converts the current time to FrankTime automatically at the start of each minute.
- Lets the user select an hour and minute and convert that custom time to FrankTime.
- Supports English and German display modes.
- Lets the user choose whether the app starts in English or German.
- Provides a scrollable Help window with a permanently visible Close button.
- Provides a compact single-screen layout with no scrolling required.

## FrankTime conversion rules

This section will describe the complete FrankTime conversion rules.

<!-- The full conversion rules will be added after review of FrankTimeFormatter.java. -->

## Installation

### Install the signed release APK

1. Download the latest signed release APK from the repository’s **Releases** page.
2. On your Android phone, open the downloaded APK using the Files or Downloads app.
3. If Android asks whether to allow installation from the Files app, choose **Settings** and enable **Allow from this source**.
4. Return to the APK and choose **Install**.
5. Open FrankTime from the app drawer.

The app requires Android 9 (API 28) or later.

### Build from source

1. Install Android Studio.
2. Open the FrankTime project.
3. Select the `debug` build variant.
4. Choose **Run** to install the debug version on a connected device or emulator.

## User instructions

### English

1. Tap **FrankTime** at the top of the screen to open Help.
2. The digital clock displays the current time and updates every second.
3. The **Current Time as FrankTime** area updates automatically at the beginning of every minute.
4. Use the hour and minute selectors to choose a custom time.
5. Tap **Convert Custom Time to FrankTime** to display the converted time.
6. Tap **Auf Deutsch (DE) wechseln** to switch the displayed language to German.
7. Tap the pale-green default-language button to choose the language used when the app next starts.
8. The default-language button is a toggle. Its label shows the language that will become the new startup default.

### Deutsch

1. Tippen Sie auf **FrankZeit** am oberen Bildschirmrand, um die Hilfe zu öffnen.
2. Die digitale Uhr zeigt die aktuelle Zeit an und wird jede Sekunde aktualisiert.
3. Der Bereich **Aktuelle Zeit als FrankZeit** wird automatisch zu Beginn jeder Minute aktualisiert.
4. Wählen Sie mit den Stunden- und Minutenwählern eine benutzerdefinierte Zeit.
5. Tippen Sie auf **Benutzerdefinierte Zeit einstellen (24 Stunden)**, um die Zeit umzurechnen.
6. Tippen Sie auf **Switch to English (EN)**, um die angezeigte Sprache auf Englisch umzustellen.
7. Tippen Sie auf die hellgrüne Standardsprache-Schaltfläche, um die Sprache festzulegen, die beim nächsten Start verwendet wird.
8. Die Standardsprache-Schaltfläche ist ein Schalter. Ihr Text zeigt die Sprache an, die als neue Standardsprache festgelegt wird.

## Credits

FrankTime was inspired by Francis “Frank” Hodsman.

FrankTime/FrankZeit App v1.1 © Alan Laurie 2026
