package com.example.studybuddy.db;

import android.content.Context;

import com.example.studybuddy.R;
import com.example.studybuddy.models.CourseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class DatabaseCourseRepository implements CourseRepository {
    private final DatabaseHelper db;
    private final Context context;

    public DatabaseCourseRepository(DatabaseHelper db, Context context) {
        this.db = db;
        this.context = context;
    }

    @Override
    public List<CourseModel> getAllCourses() {
        String[] courseNames = context.getResources().getStringArray(R.array.compsci_courses_names);
        String[] courseIDs = context.getResources().getStringArray(R.array.cs_course_ids);

        List<CourseModel> courses = new ArrayList<>();
        for(int i = 0; i < courseNames.length; i++) {
            courses.add(new CourseModel(courseNames[i], courseIDs[i]));
        }
        return courses;
    }

    @Override
    public void insertCourses(List<CourseModel> courses) {
        db.insertCourses(courses);
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
