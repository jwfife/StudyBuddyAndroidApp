package com.example.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class ProfilePage extends AppCompatActivity {

    TextView yourName;
    DatabaseHelper DB;
    private Button editProfile;
    String currentUserFirst = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        yourName = findViewById(R.id.yourName);
        DB = new DatabaseHelper(this);


        //retrieves the logged in user's email from the passed intent
        Bundle extras = getIntent().getExtras();
        String currentUserEmail = "";
        if (extras != null) {
            currentUserEmail = extras.getString("key");
        }

        try {
            currentUserFirst = DB.getFirstName(currentUserEmail);
            yourName.setText(currentUserFirst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
    }
}