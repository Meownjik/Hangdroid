package com.wikia.meownjik.shibenitsa.database;

import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

public class WordModel {
    private int _id;
    private String word;
    private CategoryModel category;
    private String description;

    public WordModel(int id, String word, CategoryModel category, String description) {
        this._id = id;
        this.word = word;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return _id;
    }

    public String getWord() {
        return word;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public Languages getLang() {
        return category.getLang();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return word + " — " + category.toString();
    }
}
