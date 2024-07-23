package com.example.studybuddy;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper extends  SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";
    private String currentUserFirstName;

    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users (email TEXT primary key, password TEXT, firstname TEXT, lastname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String email, String password, String firstname, String lastname){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);

        long result = MyDB.insert("users", null, contentValues);

        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});

        return cursor.getCount() > 0;

    }

    public String getFirstName(String email) throws SQLException {

        String url = "jdbc:sqlite:Login.db";
        String sqlStatement = "SELECT firstname FROM users WHERE email = " + email;

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sqlStatement)) {

            while (rs.next()) {
                //String currentUserFirstName = "";
                currentUserFirstName = rs.getString(1);
                return currentUserFirstName;
            }
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return currentUserFirstName;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?", new String[] {email, password});
        return cursor.getCount() > 0;
    }


    //method to write 1 (true) or 0 (false) into the currentuser column of the database
    //TO-DO: not sure how to insert into the correct record of the current user
    public void setCurrentUser(Integer truthValue){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("currentuser", truthValue);

        long result = MyDB.insert("users", null, contentValues);
    }

}

