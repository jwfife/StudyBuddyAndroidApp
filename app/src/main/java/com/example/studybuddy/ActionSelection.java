package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActionSelection extends AppCompatActivity {

    String currentUserEmail ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_selection);


        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }



        View searchForClasses = findViewById(R.id.searchForClasses);
        searchForClasses.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, SearchForClasses.class);
                        startActivity(i);
                    }
                }
        );

        View viewProfile = findViewById(R.id.viewProfileButton);
        //String finalCurrentUserEmail = currentUserEmail;
        viewProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, ProfilePage.class);
                        //passes the user's email through the intent to the next activity
                        i.putExtra("key", currentUserEmail);
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
                        startActivity(i);
                    }
                }
        );

        View goToMap = findViewById(R.id.groupMap);
        goToMap.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, MapsActivity.class);
                        startActivity(i);
                    }
                }
        );
    }
}