package com.wikia.meownjik.shibenitsa.businesslogic;

import java.util.ArrayList;
import java.util.Arrays;

public enum Languages {
    ENGLISH("English", "en", "abcdefghijklmnopqrstuvwxyz"),
    RUSSIAN("Русский","ru", "абвгдеёжзийклмнопрстуфхцчшщьыъэюя"),
    UKRAINIAN("Українська","uk", "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя");

    private String langName;
    private String langCode;
    private String availableLetters;

    Languages(String langName, String langCode, String availableLetters) {
        this.langName = langName;
        this.langCode = langCode;
        this.availableLetters = availableLetters;
    }

    public String getLangName() {
        return langName;
    }

    public String getLangCode() {
        return langCode;
    }

    public String getAvailableLetters() {
        return availableLetters;
    }

    public ArrayList<String> getAvailableLettersAsList() {
        return new ArrayList<String>(Arrays.asList(availableLetters.split("(?!^)")));
    }

    public static Languages getByName(String langName) {
        switch (langName) {
            case "Русский":
                return RUSSIAN;
                //break;
            case "Українська":
                return UKRAINIAN;
                //break;
            case "English":
            default:
                return ENGLISH;
                //break;
        }
    }
}
