package com.example.studybuddy.db;

import com.example.studybuddy.models.CourseModel;

import java.util.List;

public interface CourseRepository {
    List<CourseModel> getAllCourses();
    void insertCourses(List<CourseModel> courses);
    List<String> getEnrolledCourseIDs(String email);
    String getCourseTitle(String courseID);
}
