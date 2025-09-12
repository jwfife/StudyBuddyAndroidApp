package com.example.studybuddy.db;

import java.sql.SQLException;

public interface UserRepository {
    String getFirstName(String email) throws SQLException;
    String getLastName(String email) throws SQLException;
}
