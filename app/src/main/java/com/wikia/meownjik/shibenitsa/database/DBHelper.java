package com.wikia.meownjik.shibenitsa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.wikia.meownjik.shibenitsa.MainActivity;
import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

import static com.wikia.meownjik.shibenitsa.database.CRUD.*;
import static com.wikia.meownjik.shibenitsa.database.Tables.*;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME.getName(), null, 1);
        this.context = context;
    }

    private void createLanguagesTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + LANG_TBL.getName() + " ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + LANG_TBL_COLUMN_NAME.getName() + " TEXT NOT NULL UNIQUE,"
                        + LANG_TBL_COLUMN_IS_APPLIED.getName() + " INTEGER DEFAULT 0" //boolean
                        + ");"
        );
    }

    private void createCategoriesTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + CAT_TBL.getName() + " ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + CAT_TBL_COLUMN_NAME.getName() + " TEXT NOT NULL,"
                        + CAT_TBL_COLUMN_LANG_ID.getName() + " INTEGER NOT NULL,"
                        + "FOREIGN KEY (" + CAT_TBL_COLUMN_LANG_ID.getName() + ") REFERENCES "
                                + LANG_TBL.getName() + "(_id)"
                        + ");"
        );
    }

    private void createWordsTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + WORD_TBL.getName() + " ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + WORD_TBL_COLUMN_WORD.getName() + " TEXT NOT NULL,"
                        + WORD_TBL_COLUMN_CAT_ID.getName() + " INTEGER NOT NULL,"
                        + WORD_TBL_COLUMN_DESCRIPTION.getName() + " TEXT DEFAULT \"-\","
                        + "FOREIGN KEY (" + WORD_TBL_COLUMN_CAT_ID.getName() + ") REFERENCES "
                                + CAT_TBL.getName() + "(_id)"
                        + ");"
        );
    }

    private void fillLanguagesTable(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        for (String lang :
                new String[]{Languages.ENGLISH.getLangName(),
                        Languages.RUSSIAN.getLangName(), Languages.UKRAINIAN.getLangName()}) {
            cv.put("name", lang);
            db.insert(LANG_TBL.getName(), null, cv);
            cv.clear();
        }
    }

    private void fillCategoriesTable(SQLiteDatabase db) {
        for (String cat : new String[]{"Съедобное", "Предметы", "Понятия"}) {
            insertIntoCategories(db, cat, Languages.RUSSIAN.getLangName());
        }
        for (String cat : new String[]{"Food", "Items", "Abstraction"}) {
            insertIntoCategories(db, cat, Languages.ENGLISH.getLangName());
        }
        for (String cat : new String[]{"Їстівне", "Речі", "Поняття"}) {
            insertIntoCategories(db, cat, Languages.UKRAINIAN.getLangName());
        }
    }

    private void fillWordsTable(SQLiteDatabase db) {
        insertIntoWords(db, "Помидор", "Съедобное", Languages.RUSSIAN.getLangName(),
                "Дж. Вашингтона пытались этим отравить. Он ядовит, пока зелёный.");
        insertIntoWords(db, "Очки", "Предметы", Languages.RUSSIAN.getLangName(),
                "С этой вещью мартышке разобраться не под силу.");

        insertIntoWords(db, "Оселедець", "Поняття", Languages.UKRAINIAN.getLangName(),
                "Є у річці, є в козака.");

        insertIntoWords(db, "Dictionary", "Items", Languages.ENGLISH.getLangName(),
                "A thick book that might be useful for this game.");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(MainActivity.TAG, "DBHelper onCreate() database");
        Toast.makeText(context, "Installing the words database, please wait...",
                Toast.LENGTH_LONG).show();

        db.execSQL("PRAGMA foreign_keys=on;");

        createLanguagesTable(db);
        createCategoriesTable(db);
        createWordsTable(db);

        fillLanguagesTable(db);
        fillCategoriesTable(db);
        fillWordsTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
