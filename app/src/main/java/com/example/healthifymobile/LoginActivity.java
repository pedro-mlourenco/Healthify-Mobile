package com.example.healthifymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.healthifymobile.listener.LoginListener;
import com.example.healthifymobile.modelo.InfoPerfil;
import com.example.healthifymobile.modelo.SingletonGestorBd;

public class LoginActivity extends AppCompatActivity implements LoginListener {
    private EditText etUser, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser = findViewById(R.id.eTUser);
        etPassword = findViewById(R.id.eTPassword);
        SingletonGestorBd.getInstance(getApplicationContext()).setLoginListener(this);
    }

    public void onClickLogin(View view) {
        String email = etUser.getText().toString();
        String password = etPassword.getText().toString();

        SingletonGestorBd.getInstance(getApplicationContext()).loginAPI(email,password,getApplicationContext());
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidateLogin(InfoPerfil infoPerfil, Context context) {
        if(infoPerfil.getToken()!=null) {

            SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
            SharedPreferences.Editor addShare = sharedPreferences.edit();

            addShare.putInt("profileId",infoPerfil.getIdPerfil());
            addShare.putInt("nif",infoPerfil.getNif());
            addShare.putString("name",infoPerfil.getNome());
            addShare.putString("email",infoPerfil.getEmail());
            addShare.putInt("cellphone",infoPerfil.getTelemovel());
            addShare.putString("street",infoPerfil.getRua());
            addShare.putInt("door",infoPerfil.getPorta());
            addShare.putString("floor",infoPerfil.getAndar());
            addShare.putString("city",infoPerfil.getCidade());
            addShare.putInt("userId",infoPerfil.getIdUser());
            addShare.putString("token",infoPerfil.getToken());
            addShare.putString("role",infoPerfil.getRole());

            addShare.apply();
            if (infoPerfil.getRole().equals("client")){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, StaffMainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}