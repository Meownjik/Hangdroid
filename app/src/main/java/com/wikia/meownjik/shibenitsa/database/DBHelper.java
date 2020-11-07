package com.wikia.meownjik.shibenitsa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wikia.meownjik.shibenitsa.MainActivity;

public class DBHelper extends SQLiteOpenHelper {

    public static final String LANG_TBL = "languages_tbl";
    public static final String CAT_TBL = "categories_tbl";
    public static final String WORD_TBL = "words_tbl";

    public DBHelper(Context context) {
        super(context, "shibenitsaDB", null, 1);
    }

    private void createLanguagesTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + LANG_TBL + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "name TEXT NOT NULL,"
                        + "is_applied INTEGER DEFAULT 0" //boolean
                        + ");"
        );
    }

    private void createCategoriesTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + CAT_TBL + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "name TEXT NOT NULL,"
                        + "language_id INTEGER NOT NULL,"
                        + "FOREIGN KEY (language_id) REFERENCES languages_tbl(id)"
                        + ");"
        );
    }

    private void createWordsTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + WORD_TBL + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "word TEXT NOT NULL,"
                        + "category_id INTEGER NOT NULL,"
                        + "description TEXT DEFAULT \"-\","
                        + "FOREIGN KEY (category_id) REFERENCES categories_tbl(id)"
                        + ");"
        );
    }

    private void fillLanguagesTable(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        for (String lang : new String[]{"English", "Русский", "Українська"}) {
            cv.put("name", lang);
            db.insert(LANG_TBL, null, cv);
            cv.clear();
        }
    }

    private int getLanguageIdByName(SQLiteDatabase db, String name) {
        Cursor c = db.query(LANG_TBL, new String[] {"id"},
                "name = ?", new String[] {name},
                null, null, null);
        c.moveToFirst();
        int langId = c.getInt(c.getColumnIndex("id"));
        c.close();
        return langId;
    }

    private void fillCategoriesTable(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        for (String cat : new String[]{"Съедобное", "Предметы", "Понятия"}) {
            cv.put("name", cat);

//            Cursor c = db.query(LANG_TBL, new String[] {"id"},
//                    "name = ?", new String[] {"Русский"},
//                    null, null, null);
//            c.moveToFirst();
//            int langId = c.getInt(c.getColumnIndex("id"));
            cv.put("language_id", getLanguageIdByName(db, "Русский"));

            db.insert(CAT_TBL, null, cv);
            cv.clear();
        }

        for (String cat : new String[]{"Food", "Items", "Abstraction"}) {
            cv.put("name", cat);
            cv.put("language_id", getLanguageIdByName(db, "English"));

            db.insert(CAT_TBL, null, cv);
            cv.clear();
        }

        for (String cat : new String[]{"Їстівне", "Речі", "Поняття"}) {
            cv.put("name", cat);
            cv.put("language_id", getLanguageIdByName(db, "Українська"));

            db.insert(CAT_TBL, null, cv);
            cv.clear();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(MainActivity.TAG, "DBHelper onCreate() database");
        db.execSQL("PRAGMA foreign_keys=on;");

        createLanguagesTable(db);
        createCategoriesTable(db);
        createWordsTable(db);

        fillLanguagesTable(db);
        fillCategoriesTable(db);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
