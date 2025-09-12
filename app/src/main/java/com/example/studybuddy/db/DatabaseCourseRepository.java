package com.example.studybuddy.db;

import java.util.List;

public class DatabaseCourseRepository implements CourseRepository {
    private final DatabaseHelper db;

    public DatabaseCourseRepository(DatabaseHelper db) {
        this.db = db;
    }

    @Override
    public List<String> getEnrolledCourseIDs(String email) {
        return db.getEnrolledCourse(email);
    }

    @Override
    public String getCourseTitle(String courseID) {
        return db.getCourseTitle(courseID);
    }
}
