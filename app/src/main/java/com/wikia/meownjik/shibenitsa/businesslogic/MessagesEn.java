package com.wikia.meownjik.shibenitsa.businesslogic;

public enum MessagesEn {
    WORD_INVALID_LENGTH("The word is either empty or too long!"),
    WORD_INVALID_SYMBOLS("The word shouldn't contain special symbols!"),
    WORD_INVALID_LANGUAGE("The word contains no letters legal for the chosen language!");

    String message;
    MessagesEn (String message) {
        this.message = message;
    }

    public String msg() {
        return message;
    }
}
