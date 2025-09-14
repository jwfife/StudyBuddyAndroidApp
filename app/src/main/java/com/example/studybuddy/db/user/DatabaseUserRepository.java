package com.example.studybuddy.db.user;

import com.example.studybuddy.db.DatabaseHelper;
import com.example.studybuddy.db.user.UserRepository;

import java.sql.SQLException;

public class DatabaseUserRepository implements UserRepository {
    private final DatabaseHelper db;

    public DatabaseUserRepository(DatabaseHelper db) {
        this.db = db;
    }

    @Override
    public String getFirstName(String email) throws SQLException {
        return db.getFirstName(email);
    }

    @Override
    public String getLastName(String email) throws SQLException {
        return db.getLastName(email);
    }

    @Override
    public boolean insertUser(String email, String password, String firstName, String lastName){
        return db.insertUserData(email, password, firstName, lastName);
    }
}
