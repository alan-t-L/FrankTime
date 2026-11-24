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

FrankTime divides each hour into three 20-minute parts, called thirds.

Let **H** be the current hour and **H+1** be the following hour. The hour names wrap around midnight: 11 pm is followed by 12 am, and 11 am is followed by 12 pm.

### English rules

| Minute | FrankTime wording |
|---|---|
| 0 | `twenty past a third to H` |
| 1–19 | `[20 − minute] to a third past H` |
| 20 | `a third past H` |
| 21–39 | `[minute − 20] past two thirds to H+1` |
| 40 | `a third to H+1` |
| 41–59 | `[minute − 40] past a third to H+1` |

For example:

- `07:00` becomes `twenty past a third to seven`
- `07:15` becomes `five to a third past seven`
- `07:20` becomes `a third past seven`
- `07:30` becomes `ten past two thirds to eight`
- `07:40` becomes `a third to eight`
- `07:45` becomes `five past a third to eight`
- `07:59` becomes `nineteen past a third to eight`

### Offset wording

For offsets from 1 to 19:

- Multiples of 5 use the number alone: `five`, `ten`, or `fifteen`.
- Offset 1 uses `one`.
- Other offsets use the number followed by `minutes`: `three minutes`, `seventeen minutes`.

### German rules

| Minute | FrankZeit wording |
|---|---|
| 0 | `zwanzig nach einem Drittel vor H` |
| 1–19 | `[20 − Minute] vor einem Drittel nach H` |
| 20 | `ein Drittel nach H` |
| 21–39 | `[Minute − 20] nach zwei Dritteln vor H+1` |
| 40 | `ein Drittel vor H+1` |
| 41–59 | `[Minute − 40] nach einem Drittel vor H+1` |

For example:

- `07:00` becomes `zwanzig nach einem Drittel vor sieben`
- `07:15` becomes `fünf vor einem Drittel nach sieben`
- `07:20` becomes `ein Drittel nach sieben`
- `07:30` becomes `zehn nach zwei Dritteln vor acht`
- `07:40` becomes `ein Drittel vor acht`
- `07:45` becomes `fünf nach einem Drittel vor acht`
- `07:59` becomes `neunzehn nach einem Drittel vor acht`

### German offset wording

For offsets from 1 to 19:

- Multiples of 5 use the number alone: `fünf`, `zehn`, or `fünfzehn`.
- Offset 1 uses `eins`.
- Other offsets use the number followed by `Minuten`: `drei Minuten`, `siebzehn Minuten`.

## Installation

### Install the signed release APK

1. Download the latest release APK from the [FrankTime / FrankZeit v1.1 release page](https://github.com/alan-t-L/FrankTime/releases/tag/v1.1).
2. Select **FrankTime-release.apk** to download it.
3. On your Android phone, open the downloaded APK using the Files or Downloads app.
4. If Android asks whether to allow installation from the Files app, choose **Settings** and enable **Allow from this source**.
5. Return to the APK and choose **Install**.
6. Open FrankTime from the app drawer.

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
