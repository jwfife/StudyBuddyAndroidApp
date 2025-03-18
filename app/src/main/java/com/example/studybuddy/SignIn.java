package com.example.studybuddy;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/* TODO:
    - Add forgot passwordField option
 */
public class SignIn extends AppCompatActivity {

    EditText emailField, passwordField;
    Button loginButton;
    DatabaseHelper DB;
    ImageView viewPasswordEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        emailField = findViewById(R.id.editEmailAddress);
        passwordField = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.sign_in);
        DB = new DatabaseHelper(this);
        viewPasswordEye = findViewById(R.id.view_password_eye);

        viewPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewingEye();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailField.getText().toString();
                String userPassword = passwordField.getText().toString();

                if(!isUserPasswordEmpty(userEmail, userPassword)) {
                    verifyLoginCredentials(userEmail, userPassword);
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
    public void setViewingEye() {
        if (passwordField.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
            passwordField.setTransformationMethod(new SingleLineTransformationMethod());
        }
        else {
            passwordField.setTransformationMethod(new PasswordTransformationMethod());
        }

        passwordField.setSelection(passwordField.getText().length());
    }

    public Boolean isUserPasswordEmpty(String userEmail, String userPassword) {
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(SignIn.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void verifyLoginCredentials(String userEmail, String userPassword) {
        Boolean checkEmailAndPassword = DB.checkEmailPassword(userEmail, userPassword);
        if (checkEmailAndPassword) {
            Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ActionSelection.class);

            //passes the user's emailField through the intent to the next activity
            intent.putExtra("key", userEmail);
            startActivity(intent);
        }
        else {
            Toast.makeText(SignIn.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}