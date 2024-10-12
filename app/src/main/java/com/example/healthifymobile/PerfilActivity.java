package com.example.healthifymobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.healthifymobile.modelo.SingletonGestorBd;

public class PerfilActivity extends AppCompatActivity {
    private int userProfileId;
    private String userName;
    private TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userProfileId = sharedPreferences.getInt("profileId",0);
        userName = sharedPreferences.getString("name","");
        tvName = findViewById(R.id.tvNomePerfil);
        tvName.setText(userName);

    }


    public void onClickGoBack(View view){
        finish();
    }

    public void onClickFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        PagamentosFragment pagamentosFragment = new PagamentosFragment();
        HistoricoFragment historicoFragment = new HistoricoFragment();
        CriticasFragment criticasFragment = new CriticasFragment();

        frameLayout.removeAllViews();

        switch(view.getId()) {
            case R.id.btnPagamentos:
                fragmentTransaction.add(R.id.frameLayout, pagamentosFragment);
                break;
            case R.id.btnHistorico:
                fragmentTransaction.add(R.id.frameLayout, historicoFragment);
                break;
            case R.id.btnCriticas:
                fragmentTransaction.add(R.id.frameLayout, criticasFragment);
                break;
        }

        fragmentTransaction.commit();
    }

    public void editProfile(View view) {
        Intent intent = new Intent(this, EditarPerfilActivity.class);
        startActivity(intent);
    }
}