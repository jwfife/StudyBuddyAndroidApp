package com.example.studybuddy.db;

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
}
