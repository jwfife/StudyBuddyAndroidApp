package com.example.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class ProfilePage extends AppCompatActivity {

    TextView firstName, lastName, courseList;
    DatabaseHelper DB;
    String currentUserEmail = "";
    String currentUserFirst = "";
    String currentUserLast = "";
    String courseListStr = "";
    StringBuilder strBuilder = new StringBuilder(courseListStr);

    ArrayList<String> currAddedCourses = new ArrayList<>();
    ArrayList<String> savedCourseList = new ArrayList<>();
    ArrayList<String> addedCoursesIDs = new ArrayList<>();
    ArrayList<String> removedCourses = new ArrayList<>();
    ArrayList<String> removedCoursesIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        DB = new DatabaseHelper(this);

        courseList = findViewById(R.id.course_list_array);

        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
            currAddedCourses = extras.getStringArrayList("course_list");
            addedCoursesIDs = extras.getStringArrayList("courseID_list");
            removedCourses = extras.getStringArrayList("removed_course_list");
            removedCoursesIDs = extras.getStringArrayList("removed_course_ids");
            try {
                currentUserFirst = DB.getFirstName(currentUserEmail);
                currentUserLast = DB.getLastName(currentUserEmail);
                firstName.setText(currentUserFirst);
                lastName.setText(currentUserLast);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //to enroll in course through database
        if (addedCoursesIDs != null) {
            HashSet hashCourseIDs = new HashSet(addedCoursesIDs);
            ArrayList<String> uniqueCourseIDList = new ArrayList<>(hashCourseIDs);
            DB.insertEnrollment(currentUserEmail, uniqueCourseIDList);
            //just added 9/3
//            for (String a : uniqueCourseIDList){
//                currAddedCourses.add(DB.getCourseTitle(a));
//            }
        }

        if (currAddedCourses == null) {
            String noCoursesYet = "No courses yet";
            courseList.setText(noCoursesYet);
        }
        else {
            setCourseList();
        }

//        if (courseList.getText().toString().isEmpty()) {
//            courseList.setText("No courses yet");
//        }


        View viewMessages = findViewById(R.id.messagesPage);
        viewMessages.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfilePage.this, MessagesPage.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

        View editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfilePage.this, EditProfile.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

        View goToHomePage = findViewById(R.id.homePageButton);
        Bundle extrasToHome = new Bundle();
        extrasToHome.putString("key", currentUserEmail);
        extrasToHome.putStringArrayList("course_list", savedCourseList);
        extrasToHome.putStringArrayList("courseID_list", addedCoursesIDs);
        goToHomePage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfilePage.this, ActionSelection.class);
                        i.putExtras(extrasToHome);
                        startActivity(i);
                    }
                }
        );
    }

    public void setCourseList(){
        HashSet hashCourseStrs = new HashSet(currAddedCourses);
        ArrayList<String> uniqueCourseList = new ArrayList<>(hashCourseStrs);

        savedCourseList.addAll(uniqueCourseList);

        if (removedCourses != null) {
            for (int i = 0; i < removedCourses.size(); i++) {
                savedCourseList.remove(removedCourses.get(i));
            }
        }
        for (int k = 0; k < savedCourseList.size(); k++) {
            String str = "";

            //to make sure that duplicate newlines arent added
            if (!savedCourseList.get(k).contains("\n")){
                str = savedCourseList.get(k) + " \n";
            }
            else{
                str = savedCourseList.get(k);
            }
            savedCourseList.set(k, str);
            strBuilder.append(savedCourseList.get(k));
        }

        String finalCourseList = strBuilder.toString();
        courseList.setText(finalCourseList);
    }
}

