package com.example.studybuddy.db.auth;

public interface AuthRepository {
    boolean authenticate(String email, String password);
}
