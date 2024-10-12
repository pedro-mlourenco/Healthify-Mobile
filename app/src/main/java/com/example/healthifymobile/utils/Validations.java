package com.example.healthifymobile.utils;

import android.util.Patterns;

public class Validations {
    public static boolean isEmailValid(String email) {
        if (email == null)
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static boolean isPasswordValid(String password) {
        if (password == null)
            return false;

        return password.length() >= 8;
    }
}
