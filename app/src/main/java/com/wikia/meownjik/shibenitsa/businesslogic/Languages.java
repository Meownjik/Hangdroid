package com.wikia.meownjik.shibenitsa.businesslogic;

import java.util.ArrayList;
import java.util.Arrays;

public enum Languages {
    ENGLISH("en", "abcdefghijklmnopqrstuvwxyz"),
    RUSSIAN("ru", "абвгдеёжзийклмнопрстуфхцчшщьыъэюя"),
    UKRAINIAN("uk", "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя");

    private String langCode;
    private String availableLetters;

    Languages(String langCode, String availableLetters) {
        this.langCode = langCode;
        this.availableLetters = availableLetters;
    }

    public String getLangCode() {
        return langCode;
    }

    public String getAvailableLetters() {
        return availableLetters;
    }

    public ArrayList<String> getAvailableLettersAsList() {
        return (ArrayList<String>) Arrays.asList(availableLetters.split("(?!^)"));
    }
}
