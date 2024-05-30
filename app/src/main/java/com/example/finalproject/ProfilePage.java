package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {

    TextView yourName;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        View backToSelectionPage = findViewById(R.id.returnToSelection);
        backToSelectionPage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfilePage.this, ActionSelection.class);
                        startActivity(i);
                    }
                }
        );

        yourName = (TextView) findViewById(R.id.yourName);
        inputText = (EditText) findViewById(R.id.inputText);
    }

    public void updateText(View view){
        yourName.setText(inputText.getText());
    }
}