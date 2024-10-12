package com.example.healthifymobile.listener;

public interface NewAccountListener {

    void onValidateRegister(int userid, String username, String email, String password);

    void onValidateNewProfile(Boolean message);
}
