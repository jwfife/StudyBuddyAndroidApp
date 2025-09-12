package com.example.studybuddy.ui.profile;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.db.UserRepository;
import com.example.studybuddy.db.CourseRepository;
import com.example.studybuddy.db.DatabaseCourseRepository;
import com.example.studybuddy.db.DatabaseUserRepository;
import com.example.studybuddy.db.DatabaseHelper;
import com.example.studybuddy.ui.courses.ActionSelection;
import com.example.studybuddy.ui.messaging.MessagesPage;
import com.example.studybuddy.util.CourseFormatter;

import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class ProfilePage extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger(ProfilePage.class.getName());
    private TextView firstName, lastName, courseList;
    private String currentUserEmail = "";

    private UserRepository userRepository;
    private CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        courseList = findViewById(R.id.course_list_array);

        DatabaseHelper DB = DatabaseHelper.getInstance(this);
        userRepository = new DatabaseUserRepository(DB);
        courseRepository = new DatabaseCourseRepository(DB);

        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        //sets the user's enrolled course list
        displayUserInfo();
        displayCourseList();
        setupButtonListeners();
    }

    //displays user's first and last name
    private void displayUserInfo() {
        try {
            String first = userRepository.getFirstName(currentUserEmail);
            String last = userRepository.getLastName(currentUserEmail);
            firstName.setText(first);
            lastName.setText(last);
        } catch (SQLException e) {
            logger.severe("An error occurred in displayUserInfo: " + e.getMessage());
            firstName.setText(getString(R.string.error));
            lastName.setText(getString(R.string.error));
        }
    }

    //displays user's list of enrolled courses (if applicable)
    public void displayCourseList() {
        List<String> courseIDs = courseRepository.getEnrolledCourseIDs(currentUserEmail);
        List<String> courseTitles = new ArrayList<>();

        if (courseIDs != null) {
            for (String id : courseIDs
                 ) {
                courseTitles.add(courseRepository.getCourseTitle(id));
            }
        }
        String formattedCourses = CourseFormatter.formatCourses(courseIDs, courseTitles);
        courseList.setText(formattedCourses);
    }

    private void setupButtonListeners() {
        setupNavigation(R.id.messagesPage, MessagesPage.class);
        setupNavigation(R.id.editProfile, EditProfile.class);
        setupNavigation(R.id.homePageButton, ActionSelection.class);
    }

    //sets up the listener for each activity
    private void setupNavigation(int viewID, Class<?> targetActivity) {
        findViewById(viewID).setOnClickListener(v -> {
            Intent i = new Intent(ProfilePage.this, targetActivity);
            i.putExtra("key", currentUserEmail);
            startActivity(i);
        });
    }
}

