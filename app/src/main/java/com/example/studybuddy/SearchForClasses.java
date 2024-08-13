package com.example.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchForClasses extends AppCompatActivity {
    String currentUserEmail = "";

    ArrayList<CourseModel> courseModels = new ArrayList<>();

    static ArrayList<String> addedCoursesStrings = new ArrayList<>();

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

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
        //String finalCurrentUserEmail = currentUserEmail;
        Bundle extrasToProfile = new Bundle();
        extrasToProfile.putString("key", currentUserEmail);
        extrasToProfile.putStringArrayList("course_list", addedCoursesStrings);
        viewProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SearchForClasses.this, ProfilePage.class);
                        i.putExtras(extrasToProfile);
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

        databaseHelper.insertCourses(courseModels);
        //get coursemodels arraylist from here?

    }

    public static void getAddedCourses(ArrayList<String> addedCourses){
        addedCoursesStrings.addAll(addedCourses);
//        for (int i = 0; i < addedCoursesStrings.size(); i++){
//            System.out.println(addedCoursesStrings.get(i));
//        }
        //addedCoursesStrings = (ArrayList<String>)addedCourses.clone();
    }



}