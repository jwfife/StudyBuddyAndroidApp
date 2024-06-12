package com.example.studybuddy;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.widget.Button;
import android.widget.DatePicker;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.sql.Date;
import java.util.Calendar;

public class RegisterAccount extends AppCompatActivity {

    /*
    TO DO:
    - create/reference database containing most US schools and universities
    (https://public.opendatasoft.com/explore/dataset/us-colleges-and-universities/table/?flg=en-us
    might work)
    - use that database as an option for a drop down menu when registering for an account
     */

    EditText email, password, firstName, lastName;
    Button register;
    DatabaseHelper DB;
    ImageView viewingEye;
    CardView card1, card2, card3, card4;
    private boolean is8char = false, hasNum = false, hasUpper = false, hasSpecialSymbol = false;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        email = findViewById(R.id.editEmailAddress);
        password = findViewById(R.id.editPassword);
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        register = findViewById(R.id.register);
        DB = new DatabaseHelper(this);
        viewingEye = findViewById(R.id.view_password_eye);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        register.setBackgroundColor(Color.parseColor(getString(R.color.myGrey)));

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email.getText().toString();
                String user_pass = password.getText().toString();
                String userFirstName = firstName.getText().toString();
                String userLastName = lastName.getText().toString();
                //Date birthDate = (Date) birthdateButton.getText();

              /*  if (!passwordValidate()) {
                    Toast.makeText(RegisterAccount.this, "Please enter all fields correctly.", Toast.LENGTH_SHORT).show();
                }
                else { */
                    if (user_email.isEmpty() || user_pass.isEmpty()){
                        Toast.makeText(RegisterAccount.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean checkForDupeEmail = DB.checkEmail(user_email);
                        if (!checkForDupeEmail) {
                            Boolean insert = DB.insertData(user_email, user_pass, userFirstName, userLastName);
                            if (insert) {
                                Toast.makeText(RegisterAccount.this, "Registration complete!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegisterAccount.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        else {
                            Toast.makeText(RegisterAccount.this, "User already exists! Please sign in or use another email.", Toast.LENGTH_SHORT).show();
                        }
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

    @SuppressLint("ResourceType")
    public Boolean passwordValidate(){

        String passwordStr = password.getText().toString();

        // 8 character
        if (password.length()>= 8) {
            is8char = true;
            card1.setCardBackgroundColor(Color.parseColor(getString((R.color.myBlue))));
        }
        else {
            is8char = false;
            //Toast.makeText(RegisterAccount.this, "Password must be at least 8 characters long.", Toast.LENGTH_SHORT).show();
            card1.setCardBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
        }


        //number!
        if (passwordStr.matches("(.*[0-9].*)")) {
            hasNum = true;
            card2.setCardBackgroundColor(Color.parseColor(getString(R.color.myBlue)));
        }
        else {
            hasNum = false;
            //Toast.makeText(RegisterAccount.this, "Password must have at least 1 number.", Toast.LENGTH_SHORT).show();
            card2.setCardBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
        }


        //upper case
        if (passwordStr.matches("(.*[A-Z].*)")) {
            hasUpper = true;
            card3.setCardBackgroundColor(Color.parseColor(getString(R.color.myBlue)));
        }
        else {
            hasUpper = false;
            //Toast.makeText(RegisterAccount.this, "Password must have at least 1 upper case letter.", Toast.LENGTH_SHORT).show();
           card3.setCardBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
        }


        //symbol
        if (passwordStr.matches("^(?=.*[_.()$&@]).*$")) {
            hasSpecialSymbol = true;
            card4.setCardBackgroundColor(Color.parseColor(getString(R.color.myBlue)));
        }
        else {
            hasSpecialSymbol = false;
            //Toast.makeText(RegisterAccount.this, "Password must contain at least 1 special character.", Toast.LENGTH_SHORT).show();
            card4.setCardBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
        }


        return is8char && hasNum && hasUpper && hasSpecialSymbol;


    }

    @SuppressLint("ResourceType")
    private void inputChanged(){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordValidate();
                if (passwordValidate()) {
                    register.setBackgroundColor(Color.parseColor(getString(R.color.myBlue)));
                }
                else {
                    register.setBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
//
        });
//
    }

}