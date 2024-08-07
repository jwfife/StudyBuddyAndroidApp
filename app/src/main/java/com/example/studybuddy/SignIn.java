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
    - Add forgot password option
 */
public class SignIn extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    DatabaseHelper DB;
    ImageView viewingEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        email = findViewById(R.id.editEmailAddress);
        password = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.sign_in);
        DB = new DatabaseHelper(this);
        viewingEye = findViewById(R.id.view_password_eye);

        viewingEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
                    password.setTransformationMethod(new SingleLineTransformationMethod());
                }
                else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }

                password.setSelection(password.getText().length());
            }
        });

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

                        //passes the user's email through the intent to the next activity
                        intent.putExtra("key", user_email);
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