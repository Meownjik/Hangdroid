package com.wikia.meownjik.shibenitsa.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

import java.util.ArrayList;
import java.util.Arrays;

import static com.wikia.meownjik.shibenitsa.database.Tables.*;

public class CRUD {
    private static final String TAG = "shibenitsaLogsDB";

    /* == ID getters == */
    public static int getLanguageIdByName(SQLiteDatabase db, String name) {
        Cursor c = db.query(LANG_TBL.getName(), new String[] {"_id"},
                "name = ?", new String[] {name},
                null, null, null);
        c.moveToFirst();
        int langId = c.getInt(c.getColumnIndex("_id"));
        c.close();
        return langId;
    }

    public static int getCategoryIdByNameAndLanguage(SQLiteDatabase db, String name, String language) {
        Cursor c = db.query(CAT_TBL.getName(), new String[] {"_id"},
                "name = ? AND language_id = ?",
                new String[] {name, String.valueOf(getLanguageIdByName(db, language))},
                null, null, null);
        c.moveToFirst();
        int categoryId = c.getInt(c.getColumnIndex("_id"));
        c.close();
        return categoryId;
    }

    /* == INSERT == */
    public static int insertIntoCategories(SQLiteDatabase db, String name, String language) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("language_id", getLanguageIdByName(db, language));
        long rowsAffected = db.insert(CAT_TBL.getName(), null, cv);
        cv.clear();

