package com.wikia.meownjik.shibenitsa.businesslogic;


import java.util.ArrayList;

public class Game {
    Languages lang;
    private int trialsLeft;
    private ArrayList<String> usedLetters;
    private ArrayList<String> notUsedLetters;
    private String originalWord;
    private String hiddenWord;
    private WordHandler wordHandler;

    public Game(Languages language, String word, int trials) {
        this.lang = language;
        this.wordHandler = new WordHandler(lang);

        if(!wordHandler.validateSymbols(word)) {
            throw new IllegalArgumentException("The word shouldn't contain "
                    + wordHandler.getHider() + "symbol!");
        }
        if(!wordHandler.validateLength(word)) {
            throw new IllegalArgumentException("The word is either empty or too long!");
        }

        this.usedLetters = new ArrayList<>();
        this.notUsedLetters = lang.getAvailableLettersAsList();

        this.originalWord = word.toLowerCase();
        this.hiddenWord = wordHandler.hide(originalWord);

        this.trialsLeft = trials;
    }

    public Game(Languages language, String word) {
        this(language, word, 9);
    }

/*---- GETTERS ----------------------------------------------------------------*/
    public String getHiddenWord() {
        return hiddenWord;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public int getTrialsLeft() {
        return trialsLeft;
    }

    public ArrayList<String> getUsedLetters() {
        return usedLetters;
    }

    public ArrayList<String> getNotUsedLetters() {
        return notUsedLetters;
    }

/*---- BUSINESS LOGIC METHODS ----------------------------------------------------------------*/
    public int tryLetter(String letter) {
        if (usedLetters.contains(letter)) {
            throw new IllegalArgumentException("The letter " + letter + "is already tried!");
        }
        if (!notUsedLetters.contains(letter)) {
            throw new IllegalArgumentException("The letter " + letter + "can't be used!");
        }
        usedLetters.add(letter);
        notUsedLetters.remove(letter);

        if(originalWord.contains(letter)) { //Successful trial
            hiddenWord = wordHandler.unhide(originalWord, hiddenWord, letter);
        }
        else {
            trialsLeft--;
        }
        return trialsLeft;
    }

    public boolean isFailure() {
        return (trialsLeft <= 0) && !hiddenWord.equals(originalWord);
    }

    public boolean isVictory() {
        return (trialsLeft > 0) && hiddenWord.equals(originalWord);
    }
}
