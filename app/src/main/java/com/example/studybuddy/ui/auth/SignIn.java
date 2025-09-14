package com.example.studybuddy.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.db.auth.AuthRepository;
import com.example.studybuddy.db.auth.DatabaseAuthRepository;
import com.example.studybuddy.ui.courses.ActionSelection;
import com.example.studybuddy.db.DatabaseHelper;
import com.example.studybuddy.R;
import com.example.studybuddy.util.AuthValidator;


/* TODO:
    - Add forgot passwordField option
 */
public class SignIn extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton;
    private ImageView viewPasswordEye;
    private AuthRepository authRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        emailField = findViewById(R.id.editEmailAddress);
        passwordField = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.sign_in);

        viewPasswordEye = findViewById(R.id.view_password_eye);

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        authRepository = new DatabaseAuthRepository(db);

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        viewPasswordEye.setOnClickListener(v -> togglePasswordVisibility());

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (AuthValidator.isEmpty(email, password)) {
                Toast.makeText(SignIn.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                handleLogin(email, password);
            }
        });

        findViewById(R.id.sign_up_register).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterAccount.class));
        });
    }

    private void handleLogin(String email, String password) {
        if (authRepository.authenticate(email, password)) {
            Toast.makeText(SignIn.this, "Sign in successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ActionSelection.class);

            //passes the user's emailField through the intent to the next activity
            intent.putExtra("key", email);
            startActivity(intent);
        } else {
            Toast.makeText(SignIn.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private void togglePasswordVisibility() {
        if (passwordField.getTransformationMethod() instanceof PasswordTransformationMethod) {
            passwordField.setTransformationMethod(new SingleLineTransformationMethod());
        } else {
            passwordField.setTransformationMethod(new PasswordTransformationMethod());
        }
        passwordField.setSelection(passwordField.getText().length());
    }
}