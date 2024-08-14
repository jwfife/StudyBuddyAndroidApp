package com.example.studybuddy;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends  SQLiteOpenHelper{

    public static final String DBNAME = "Login.db";

    // Emulator Database Path = /data/data/com.example.studybuddy/databases/Login.db
    // Local database path = "C:\Users\jwf12\OneDrive\Desktop\Databases\Login.db"
    private String currentUserFirstName;
    private String currentUserLastName;


    //course ID, course title
    public HashMap<String, String> courses = new HashMap<>();

    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users (email TEXT primary key, password TEXT, firstname TEXT, lastname TEXT)");
        MyDB.execSQL("create Table courses (id TEXT primary key, title TEXT, description TEXT)");
        MyDB.execSQL("create Table enrolled (id INTEGER primary key AUTOINCREMENT, user_email TEXT, course_id TEXT)");
    }

    public void insertCourses(ArrayList<CourseModel> courseModels){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        for (int i = 0; i < courseModels.size(); i++) {
            String courseID = courseModels.get(i).getCourseID();
            String courseName = courseModels.get(i).getCourseName();
            courses.put(courseID, courseName);
        }

        ContentValues contentValues = new ContentValues();

        for (String i : courses.keySet()) {
            if (!checkCourseID(i)) {
                contentValues.put("id", i);
                contentValues.put("title", courses.get(i));
                MyDB.insert("courses", null, contentValues);
            }
        }
    }

    public void insertEnrollment(String currentUserEmail, ArrayList<String> courseID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        for (int i = 0; i < courseID.size(); i++){
            if (!checkEnrollment(currentUserEmail, courseID.get(i))){
                ContentValues contentValues = new ContentValues();
                contentValues.put("user_email", currentUserEmail);
                contentValues.put("course_id", courseID.get(i));
                MyDB.insert("enrolled", null, contentValues);
            }
        }
    }

    public void removeEnrollment(String currentUserEmail, String courseIDToRemove){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.delete("enrolled", "user_email = ? AND " +
                "course_id = ?", new String[] {currentUserEmail, courseIDToRemove} );
    }

    public Boolean checkEnrollment(String currentUserEmail, String courseID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from enrolled where user_email = ? and " +
                "course_id = ?", new String[] {currentUserEmail, courseID});
        return cursor.getCount() > 0;
    }

    public Boolean checkCourseID(String courseID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from courses where id = ?", new String[] {courseID});
        return cursor.getCount() > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists courses");
        MyDB.execSQL("drop Table if exists enrolled");
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
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});
        cursor.moveToFirst();
        int indexOfColumn = cursor.getColumnIndexOrThrow("firstname");
        currentUserFirstName = cursor.getString(indexOfColumn);
        
        return currentUserFirstName;
    }

    public String getLastName(String email) throws SQLException {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});
        cursor.moveToFirst();
        int indexOfColumn = cursor.getColumnIndexOrThrow("lastname");
        currentUserLastName = cursor.getString(indexOfColumn);

        return currentUserLastName;
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

