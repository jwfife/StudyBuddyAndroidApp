package com.example.studybuddy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ProfilePage extends AppCompatActivity {

    TextView yourName;
    EditText inputText;
    DatabaseHelper DB;
    private Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        yourName = (TextView) findViewById(R.id.yourName);
        DB = new DatabaseHelper(this);

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

    public void updateText(View view){
        yourName.setText(inputText.getText());
    }

}