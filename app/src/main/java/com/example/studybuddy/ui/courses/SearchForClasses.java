package com.example.studybuddy.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.db.DatabaseHelper;
import com.example.studybuddy.models.CourseModel;
import com.example.studybuddy.ui.messaging.MessagesPage;
import com.example.studybuddy.ui.profile.ProfilePage;

import java.util.ArrayList;

public class SearchForClasses extends AppCompatActivity {
    String currentUserEmail = "";
    ArrayList<CourseModel> courseModels = new ArrayList<>();
    ArrayList<String> addedCoursesStrings = new ArrayList<>();
    ArrayList<String> addedCoursesIDStrings = new ArrayList<>();
    ArrayList<String> removedCoursesStrings = new ArrayList<>();
    ArrayList<String> removedCoursesIDStrings = new ArrayList<>();
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_classes);
        DB = DatabaseHelper.getInstance(this);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        setUpCourseModels();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        Course_RecyclerViewAdapter adapter = new Course_RecyclerViewAdapter(
                this,
                courseModels,
                currentUserEmail,
                new Course_RecyclerViewAdapter.OnCourseToggleListener() {
                    @Override
                    public void onCourseToggled(String courseID, String courseName, boolean isEnrolled) {
                        String fullCourseString = courseID + " " + courseName;

                        if (isEnrolled) {
                            DB.insertEnrollment(currentUserEmail, courseID);
                            addedCoursesIDStrings.add(courseID);
                            addedCoursesStrings.add(fullCourseString);

                            /* Debugging:
                            Assign a boolean to the db.insertEnrollment
                            Log.d("DB_DEBUG", "Enrolled: " + courseID + " | Success: " + boolean);
                             */
                        }
                        else {
                            DB.removeEnrollment(currentUserEmail, courseID);
                            removedCoursesIDStrings.add(courseID);
                            removedCoursesStrings.add(fullCourseString);

                            /* Debugging:
                            Assign a boolean to the db.removeEnrollment
                            Log.d("DB_DEBUG", "Unenrolled: " + courseID + " | Success: " + boolean);
                             */
                        }

                    }
                });
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
        DB.insertCourses(courseModels);
    }


    //these might be necessary for adding the list of enrolled courses to the profile page
//    public void getAddedCourses(ArrayList<String> addedCourses, ArrayList<String> addedCoursesIDs) {
//        addedCoursesStrings.addAll(addedCourses);
//        addedCoursesIDStrings.addAll(addedCoursesIDs);
//    }
//
//    public void getRemovedCourses(ArrayList<String> removedCourses, ArrayList<String> removedCoursesIDs) {
//        removedCoursesStrings.addAll(removedCourses);
//        removedCoursesIDStrings.addAll(removedCoursesIDs);
//    }

}