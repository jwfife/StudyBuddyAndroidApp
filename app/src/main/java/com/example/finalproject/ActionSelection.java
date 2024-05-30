package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActionSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_selection);

        View backToMain = findViewById(R.id.backToMain);
        backToMain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, SignIn.class);
                        startActivity(i);
                    }
                }
        );

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
        viewProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActionSelection.this, ProfilePage.class);
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
    }
}