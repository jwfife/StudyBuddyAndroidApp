package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        email = (EditText) findViewById(R.id.editEmailAddress);
        password = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.sign_in);
        DB = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email.getText().toString();
                String user_pass = password.getText().toString();

                if (user_email.isEmpty() || user_pass.isEmpty()) {
                    Toast.makeText(SignIn.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkEmailAndPassword = DB.checkEmailPassword(user_email, user_pass);
                    if (checkEmailAndPassword) {
                        Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActionSelection.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(SignIn.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        View continueAsGuest = findViewById(R.id.continue_as_guest);
        continueAsGuest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SignIn.this, ActionSelection.class);
                        startActivity(i);
                    }
                }
        );

        View signUp = findViewById(R.id.sign_up_register);
        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SignIn.this, RegisterAccount.class);
                        startActivity(i);
                    }
                }
        );



    }
}