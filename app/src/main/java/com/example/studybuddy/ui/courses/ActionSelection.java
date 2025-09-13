package com.example.studybuddy.ui.courses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studybuddy.R;
import com.example.studybuddy.ui.maps.MapsActivity;
import com.example.studybuddy.ui.messaging.MessagesPage;
import com.example.studybuddy.ui.profile.ProfilePage;

import java.util.ArrayList;

public class ActionSelection extends AppCompatActivity {

    private String currentUserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_selection);

        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        setupNavigation(R.id.searchForClasses, SearchForClasses.class);
        setupNavigation(R.id.viewProfileButton, ProfilePage.class);
        setupNavigation(R.id.messagesPage, MessagesPage.class);
        setupNavigation(R.id.groupMap, MapsActivity.class);
    }

    private void setupNavigation(int viewID, Class<?> targetActivity) {
        findViewById(viewID).setOnClickListener(v -> {
            Intent i = new Intent(ActionSelection.this, targetActivity);
            i.putExtra("key", currentUserEmail);
            startActivity(i);
        });
    }
}