package com.example.studybuddy.db.auth;

import com.example.studybuddy.db.DatabaseHelper;

public class DatabaseValidRepository implements ValidRepository {
    private final DatabaseHelper db;

    public DatabaseValidRepository (DatabaseHelper db){
        this.db = db;
    }

    public boolean validate (String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return !db.checkEmail(email);
    }
}
