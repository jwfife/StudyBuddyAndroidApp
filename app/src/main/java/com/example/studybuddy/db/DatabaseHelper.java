package com.example.studybuddy.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.*;

import com.example.studybuddy.models.CourseModel;
import com.example.studybuddy.ui.profile.ProfilePage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends  SQLiteOpenHelper{

    private static final String DB_NAME = "Login.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    //course ID, course title
    public HashMap<String, String> courses = new HashMap<>();

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users (email TEXT primary key, password TEXT, firstname TEXT, lastname TEXT)");
        MyDB.execSQL("create Table courses (id TEXT primary key, title TEXT, description TEXT)");
        MyDB.execSQL("create Table enrolled (id INTEGER primary key AUTOINCREMENT, user_email TEXT, course_id TEXT)");
    }


    public void insertCourses(List<CourseModel> courseModels){
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
        MyDB.close();
    }

    public void insertEnrollment(String currentUserEmail, String courseID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_email", currentUserEmail);
        contentValues.put("course_id", courseID);
        MyDB.insert("enrolled", null, contentValues);
        MyDB.close();
        //return true; make return type boolean <- for debugging only
    }

    public void removeEnrollment(String currentUserEmail, String courseIDToRemove){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.delete("enrolled", "user_email = ? and course_id = ?",
                new String[] {currentUserEmail, courseIDToRemove} );
        MyDB.close();
        //return true; make return type boolean for debugging only
    }

    public Boolean checkEnrollment(String currentUserEmail, String courseID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from enrolled where user_email = ? and " +
                "course_id = ?", new String[] {currentUserEmail, courseID});
        return cursor.getCount() > 0;
    }

    public List<String> getEnrolledCourse(String currentUserEmail) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<String> courses = new ArrayList<>();

        Cursor cursor = MyDB.query(
                "enrolled",
                new String[]{"course_id"},
                "user_email = ?",
                new String[]{currentUserEmail},
                null,
                null,
                null
        );

        //in the case the user is enrolled in 1+ courses
        if (cursor != null) {
            //loops through cursor
            while (cursor.moveToNext()) {
                String courseId = cursor.getString(
                        cursor.getColumnIndexOrThrow("course_id"));
                courses.add(courseId);
            }
            cursor.close();
        }
        return courses;
    }

    public String getCourseTitle(String courseID) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String courseTitle = "";

        Cursor cursor = MyDB.query("courses",
                new String[]{"title"},
                "id = ?",
                new String[]{courseID},
                null,
                null,
                null
        );

        if (cursor != null) {
            while(cursor.moveToNext()) {
                courseTitle = cursor.getString(
                        cursor.getColumnIndexOrThrow("title"));
            }
            cursor.close();
        }

        return courseTitle;
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

    public Boolean insertUserData(String email, String password, String firstname, String lastname){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);

        long result = MyDB.insert("users", null, contentValues);
        MyDB.close();
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
        String currentUserFirstName = cursor.getString(indexOfColumn);
        
        return currentUserFirstName;
    }

    public String getLastName(String email) throws SQLException {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});
        cursor.moveToFirst();
        int indexOfColumn = cursor.getColumnIndexOrThrow("lastname");
        String currentUserLastName = cursor.getString(indexOfColumn);

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

