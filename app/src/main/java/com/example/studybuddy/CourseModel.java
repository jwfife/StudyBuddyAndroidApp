package com.example.studybuddy;

public class CourseModel {
    String courseName;
    String courseID;

    public CourseModel(String courseName, String courseID) {
        this.courseName = courseName;
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseID() {
        return courseID;
    }
}
