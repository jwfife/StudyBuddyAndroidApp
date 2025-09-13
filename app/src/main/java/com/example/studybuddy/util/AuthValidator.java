package com.example.studybuddy.util;

public class AuthValidator {
    public static boolean isEmpty(String email, String password){
        return email == null || email.isEmpty() || password == null || password.isEmpty();
    }
}
