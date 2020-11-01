package com.wikia.meownjik.shibenitsa.businesslogic;

public class WordHandler {
    Languages lang;
    String availableLetters;
    final int maxLength = 20;

    public WordHandler(Languages lang) {
        this.lang = lang;
        this.availableLetters = lang.getAvailableLetters();
    }

    private String[] getLetters(String word) {
        return word.toLowerCase().split("(?!^)");
    }

    public boolean validateLength(String word) {
        if (word.length() == 0) {
            return false;
        }
        String[] letters = getLetters(word);
        int count = 0;
        for (String l : letters) {
            if(availableLetters.contains(l)) {
                count++;
            }
        }
        return (count > 0) && (count <= maxLength);
    }

    /**
     * Returns a word representation for the game, 
     * where all available letters are hidden, and all the other are left as they are.
     * @param word The word to hide letters in
     */
    public String hide(String word) {
        String[] letters = getLetters(word);
        String hiddenWord = "";
        for (String l : letters) {
            if(availableLetters.contains(l)) {
                hiddenWord += "*";
            }
            else {
                hiddenWord += l;
            }
        }
        return hiddenWord;
    }

    /**
     * Opens letter(s) in the hidden word.
     * @param hiddenWord The word with letters hidden by asterisks (retrieved with .hide method)
     * @param letter The character that should be opened.
     * @return Hidden word with the specified letter opened (if it's present)
     */
    public String unhide(String originalWord, String hiddenWord, String letter) {
        if(originalWord.length() != hiddenWord.length()) {
            throw new IllegalArgumentException("The originalWord and hiddenWord lengths should be the same!");
        }
        String[] originalLetters = getLetters(originalWord);
        String[] hiddenLetters = getLetters(hiddenWord);
        String unhiddenWord = "";
        for (int i = 0; i < originalLetters.length; i++) {
            if (originalLetters[i].equals(letter)) {
                //hiddenLetters[i] = letter;
                unhiddenWord += letter;
            }
            else {
                unhiddenWord += hiddenLetters[i];
            }
        }
        return unhiddenWord;
    }

}
