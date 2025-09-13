package com.example.studybuddy.ui.auth;

public interface AuthRepository {
    boolean authenticate(String email, String password);
}
