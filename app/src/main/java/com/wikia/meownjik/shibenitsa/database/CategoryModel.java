package com.wikia.meownjik.shibenitsa.database;

import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

public class CategoryModel {
    private int _id;
    private String name;
    private Languages lang;

    public CategoryModel(int id, String name, Languages lang) {
        this._id = id;
        this.name = name;
        this.lang = lang;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public Languages getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return name + " (" + lang.getLangCode() + ")";
    }
}
