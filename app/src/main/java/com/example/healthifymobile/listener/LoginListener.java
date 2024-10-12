package com.example.healthifymobile.listener;

import android.content.Context;

import com.example.healthifymobile.modelo.InfoPerfil;

public interface LoginListener {
    void onValidateLogin(InfoPerfil infoPerfil, final Context context);
}
