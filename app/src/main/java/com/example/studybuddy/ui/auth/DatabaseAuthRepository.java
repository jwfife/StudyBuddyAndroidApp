package com.example.studybuddy.ui.auth;

import com.example.studybuddy.db.DatabaseHelper;

public class DatabaseAuthRepository implements AuthRepository{
    private final DatabaseHelper db;

    public DatabaseAuthRepository(DatabaseHelper db) {
        this.db = db;
    }
    @Override
    public boolean authenticate(String email, String password) {
        return db.checkEmailPassword(email, password);
    }
}
