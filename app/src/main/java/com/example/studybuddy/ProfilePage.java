package com.example.studybuddy;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ProfilePage extends AppCompatActivity {

    TextView firstName, lastName, courseList;
    DatabaseHelper DB;
    String currentUserEmail = "";
    String currentUserFirst = "";
    String currentUserLast = "";
    String courseListStr = "";

    ArrayList<String> addedCourses = new ArrayList<>();
    ArrayList<String> addedCoursesIDs = new ArrayList<>();
    ArrayList<String> updatedCourseList = new ArrayList<>();
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
            assignUserVariables(extras);
            assignCourseVariables(extras);
        }

        //text for course list when no courses are selected
        if (addedCourses == null) {
            String noCoursesYet = "No courses yet";
            courseList.setText(noCoursesYet);
        }
        else {
            setCourseList();
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
        extrasToHome.putStringArrayList("course_list", updatedCourseList);
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

    public void assignUserVariables(Bundle extras) {
        currentUserEmail = extras.getString("key");
        try {
            currentUserFirst = DB.getFirstName(currentUserEmail);
            currentUserLast = DB.getLastName(currentUserEmail);
            firstName.setText(currentUserFirst);
            lastName.setText(currentUserLast);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignCourseVariables(Bundle extras) {
        addedCourses = extras.getStringArrayList("course_list");
        addedCoursesIDs = extras.getStringArrayList("courseID_list");
        removedCourses = extras.getStringArrayList("removed_course_list");
        removedCoursesIDs = extras.getStringArrayList("removed_course_ids");
    }

    public void setCourseList() {
        LinkedHashSet<String> uniqueCourseList = new LinkedHashSet<String>(addedCourses);
        updatedCourseList.addAll(uniqueCourseList);

        if (removedCourses != null) {
            updatedCourseList = removeCoursesFromSavedList(updatedCourseList, removedCourses);
        }

        StringBuilder strBuilder = new StringBuilder(courseListStr);
        //adds newline character to each course if necessary
        for (int k = 0; k < updatedCourseList.size(); k++) {
            String courseString = setNewline(updatedCourseList, k);
            updatedCourseList.set(k, courseString);
            strBuilder.append(updatedCourseList.get(k));
        }

        String finalCourseList = strBuilder.toString();
        courseList.setText(finalCourseList);
    }

    public ArrayList<String> removeCoursesFromSavedList(ArrayList<String> updatedCourseList, ArrayList<String> removedCourses) {
        for (int i = 0; i < removedCourses.size(); i++) {
            updatedCourseList.remove(removedCourses.get(i));
        }
        return updatedCourseList;
    }

    public String setNewline(ArrayList<String> updatedCourseList, int index) {
        String courseString;
        if (!hasNewline(updatedCourseList, index)) {
            courseString = updatedCourseList.get(index) + " \n";
        }
        else {
            courseString = updatedCourseList.get(index);
        }
        return courseString;
    }

    public boolean hasNewline(ArrayList<String> updatedCourseList, int index) {
        return updatedCourseList.get(index).contains("\n");
    }
}

