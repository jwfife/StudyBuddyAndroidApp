package com.example.studybuddy.db;

import java.util.List;

public interface CourseRepository {
    List<String> getEnrolledCourseIDs(String email);
    String getCourseTitle(String courseID);
}
