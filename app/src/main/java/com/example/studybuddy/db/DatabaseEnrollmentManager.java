package com.example.studybuddy.db;

public class DatabaseEnrollmentManager implements EnrollmentManager{

    private final DatabaseHelper db;

    public DatabaseEnrollmentManager(DatabaseHelper db){
        this.db = db;
    }

    @Override
    public void enroll(String userEmail, String courseID) {
        db.insertEnrollment(userEmail, courseID);
    }

    @Override
    public void unenroll(String userEmail, String courseID) {
        db.removeEnrollment(userEmail, courseID);
    }
}
