package com.wikia.meownjik.shibenitsa.database;

public enum Tables {
    DB_NAME("shibenitsaDB"),

    LANG_TBL("languages_tbl"), //This table shouldn't be edited by user
    LANG_TBL_COLUMN_NAME("name"),
    LANG_TBL_COLUMN_IS_APPLIED("is_applied"),

    CAT_TBL("categories_tbl"), //This table might be editable for user
    CAT_TBL_COLUMN_NAME("name"),
    CAT_TBL_COLUMN_LANG_ID("language_id"),

    WORD_TBL("words_tbl"), //This table will be editable for user
    WORD_TBL_COLUMN_WORD("word"),
    WORD_TBL_COLUMN_CAT_ID("category_id"),
    WORD_TBL_COLUMN_DESCRIPTION("description");

    private String name;

    Tables(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
