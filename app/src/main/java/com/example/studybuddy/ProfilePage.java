package com.example.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class ProfilePage extends AppCompatActivity {

    TextView firstName;
    TextView lastName;
    DatabaseHelper DB;
    String currentUserFirst = "";
    String currentUserLast = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        DB = new DatabaseHelper(this);


        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        String currentUserEmail = "";
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        try {
            currentUserFirst = DB.getFirstName(currentUserEmail);
            currentUserLast = DB.getLastName(currentUserEmail);
            firstName.setText(currentUserFirst);
            lastName.setText(currentUserLast);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        View viewMessages = findViewById(R.id.messagesPage);
        viewMessages.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfilePage.this, MessagesPage.class);
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
                        startActivity(i);
                    }
                }
        );

        View goToHomePage = findViewById(R.id.homePageButton);
        goToHomePage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfilePage.this, ActionSelection.class);
                        startActivity(i);
                    }
                }
        );
    }
}