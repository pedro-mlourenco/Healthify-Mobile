package com.example.healthifymobile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthifymobile.adaptadores.EmentaListAdapter;
import com.example.healthifymobile.adaptadores.MealsListAdapter;
import com.example.healthifymobile.listener.EmentaListener;
import com.example.healthifymobile.modelo.MealsAPI;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class EmentaActivity extends AppCompatActivity implements EmentaListener {
    private ListView list;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ementa);
        list = findViewById(R.id.listHistorico);
        context = getApplicationContext();

        SingletonGestorBd.getInstance(context).setEmentaListener(this);
        SingletonGestorBd.getInstance(context).showEmentaAPI(context);

    }

    public void onClickGoBack(View view){
        finish();
    }

    @Override
    public void onLoadEmenta(ArrayList<MealsAPI> mealsAPI) {
        if (mealsAPI!=null) {
            list.setAdapter(new EmentaListAdapter(context, mealsAPI));
        }
    }
}