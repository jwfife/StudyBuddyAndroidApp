package com.example.studybuddy;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.util.Calendar;

public class RegisterAccount extends AppCompatActivity {

    /*
    TO DO (MAYBE):
    - create/reference database containing most US schools and universities
    (https://public.opendatasoft.com/explore/dataset/us-colleges-and-universities/table/?flg=en-us might work)
    - use that database as an option for a drop down menu when registering for an account
     */

    private DatePickerDialog birthdatePickerDialog;
    private Button birthdateButton;
    EditText email, password, firstName, lastName;
    Button register;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        initDatePicker();
        birthdateButton = findViewById(R.id.editDateOfBirth);
        birthdateButton.setText(getTodaysDate());

        email = findViewById(R.id.editEmailAddress);
        password = findViewById(R.id.editPassword);
        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        register = findViewById(R.id.register);
        DB = new DatabaseHelper(this);



        //not sure if this is right
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email.getText().toString();
                String user_pass = password.getText().toString();
                String userFirstName = firstName.getText().toString();
                String userLastName = lastName.getText().toString();
                //Date birthDate = (Date) birthdateButton.getText();

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

    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                birthdateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        birthdatePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        String monthString = "";
        switch (month) {
            case 1:
                monthString = "JAN";
                break;
            case 2:
                monthString = "FEB";
                break;
            case 3:
                monthString = "MAR";
                break;
            case 4:
                monthString = "APR";
                break;
            case 5:
                monthString = "MAY";
                break;
            case 6:
                monthString = "JUN";
                break;
            case 7:
                monthString = "JUL";
                break;
            case 8:
                monthString = "AUG";
                break;
            case 9:
                monthString = "SEP";
                break;
            case 10:
                monthString = "OCT";
                break;
            case 11:
                monthString = "NOV";
                break;
            case 12:
                monthString = "DEC";
                break;
        }
        return monthString;
    }
    public void openBirthdatePicker(View view){
        birthdatePickerDialog.show();
    }

}