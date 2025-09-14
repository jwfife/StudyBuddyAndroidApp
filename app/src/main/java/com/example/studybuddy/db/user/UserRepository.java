package com.example.studybuddy.db.user;

import java.sql.SQLException;

public interface UserRepository {
    String getFirstName(String email) throws SQLException;
    String getLastName(String email) throws SQLException;
    boolean insertUser(String email, String password, String firstName, String lastName);
}
