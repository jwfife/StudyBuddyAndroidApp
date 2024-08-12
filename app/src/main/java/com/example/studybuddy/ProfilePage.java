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
            try {
                currentUserFirst = DB.getFirstName(currentUserEmail);
                currentUserLast = DB.getLastName(currentUserEmail);
                firstName.setText(currentUserFirst);
                lastName.setText(currentUserLast);

                if (currAddedCourses != null) {
                    HashSet hashCourseStrs = new HashSet(currAddedCourses);
                    ArrayList<String> uniqueCourseList = new ArrayList<String>();
                    uniqueCourseList.addAll(hashCourseStrs);
                    savedCourseList.addAll(uniqueCourseList);

                    for (int i = 0; i < uniqueCourseList.size(); i++) {
                        StringBuilder str = new StringBuilder();
                        str.append(uniqueCourseList.get(i).toString());
                        str.append(" \n");
                        uniqueCourseList.set(i, String.valueOf(str));
                        strBuilder.append(uniqueCourseList.get(i));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        String finalCourseList = strBuilder.toString();

        courseList.setText(finalCourseList);

        if (courseList.getText().equals(null)) {
            courseList.setText("No courses yet");
        }


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

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        TextView firstName = findViewById(R.id.firstName);
//        TextView lastName = findViewById(R.id.lastName);
//        SharedPreferences.Editor prefEditor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
//
//        prefEditor.putString("userFirst", firstName.getText().toString());
//        prefEditor.putString("userLast", lastName.getText().toString());
//        prefEditor.commit();
//    }

}