package com.example.studybuddy.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.db.CourseRepository;
import com.example.studybuddy.db.DatabaseCourseRepository;
import com.example.studybuddy.db.DatabaseEnrollmentManager;
import com.example.studybuddy.db.DatabaseHelper;
import com.example.studybuddy.db.EnrollmentManager;
import com.example.studybuddy.models.CourseModel;
import com.example.studybuddy.ui.messaging.MessagesPage;
import com.example.studybuddy.ui.profile.ProfilePage;

import java.util.ArrayList;
import java.util.List;

public class SearchForClasses extends AppCompatActivity {
    private String currentUserEmail = "";
    private List<CourseModel> courseModels = new ArrayList<>();
    private CourseRepository courseRepository;
    private EnrollmentManager enrollmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_classes);

        DatabaseHelper DB = DatabaseHelper.getInstance(this);
        courseRepository = new DatabaseCourseRepository(DB, this);
        enrollmentManager = new DatabaseEnrollmentManager(DB);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        setUpCourseModels();
        setupButtonListeners();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        Course_RecyclerViewAdapter adapter = new Course_RecyclerViewAdapter(
                this,
                courseModels,
                currentUserEmail,
                (courseID, courseName, isEnrolled) -> {
                    if (isEnrolled) {
                        enrollmentManager.enroll(currentUserEmail, courseID);
                    }
                    else {
                        enrollmentManager.unenroll(currentUserEmail, courseID);
                    }

                }
        );

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCourseModels() {
        courseModels = courseRepository.getAllCourses();
        courseRepository.insertCourses((courseModels));
    }

    private void setupButtonListeners() {
        setupNavigation(R.id.messagesPage, MessagesPage.class);
        setupNavigation(R.id.viewProfileButton, ProfilePage.class);
        setupNavigation(R.id.homePageButton, ActionSelection.class);
    }

    //sets up the listener for each activity
    private void setupNavigation(int viewID, Class<?> targetActivity) {
        findViewById(viewID).setOnClickListener(v -> {
            Intent i = new Intent(SearchForClasses.this, targetActivity);
            i.putExtra("key", currentUserEmail);
            startActivity(i);
        });
    }
}