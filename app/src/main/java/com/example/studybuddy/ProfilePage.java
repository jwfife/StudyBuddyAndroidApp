package com.example.studybuddy;


import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ProfilePage extends AppCompatActivity {

    TextView firstName, lastName, courseList;
    private DatabaseHelper DB;
    String currentUserEmail = "";
    String currentUserFirst = "";
    String currentUserLast = "";
    String courseListStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        DB = DatabaseHelper.getInstance(this);
        courseList = findViewById(R.id.course_list_array);

        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            assignUserVariables(extras);
        }

        //sets the user's enrolled course list
        setCourseList();

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

    public void setCourseList() {
        //text for course list when no courses are selected
        if (DB.getEnrolledCourse(currentUserEmail) == null) {
            String noCoursesYet = "No courses yet";
            courseList.setText(noCoursesYet);
        }
        else {
            List<String> courseStrArray = getFullCourseStrings();

            if (courseStrArray.isEmpty()) {
                String noCoursesYet = "No courses yet";
                courseList.setText(noCoursesYet);
            }
            else {
                StringBuilder strBuilder = new StringBuilder(courseListStr);
                //adds newline character to each course if necessary
                for (int k = 0; k < courseStrArray.size(); k++) {
                    String courseString = setNewline(courseStrArray, k);
                    courseStrArray.set(k, courseString);
                    strBuilder.append(courseStrArray.get(k));
                }

                String finalCourseList = strBuilder.toString();
                courseList.setText(finalCourseList);
            }
        }
    }

    public List<String> getFullCourseStrings() {
        List<String> fullCourseStrings = new ArrayList<>();
        List<String> enrolledCourseIDs = DB.getEnrolledCourse(currentUserEmail);
        List<String> enrolledCourseTitles = new ArrayList<>();

        //grabs the course title for each course id
        for (String id : enrolledCourseIDs
        ) {
            enrolledCourseTitles.add(DB.getCourseTitles(id));
        }
        
        if (!enrolledCourseIDs.isEmpty()) {
            for (int i = 0; i < enrolledCourseIDs.size(); i++) {
                String fullCourseString = enrolledCourseIDs.get(i) +
                        " " + enrolledCourseTitles.get(i);

                fullCourseStrings.add(fullCourseString);
            }
        }
        return fullCourseStrings;
    }

    public String setNewline(List<String> updatedCourseList, int index) {
        String courseString;
        if (!hasNewline(updatedCourseList, index)) {
            courseString = updatedCourseList.get(index) + " \n";
        }
        else {
            courseString = updatedCourseList.get(index);
        }
        return courseString;
    }

    public boolean hasNewline(List<String> updatedCourseList, int index) {
        return updatedCourseList.get(index).contains("\n");
    }
}

