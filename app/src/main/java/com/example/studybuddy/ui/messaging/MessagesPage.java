package com.example.studybuddy.ui.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.ui.courses.ActionSelection;
import com.example.studybuddy.R;

public class MessagesPage extends AppCompatActivity {

    String currentUserEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_view);

        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        View backToSelection = findViewById(R.id.returnToPrev);
        backToSelection.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MessagesPage.this, ActionSelection.class);
                        i.putExtra("key", currentUserEmail);
                        startActivity(i);
                    }
                }
        );
    }
}