        return (int) rowsAffected;
    }

    public static int insertIntoWords(SQLiteDatabase db, String word, String category,
                                      String language, String description) {
        ContentValues cv = new ContentValues();
        cv.put("word", word);
        cv.put("category_id", getCategoryIdByNameAndLanguage(db,
                category, language));
        cv.put("description", description);
        long rowsAffected = db.insert(WORD_TBL.getName(), null, cv);
        cv.clear();

        return (int) rowsAffected;
    }

    /* == GET by ID == */
    public static Languages getLanguageById(SQLiteDatabase db, int id) {
        Cursor c = db.query(LANG_TBL.getName(), new String[] {"*"},
                "_id = ?", new String[] {String.valueOf(id)},
                null, null, null);
        c.moveToFirst();
        int nameColIndex = c.getColumnIndex(LANG_TBL_COLUMN_NAME.getName());
        String name = c.getString(nameColIndex);

        c.close();
        return Languages.getByName(name);
    }

    public static CategoryModel getCategoryById(SQLiteDatabase db, int id) {
        Cursor c = db.query(CAT_TBL.getName(), new String[] {"*"},
                "_id = ?", new String[] {String.valueOf(id)},
                null, null, null);
        c.moveToFirst();
        int idColIndex = c.getColumnIndex("_id");
        int nameColIndex = c.getColumnIndex(CAT_TBL_COLUMN_NAME.getName());
        int langIdColIndex = c.getColumnIndex(CAT_TBL_COLUMN_LANG_ID.getName());

        CategoryModel res = new CategoryModel(
                c.getInt(idColIndex),
                c.getString(nameColIndex),
                getLanguageById(db, c.getInt(langIdColIndex))
        );
        c.close();
        return res;
    }

    /* == GET by parameters == */

    /**
     * Selects words that contain given string.
     * @param db SQLiteDatabase to work with.
     * @param str The substring that words should contain.
     * @param exact If 0, requires exact match;
     *              if 1, requires starting with str;
     *              if 2, requires containing.
     */
    public static ArrayList<WordModel> selectWordsByString(SQLiteDatabase db, String str,
                                                           int exact, boolean matchCase) {
        //TODO merge duplicated code with selectAllWords()
        ArrayList<WordModel> words = new ArrayList<>();
        String wordName = matchCase ?
                WORD_TBL_COLUMN_WORD.getName() : WORD_TBL_COLUMN_WORD.getName().toLowerCase();
        String str1 = matchCase ? str : str.toLowerCase();
        String selector;
        switch (exact) {
            case 0:
                selector = " = '?'".replace("?", str1);
                break;
            case 1:
                selector = " LIKE '?%'".replace("?", str1);
                break;
            case 2:
            default:
                selector = " LIKE '%?%'".replace("?", str1);
                break;
        }

        Cursor c = db.query(WORD_TBL.getName(), new String[] {"*"},
                wordName + selector,
                null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int wordColIndex = c.getColumnIndex(WORD_TBL_COLUMN_WORD.getName());
            int catIdColIndex = c.getColumnIndex(WORD_TBL_COLUMN_CAT_ID.getName());
            int descrColIndex  = c.getColumnIndex(WORD_TBL_COLUMN_DESCRIPTION.getName());
            do {
                WordModel wordModel = new WordModel(
                        c.getInt(idColIndex),
                        c.getString(wordColIndex),
                        getCategoryById(db, c.getInt(catIdColIndex)),
                        c.getString(descrColIndex)
                );
                words.add(wordModel);
            } while (c.moveToNext());
        } else {
            Log.i(TAG, "0 rows at table " + WORD_TBL.getName());
        }

        c.close();
        return words;
    }

    /**
     * Selects words that either contain given string, or their category contain this string,
     * or their language code equals this string
     * @param db SQLiteDatabase to work with
     * @param str The substring that words, category or language code should contain
     */
    public static ArrayList<WordModel> selectWordsByString2(SQLiteDatabase db, String str) {
        //TODO rewrite with SQL select
        ArrayList<WordModel> allWords = selectAllWords(db);
        ArrayList<WordModel> chosenWords = new ArrayList<>();
        boolean isLangCode = false;
        ArrayList<Languages> langs = new ArrayList<Languages>(Arrays.asList(Languages.values()));
        for (Languages l : langs) {
            if(l.getLangCode().equals(str)) {
                isLangCode = true;
                break;
            }
        }

        if(isLangCode) {
            for (WordModel w : allWords) {
                if (w.getCategory().getLang().getLangCode().equals(str)) {
                    chosenWords.add(w);
                }
            }
        }
        else {
            for (WordModel w : allWords) {
                if (w.getWord().toLowerCase().contains(str.toLowerCase())
                        || w.getCategory().getName().toLowerCase().contains(str.toLowerCase())) {
                    chosenWords.add(w);
                }
            }
        }
        return chosenWords;
    }

    /* == GET all == */
    public static ArrayList<WordModel> selectAllWords(SQLiteDatabase db) {
        ArrayList<WordModel> words = new ArrayList<>();
        Cursor c = db.query(WORD_TBL.getName(), new String[] {"*"},
                null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int wordColIndex = c.getColumnIndex(WORD_TBL_COLUMN_WORD.getName());
            int catIdColIndex = c.getColumnIndex(WORD_TBL_COLUMN_CAT_ID.getName());
            int descrColIndex  = c.getColumnIndex(WORD_TBL_COLUMN_DESCRIPTION.getName());
            do {
                WordModel wordModel = new WordModel(
                        c.getInt(idColIndex),
                        c.getString(wordColIndex),
                        getCategoryById(db, c.getInt(catIdColIndex)),
                        c.getString(descrColIndex)
                );
                words.add(wordModel);
            } while (c.moveToNext());
        } else {
            Log.i(TAG, "0 rows at table " + WORD_TBL.getName());
        }

        c.close();
        return words;
    }

    public static ArrayList<CategoryModel> selectAllCategories(SQLiteDatabase db) {
        ArrayList<CategoryModel> cats = new ArrayList<>();
        Cursor c = db.query(CAT_TBL.getName(), new String[] {"*"},
                null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int nameColIndex = c.getColumnIndex(CAT_TBL_COLUMN_NAME.getName());
            int langIdColIndex = c.getColumnIndex(CAT_TBL_COLUMN_LANG_ID.getName());
            do {
                CategoryModel catModel = new CategoryModel(
                        c.getInt(idColIndex),
                        c.getString(nameColIndex),
                        getLanguageById(db, c.getInt(langIdColIndex))
                );
                cats.add(catModel);
            } while (c.moveToNext());
        } else {
            Log.i(TAG, "0 rows at table " + CAT_TBL.getName());
        }

        c.close();
        return cats;
    }

    /* == UPDATE == */
    public static int updateWords(SQLiteDatabase db, int id, String word, String category,
                                      String language, String description) {
        ContentValues cv = new ContentValues();
        cv.put("word", word);
        cv.put("category_id", getCategoryIdByNameAndLanguage(db,
                category, language));
        cv.put("description", description);
        long rowsAffected = db.update(WORD_TBL.getName(), cv,
                "_id = ?", new String[] {String.valueOf(id)});
        cv.clear();

        return (int) rowsAffected;
    }

    /* == DELETE == */
    public static int deleteFromWords(SQLiteDatabase db, int id) {
        long rowsAffected = db.delete(WORD_TBL.getName(), "_id = ?",
                new String[] {String.valueOf(id)});
        return (int) rowsAffected;
    }


}
