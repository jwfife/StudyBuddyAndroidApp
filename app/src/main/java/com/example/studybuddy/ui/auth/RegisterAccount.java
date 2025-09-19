package com.example.studybuddy.ui.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.studybuddy.db.DatabaseHelper;
import com.example.studybuddy.R;
import com.example.studybuddy.db.auth.DatabaseValidRepository;
import com.example.studybuddy.db.auth.ValidRepository;
import com.example.studybuddy.db.user.DatabaseUserRepository;
import com.example.studybuddy.db.user.UserRepository;
import com.example.studybuddy.util.PasswordValidator;

public class RegisterAccount extends AppCompatActivity {

    /*
    TO DO:
    - create/reference database containing most US schools and universities
    (https://public.opendatasoft.com/explore/dataset/us-colleges-and-universities/table/?flg=en-us
    might work)
    - use that database as an option for a drop down menu when registering for an account
     */

    private EditText emailField, passwordField, firstNameField, lastNameField;
    private Button register;
    private ImageView viewPasswordEye;
    private CardView checkbox1, checkbox2, checkbox3, checkbox4;

    private DatabaseHelper db;
    private ValidRepository validRepository;
    private UserRepository userRepository;
    private PasswordValidator passwordValidator;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        bindViews();

        db = DatabaseHelper.getInstance(this);
        validRepository = new DatabaseValidRepository(db);
        userRepository = new DatabaseUserRepository(db);
        passwordValidator = new PasswordValidator();

        viewPasswordEye.setOnClickListener(v -> togglePasswordVisibility());
        //setting register to enabled true might go here?
        register.setOnClickListener(view -> {
            handleRegisterClick();
        });

        setupBackToSignIn();
        setupPasswordWatcher();
    }

    @SuppressLint("ResourceType")
    private void bindViews() {
        emailField = findViewById(R.id.editEmailAddress);
        passwordField = findViewById(R.id.editPassword);
        firstNameField = findViewById(R.id.editFirstName);
        lastNameField = findViewById(R.id.editLastName);
        register = findViewById(R.id.register);
        viewPasswordEye = findViewById(R.id.view_password_eye);
        checkbox1 = findViewById(R.id.card1);
        checkbox2 = findViewById(R.id.card2);
        checkbox3 = findViewById(R.id.card3);
        checkbox4 = findViewById(R.id.card4);
        register.setBackgroundColor(Color.parseColor(getString(R.color.myGrey)));
        register.setEnabled(false);
    }

    private void handleRegisterClick() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();

        if (isInputEmpty(email, password, firstName, lastName)){
            return;
        }
        if (!isUniqueUser(email)) {
            return;
        }
        if (!passwordValidator.isValid(password)) {
            Toast.makeText(this, "Password does not meet all requirements",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        boolean inserted = userRepository.insertUser(email, password, firstName, lastName);
        if (inserted) {
            Toast.makeText(this, "Registration complete!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SignIn.class));
        }
        else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInputEmpty(String email, String password, String firstName, String lastName) {
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(RegisterAccount.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private Boolean isUniqueUser(String email) {
        boolean isValid = validRepository.validate(email);
        if (!isValid) {
            Toast.makeText(this,
                    "User already exists! Please sign in or use another email.",
                    Toast.LENGTH_SHORT
            ).show();
        }
        return isValid;
    }

    private void togglePasswordVisibility() {
        if (passwordField.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
            //reveals password
            passwordField.setTransformationMethod(new SingleLineTransformationMethod());
        }
        else {
            //hides password
            passwordField.setTransformationMethod(new PasswordTransformationMethod());
        }
        passwordField.setSelection(passwordField.getText().length());
    }

    private void setupBackToSignIn() {
        findViewById(R.id.back_to_sign_in).setOnClickListener(v ->
                startActivity(new Intent(this, SignIn.class)));
    }

    private void setupPasswordWatcher() {
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            //will constantly update the register button as the password is typed
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updatePasswordUI(s.toString());
            }
        });
    }

    @SuppressLint("ResourceType")
    private void updatePasswordUI(String password){
        setCheckboxColor(checkbox1, passwordValidator.isMinimumLength((password)));
        setCheckboxColor(checkbox2, passwordValidator.hasNumber((password)));
        setCheckboxColor(checkbox3, passwordValidator.hasUppercase((password)));
        setCheckboxColor(checkbox4, passwordValidator.hasSpecialSymbol((password)));

        boolean allValid = passwordValidator.isValid(password);
        register.setEnabled(true);
        register.setBackgroundColor(allValid ? Color.parseColor(getString(R.color.myBlue)) :
                Color.parseColor(getString(R.color.myGrey)));
    }

    private void setCheckboxColor(CardView checkbox, boolean fulfilled) {
        @SuppressLint("ResourceType") int color = fulfilled ? Color.parseColor(getString(R.color.myBlue)) :
                Color.parseColor(getString(R.color.myGrey));
        checkbox.setCardBackgroundColor(color);
    }
}