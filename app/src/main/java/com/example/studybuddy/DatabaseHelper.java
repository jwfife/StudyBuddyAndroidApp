package com.example.studybuddy;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import androidx.annotation.Nullable;
import androidx.navigation.NavDestination;

public class DatabaseHelper extends  SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users (email TEXT primary key, password TEXT, firstname TEXT, lastname TEXT, age INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String email, String password, String firstname, String lastname, int age){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("age", age);

        long result = MyDB.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});

        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }
}

