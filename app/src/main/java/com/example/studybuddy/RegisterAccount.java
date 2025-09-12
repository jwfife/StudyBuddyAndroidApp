package com.example.studybuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class RegisterAccount extends AppCompatActivity {

    /*
    TO DO:
    - create/reference database containing most US schools and universities
    (https://public.opendatasoft.com/explore/dataset/us-colleges-and-universities/table/?flg=en-us
    might work)
    - use that database as an option for a drop down menu when registering for an account
     */

    EditText emailField, passwordField, firstNameField, lastNameField;
    Button register;
    private DatabaseHelper DB;
    ImageView viewPasswordEye;
    CardView checkbox1, checkbox2, checkbox3, checkbox4;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        emailField = findViewById(R.id.editEmailAddress);
        passwordField = findViewById(R.id.editPassword);
        firstNameField = findViewById(R.id.editFirstName);
        lastNameField = findViewById(R.id.editLastName);
        register = findViewById(R.id.register);
        DB = DatabaseHelper.getInstance(this);
        viewPasswordEye = findViewById(R.id.view_password_eye);
        checkbox1 = findViewById(R.id.card1);
        checkbox2 = findViewById(R.id.card2);
        checkbox3 = findViewById(R.id.card3);
        checkbox4 = findViewById(R.id.card4);
        register.setBackgroundColor(Color.parseColor(getString(R.color.myGrey)));

        viewPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewingEye();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailField.getText().toString();
                String userPassword = passwordField.getText().toString();
                String userFirstName = firstNameField.getText().toString();
                String userLastName = lastNameField.getText().toString();
                Boolean validUser = isValidUser(userEmail, userPassword);
                if (validUser) {
                    registerAccountInDatabase(userEmail, userPassword, userFirstName, userLastName);
                }
            }
        });

        View backToSignIn = findViewById(R.id.back_to_sign_in);
        backToSignIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(RegisterAccount.this, SignIn.class);
                        startActivity(i);
                    }
                }
        );
        inputChanged();
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

    public Boolean isValidUser(String userEmail, String userPassword) {
        if(!isUserPasswordEmpty(userEmail, userPassword)) {
            Boolean checkForDupeEmail = DB.checkEmail(userEmail);
            if (checkForDupeEmail) {
                Toast.makeText(RegisterAccount.this, "User already exists! Please sign in or use another email.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void registerAccountInDatabase(String userEmail, String userPassword, String userFirstName, String userLastName){
        Boolean insertSuccessful = DB.insertData(userEmail, userPassword, userFirstName, userLastName);
        if (insertSuccessful && isValidPassword()) {
            Toast.makeText(RegisterAccount.this, "Registration complete!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(RegisterAccount.this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean isUserPasswordEmpty(String userEmail, String userPassword) {
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(RegisterAccount.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @SuppressLint("ResourceType")
    public Boolean isValidPassword(){
        String passwordStr = passwordField.getText().toString();
        return (isMinimumLength(passwordStr)
                && hasNumber(passwordStr)
                && hasUppercase(passwordStr)
                && hasSpecialSymbol(passwordStr));
    }

    public Boolean isMinimumLength(String passwordStr){
        if (passwordStr.length()>= 8) {
            setCheckboxColor(checkbox1, true);
            return true;
        }
        else {
            setCheckboxColor(checkbox1, false);
            return false;
        }
    }

    public Boolean hasNumber(String passwordStr){
        if (passwordStr.matches("(.*[0-9].*)")) {
            setCheckboxColor(checkbox2, true);
            return true;
        }
        else {
            setCheckboxColor(checkbox2, false);
            return false;
        }
    }

    public Boolean hasUppercase(String passwordStr) {
        if (passwordStr.matches("(.*[A-Z].*)")) {
            setCheckboxColor(checkbox3, true);
            return true;
        }
        else {
            setCheckboxColor(checkbox3, false);
            return false;
        }
    }

    public Boolean hasSpecialSymbol(String passwordStr) {
        if (passwordStr.matches("^(?=.*[_.()$&@]).*$")) {
            setCheckboxColor(checkbox4, true);
            return true;
        }
        else {
            setCheckboxColor(checkbox4, false);
            return false;
        }
    }

    @SuppressLint("ResourceType")
    public void setCheckboxColor(CardView checkbox, Boolean passwordRequirementFulfilled) {
        if (passwordRequirementFulfilled) {
            checkbox.setCardBackgroundColor(Color.parseColor(getString(R.color.myBlue)));
        }
        else {
            checkbox.setCardBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
        }
    }
    @SuppressLint("ResourceType")
    private void inputChanged(){
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            //will constantly update the register button as the password is typed
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidPassword()) {
                    register.setBackgroundColor(Color.parseColor(getString(R.color.myBlue)));
                }
                else {
                    register.setBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}