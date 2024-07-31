package com.example.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String currentUserEmail = "";
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
        if (extras != null) {
            currentUserEmail = extras.getString("key");
            System.out.println(currentUserEmail);
            try {
                currentUserFirst = DB.getFirstName(currentUserEmail);
                currentUserLast = DB.getLastName(currentUserEmail);
                firstName.setText(currentUserFirst);
                lastName.setText(currentUserLast);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

//        SharedPreferences prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
//        if (prefs.contains("userFirst") && prefs.contains("userLast")) {
//            firstName.setText(prefs.getString("userFirst", ""));
//            lastName.setText(prefs.getString("userLast", ""));
//        }

//        try {
//            currentUserFirst = DB.getFirstName(currentUserEmail);
//            currentUserLast = DB.getLastName(currentUserEmail);
//            firstName.setText(currentUserFirst);
//            lastName.setText(currentUserLast);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
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
        goToHomePage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfilePage.this, ActionSelection.class);
                        i.putExtra("key", currentUserEmail);
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