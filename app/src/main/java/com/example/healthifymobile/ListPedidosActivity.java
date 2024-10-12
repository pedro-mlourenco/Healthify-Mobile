package com.example.healthifymobile;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthifymobile.adaptadores.MealsListAdapter;
import com.example.healthifymobile.listener.PedidosListener;
import com.example.healthifymobile.modelo.MealsAPI;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class ListPedidosActivity extends AppCompatActivity implements PedidosListener {
    private ListView list;
    private String tableid, method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_list);
        list = findViewById(R.id.listHistorico);

        tableid = getIntent().getStringExtra("tableid");
        method = getIntent().getStringExtra("method");

        SingletonGestorBd.getInstance(getApplicationContext()).setPedidosListener(this);
        SingletonGestorBd.getInstance(getApplicationContext()).getEmentaAPI(getApplicationContext(), tableid);
    }

    @Override
    public void onRefreshListapedidos(ArrayList<MealsAPI> mealsAPI, String tableid) {
        if (mealsAPI!=null) {
            list.setAdapter(new MealsListAdapter(getApplicationContext(), mealsAPI, tableid));
        }
    }

    public void onClickGoBack(View view){
        finish();
    }

    public void onClickShowCart(View view){
        Intent intent = new Intent(this, ListCartAcivity.class);
        intent.putExtra("method", method);
        startActivity(intent);
        if (method == "cash"){
            finish();
        };
    }

}