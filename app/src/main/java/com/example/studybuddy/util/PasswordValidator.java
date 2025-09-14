package com.example.studybuddy.util;

public class PasswordValidator {

    public Boolean isValid(String password){
        return (isMinimumLength(password)
                && hasNumber(password)
                && hasUppercase(password)
                && hasSpecialSymbol(password));
    }

    public Boolean isMinimumLength(String password){
        return password.length() >= 8;
    }

    public Boolean hasNumber(String password){
        return password.matches("(.*[0-9].*)");
    }

    public Boolean hasUppercase(String password) {
        return password.matches("(.*[A-Z].*)");
    }

    public Boolean hasSpecialSymbol(String password) {
        return password.matches("^(?=.*[_.()$&@]).*$");
    }
}
