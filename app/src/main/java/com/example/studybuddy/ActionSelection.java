package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ActionSelection extends AppCompatActivity {

    String currentUserEmail = "";
    ArrayList<String> addedCourseList = new ArrayList<>();
    ArrayList<String> addedCoursesIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_selection);

        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
            addedCourseList = extras.getStringArrayList("course_list");
            addedCoursesIDs = extras.getStringArrayList("courseID_list");
        }

        View searchForClasses = findViewById(R.id.searchForClasses);
        Bundle extrasToNextPage = new Bundle();
        extrasToNextPage.putString("key", currentUserEmail);
        extrasToNextPage.putStringArrayList("course_list", addedCourseList);
        searchForClasses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, SearchForClasses.class);
                        i.putExtras(extrasToNextPage);
                        startActivity(i);
                    }
                }
        );

        View viewProfile = findViewById(R.id.viewProfileButton);
        viewProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, ProfilePage.class);
                        //passes the user's email through the intent to the next activity
                        i.putExtras(extrasToNextPage);
                        startActivity(i);
                    }
                }
        );

        View viewMessages = findViewById(R.id.messagesPage);
        viewMessages.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, MessagesPage.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

        View viewMapPage = findViewById(R.id.groupMap);
        viewMapPage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, MapsActivity.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );
    }
}