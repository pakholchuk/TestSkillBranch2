package com.pakholchuk.testskillbranch2.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "UserDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS ("
                + "id INTEGER PRIMARY KEY,"
                + "name TEXT,"
                + "username TEXT,"
                + "email TEXT,"
                + "city TEXT,"
                + "lat TEXT,"
                + "lng TEXT,"
                + "phone TEXT,"
                + "website TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
