package com.example.studybuddy.db.enrollment;

public interface EnrollmentManager {
    void enroll(String userEmail, String courseID);
    void unenroll(String userEmail, String courseID);
}
