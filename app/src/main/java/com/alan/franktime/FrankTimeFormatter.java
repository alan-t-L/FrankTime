package com.alan.franktime;

public class FrankTimeFormatter {

    public static String format(int hour, int minute) {
        return format(hour, minute, false);
    }

    public static String format(int hour, int minute, boolean german) {
        return german ? formatGerman(hour, minute) : formatEnglish(hour, minute);
    }

    private static String formatEnglish(int hour, int minute) {
        String[] hourWords = {
            "twelve", "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve",
            "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven"
        };

        String[] offsetWords = {
            "zero", "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen"
        };

        String hourWord = hourWords[hour];
        String nextHourWord = hourWords[(hour + 1) % 24];

        if (minute == 0) {
            return "twenty past a third to " + hourWord;
        }

        if (minute <= 19) {
            int offset = 20 - minute;
            return offsetPhrase(offset, offsetWords, false) + " to a third past " + hourWord;
        }

        if (minute == 20) {
            return "a third past " + hourWord;
        }

        if (minute <= 39) {
            int offset = minute - 20;
            return offsetPhrase(offset, offsetWords, false) + " past two thirds to " + nextHourWord;
        }

        if (minute == 40) {
            return "a third to " + nextHourWord;
        }

        int offset = minute - 40;
        return offsetPhrase(offset, offsetWords, false) + " past a third to " + nextHourWord;
    }

    private static String formatGerman(int hour, int minute) {
        String[] hourWords = {
            "zw\u00f6lf", "eins", "zwei", "drei", "vier", "f\u00fcnf", "sechs",
            "sieben", "acht", "neun", "zehn", "elf", "zw\u00f6lf",
            "eins", "zwei", "drei", "vier", "f\u00fcnf", "sechs", "sieben",
            "acht", "neun", "zehn", "elf"
        };

        String[] offsetWords = {
            "null", "eins", "zwei", "drei", "vier", "f\u00fcnf", "sechs",
            "sieben", "acht", "neun", "zehn", "elf", "zw\u00f6lf",
            "dreizehn", "vierzehn", "f\u00fcnfzehn", "sechzehn", "siebzehn",
            "achtzehn", "neunzehn"
        };

        String hourWord = hourWords[hour];
        String nextHourWord = hourWords[(hour + 1) % 24];

        if (minute == 0) {
            return "zwanzig nach einem Drittel vor " + hourWord;
        }

        if (minute <= 19) {
            int offset = 20 - minute;
            return offsetPhrase(offset, offsetWords, true) + " vor einem Drittel nach " + hourWord;
        }

        if (minute == 20) {
            return "ein Drittel nach " + hourWord;
        }

        if (minute <= 39) {
            int offset = minute - 20;
            return offsetPhrase(offset, offsetWords, true) + " nach zwei Dritteln vor " + nextHourWord;
        }

        if (minute == 40) {
            return "ein Drittel vor " + nextHourWord;
        }

        int offset = minute - 40;
        return offsetPhrase(offset, offsetWords, true) + " nach einem Drittel vor " + nextHourWord;
    }

    private static String offsetPhrase(int offset, String[] offsetWords, boolean german) {
        String word = offsetWords[offset];

        if (offset == 1) {
            return "one minute".equals(word) ? word : word;
        }

        if (offset % 5 == 0) {
            return word;
        }

        return german ? word + " Minuten" : word + " minutes";
    }
}
