package com.example.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchForClasses extends AppCompatActivity {

    String currentUserEmail = "";

    ArrayList<CourseModel> courseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_classes);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        setUpCourseModels();

        Course_RecyclerViewAdapter adapter = new Course_RecyclerViewAdapter(this,
                courseModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        View viewMessages = findViewById(R.id.messagesPage);
        viewMessages.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SearchForClasses.this, MessagesPage.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

        View viewHomepage = findViewById(R.id.homePageButton);
        viewHomepage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SearchForClasses.this, ActionSelection.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

        View viewProfile = findViewById(R.id.viewProfileButton);
        String finalCurrentUserEmail = currentUserEmail;
        viewProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SearchForClasses.this, ProfilePage.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

    }

    private void setUpCourseModels(){
        String[] courseNames = getResources().getStringArray(R.array.compsci_courses_names);
        String[] courseIDs = getResources().getStringArray(R.array.cs_course_ids);

        for (int i = 0; i < courseNames.length; i++){
            courseModels.add(new CourseModel(courseNames[i],
                    courseIDs[i]));
        }

    }



}