package com.example.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SearchForClasses extends AppCompatActivity {
    String currentUserEmail = "";
    ArrayList<CourseModel> courseModels = new ArrayList<>();
    static ArrayList<String> addedCoursesStrings = new ArrayList<>();
    static ArrayList<String> addedCoursesIDStrings = new ArrayList<>();
    static ArrayList<String> removedCoursesStrings = new ArrayList<>();
    static ArrayList<String> removedCoursesIDStrings = new ArrayList<>();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_classes);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        setUpCourseModels();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        Course_RecyclerViewAdapter adapter = new Course_RecyclerViewAdapter(this,
                courseModels, currentUserEmail);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        View viewMessagesPage = findViewById(R.id.messagesPage);
        viewMessagesPage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SearchForClasses.this, MessagesPage.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

        View viewHomePage = findViewById(R.id.homePageButton);
        viewHomePage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SearchForClasses.this, ActionSelection.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );

        View viewProfilePage = findViewById(R.id.viewProfileButton);
        Bundle extrasToProfile = new Bundle();
        extrasToProfile.putString("key", currentUserEmail);
        extrasToProfile.putStringArrayList("course_list", addedCoursesStrings);
        extrasToProfile.putStringArrayList("courseID_list", addedCoursesIDStrings);
        extrasToProfile.putStringArrayList("removed_course_list", removedCoursesStrings);
        extrasToProfile.putStringArrayList("removed_course_ids", removedCoursesIDStrings);
        viewProfilePage.setOnClickListener(
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

    private void setUpCourseModels() {
        String[] courseNames = getResources().getStringArray(R.array.compsci_courses_names);
        String[] courseIDs = getResources().getStringArray(R.array.cs_course_ids);

        //adds the courses for each of the recyclerview models/entities
        for (int i = 0; i < courseNames.length; i++) {
            courseModels.add(new CourseModel(courseNames[i],
                    courseIDs[i]));
        }
        databaseHelper.insertCourses(courseModels);
    }

    public static void getAddedCourses(ArrayList<String> addedCourses, ArrayList<String> addedCoursesIDs) {
        addedCoursesStrings.addAll(addedCourses);
        addedCoursesIDStrings.addAll(addedCoursesIDs);
    }

    public static void getRemovedCourses(ArrayList<String> removedCourses, ArrayList<String> removedCoursesIDs) {
        removedCoursesStrings.addAll(removedCourses);
        removedCoursesIDStrings.addAll(removedCoursesIDs);
    }

}