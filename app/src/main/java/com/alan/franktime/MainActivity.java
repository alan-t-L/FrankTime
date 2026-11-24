package com.alan.franktime;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "FrankTimePreferences";
    private static final String DEFAULT_LANGUAGE_KEY = "defaultLanguage";

    private boolean isGerman = false;
    private boolean savedDefaultIsGerman = false;

    private TextView mainTitleView;
    private TextView helpHintView;
    private TextView subtitleView;
    private TextView currentHeadingView;
    private TextView currentFrankTimeView;
    private TextView customHeadingView;
    private TextView customFrankTimeView;

    private Button convertCustomButton;
    private Button languageButton;
    private Button defaultLanguageButton;

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable updateClock = new Runnable() {
        @Override
        public void run() {
            updateCurrentTime();
            handler.postDelayed(this, 1000L);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTitleView = findViewById(R.id.mainTitleView);
        helpHintView = findViewById(R.id.helpHintView);
        subtitleView = findViewById(R.id.subtitleView);
        currentHeadingView = findViewById(R.id.currentHeadingView);
        currentFrankTimeView = findViewById(R.id.currentFrankTimeView);
        customHeadingView = findViewById(R.id.customHeadingView);
        customFrankTimeView = findViewById(R.id.customFrankTimeView);

        convertCustomButton = findViewById(R.id.convertCustomButton);
        languageButton = findViewById(R.id.languageButton);
        defaultLanguageButton = findViewById(R.id.defaultLanguageButton);

        hourPicker = findViewById(R.id.hourPicker);
        minutePicker = findViewById(R.id.minutePicker);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setValue(7);
        hourPicker.setWrapSelectorWheel(true);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setValue(15);
        minutePicker.setWrapSelectorWheel(true);

        mainTitleView.setOnClickListener(view -> showHelpDialog());

        convertCustomButton.setOnClickListener(view -> convertCustomTime());
        languageButton.setOnClickListener(view -> switchLanguage());
        defaultLanguageButton.setOnClickListener(view -> setDefaultLanguage());

        savedDefaultIsGerman = getSavedDefaultLanguageIsGerman();
        isGerman = savedDefaultIsGerman;

        updateCurrentTime();
        updateLanguage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(updateClock);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateClock);
    }

    private boolean getSavedDefaultLanguageIsGerman() {
        SharedPreferences preferences = getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        );

        return preferences.getBoolean(DEFAULT_LANGUAGE_KEY, false);
    }

    private void saveDefaultLanguageIsGerman(boolean german) {
        SharedPreferences preferences = getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        );

        preferences.edit()
            .putBoolean(DEFAULT_LANGUAGE_KEY, german)
            .apply();
    }

    private void updateCurrentTime() {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String time = String.format(Locale.UK, "%02d:%02d:%02d", hour, minute, second);
        ((TextView) findViewById(R.id.digitalClockView)).setText(time);

        if (second == 0) {
            updateCurrentFrankTime(hour, minute);
        }
    }

    private void updateCurrentFrankTime(int hour, int minute) {
        currentFrankTimeView.setText(FrankTimeFormatter.format(
            hour,
            minute,
            isGerman
        ));
    }

    private void switchLanguage() {
        isGerman = !isGerman;
        updateLanguage();
    }

    private void setDefaultLanguage() {
        savedDefaultIsGerman = !savedDefaultIsGerman;

        saveDefaultLanguageIsGerman(savedDefaultIsGerman);
        updateDefaultLanguageButtonText();
    }

    private void updateDefaultLanguageButtonText() {
        if (savedDefaultIsGerman) {
            defaultLanguageButton.setText("Set default language to English (EN)");
        } else {
            defaultLanguageButton.setText("Standardsprache auf Deutsch (DE) einstellen");
        }
    }

    private void showHelpDialog() {
        final Dialog helpDialog = new Dialog(this);

        helpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        helpDialog.setContentView(R.layout.dialog_help);
        helpDialog.getWindow().setLayout(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        );

        TextView helpDialogTitle = helpDialog.findViewById(R.id.helpDialogTitle);
        TextView helpText = helpDialog.findViewById(R.id.helpText);
        Button helpCloseButton = helpDialog.findViewById(R.id.helpCloseButton);

        if (isGerman) {
            helpDialogTitle.setText("FrankZeit Hilfe");
            helpText.setText(getGermanHelpText());
            helpCloseButton.setText("Schließen");
        } else {
            helpDialogTitle.setText("FrankTime Help");
            helpText.setText(getEnglishHelpText());
            helpCloseButton.setText("Close");
        }

        helpCloseButton.setOnClickListener(view -> helpDialog.dismiss());

        helpDialog.show();
    }

    private String getEnglishHelpText() {
        return "FrankTime is a different way of displaying and converting time.\n\n"
            + "CURRENT TIME\n\n"
            + "The digital clock shows the current time and updates every second.\n\n"
            + "The Current Time as FrankTime area updates automatically at the beginning of every minute. "
            + "It always shows the FrankTime equivalent of the current hour and minute.\n\n"
            + "SET CUSTOM TIME\n\n"
            + "Use the hour and minute selectors to choose a custom time. "
            + "The initial custom time is 07:15.\n\n"
            + "Tap Convert Custom Time to FrankTime to display the FrankTime equivalent of the selected time.\n\n"
            + "LANGUAGE\n\n"
            + "Tap Auf Deutsch (DE) wechseln to display the app in German.\n\n"
            + "Tap Switch to English (EN) to display the app in English.\n\n"
            + "Changing the displayed language affects the current session only.\n\n"
            + "DEFAULT LANGUAGE\n\n"
            + "The pale green button sets the language that FrankTime will use when it is next opened.\n\n"
            + "If the saved default is English, the button reads Standardsprache auf Deutsch (DE) einstellen. "
            + "Tap it to make German the startup default.\n\n"
            + "If the saved default is German, the button reads Set default language to English (EN). "
            + "Tap it to make English the startup default.\n\n"
            + "The button is a toggle. Its text always shows the language that will become the new startup default.\n\n"
            + "FrankTime was inspired by Francis 'Frank' Hodsman.";
    }

    private String getGermanHelpText() {
        return "FrankZeit ist eine andere Art, die Zeit anzuzeigen und umzurechnen.\n\n"
            + "AKTUELLE ZEIT\n\n"
            + "Die digitale Uhr zeigt die aktuelle Zeit an und wird jede Sekunde aktualisiert.\n\n"
            + "Der Bereich Aktuelle Zeit als FrankZeit wird automatisch zu Beginn jeder Minute aktualisiert. "
            + "Er zeigt immer die FrankZeit-Entsprechung der aktuellen Stunde und Minute.\n\n"
            + "BENUTZERDEFINIERTE ZEIT\n\n"
            + "Wählen Sie mit den Stunden- und Minutenwahlern eine benutzerdefinierte Zeit. "
            + "Die anfängliche benutzerdefinierte Zeit ist 07:15.\n\n"
            + "Tippen Sie auf Benutzerdefinierte Zeit einstellen (24 Stunden), um die FrankZeit-Entsprechung "
            + "der ausgewählten Zeit anzuzeigen.\n\n"
            + "SPRACHE\n\n"
            + "Tippen Sie auf Switch to English (EN), um die App auf Englisch anzuzeigen.\n\n"
            + "Tippen Sie auf Auf Deutsch (DE) wechseln, um die App auf Deutsch anzuzeigen.\n\n"
            + "Das Ändern der angezeigten Sprache wirkt sich nur auf die aktuelle Sitzung aus.\n\n"
            + "STANDARDSPRACHE\n\n"
            + "Die hellgrüne Schaltfläche legt die Sprache fest, die FrankZeit beim nächsten Öffnen verwendet.\n\n"
            + "Wenn Englisch als Standardsprache gespeichert ist, zeigt die Schaltfläche "
            + "Standardsprache auf Deutsch (DE) einstellen. Tippen Sie darauf, um Deutsch als Standardsprache festzulegen.\n\n"
            + "Wenn Deutsch als Standardsprache gespeichert ist, zeigt die Schaltfläche "
            + "Set default language to English (EN). Tippen Sie darauf, um Englisch als Standardsprache festzulegen.\n\n"
            + "Die Schaltfläche ist ein Schalter. Ihr Text zeigt immer die Sprache, die als neue Standardsprache festgelegt wird.\n\n"
            + "FrankZeit wurde von Francis 'Frank' Hodsman inspiriert.";
    }

    private void updateLanguage() {
        Calendar calendar = Calendar.getInstance();

        if (isGerman) {
            ((ScrollView) findViewById(R.id.mainScrollView)).setBackgroundColor(0xFFFFF8D6);

            mainTitleView.setText("FrankZeit");
            helpHintView.setText("Tippen Sie auf den Titel für Hilfe");
            subtitleView.setText("Eine revolution\u00e4re Art, die Zeit abzulesen");
            currentHeadingView.setText("AKTUELLE ZEIT");

            customHeadingView.setText("BENUTZERDEFINIERTE ZEIT EINSTELLEN\n(24 STUNDEN)");
            convertCustomButton.setText("Benutzerdefinierte Zeit einstellen\n(24 Stunden)");

            languageButton.setText("Switch to English (EN)");
            updateDefaultLanguageButtonText();

            currentFrankTimeView.setText(FrankTimeFormatter.format(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ));

            customFrankTimeView.setText("Benutzerdefinierte Zeit als FrankZeit");
        } else {
            ((ScrollView) findViewById(R.id.mainScrollView)).setBackgroundColor(0xFFE6E0F8);

            mainTitleView.setText("FrankTime");
            helpHintView.setText("Press title text for help");
            subtitleView.setText("A revolutionary way to tell the time");
            currentHeadingView.setText("CURRENT TIME");

            customHeadingView.setText("SET CUSTOM TIME (24H)");
            convertCustomButton.setText("Convert Custom Time to FrankTime");

            languageButton.setText("Auf Deutsch (DE) wechseln");
            updateDefaultLanguageButtonText();

            currentFrankTimeView.setText(FrankTimeFormatter.format(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            ));

            customFrankTimeView.setText("Custom Time as FrankTime");
        }
    }

    private void convertCustomTime() {
        customFrankTimeView.setText(FrankTimeFormatter.format(
            hourPicker.getValue(),
            minutePicker.getValue(),
            isGerman
        ));
    }
}
