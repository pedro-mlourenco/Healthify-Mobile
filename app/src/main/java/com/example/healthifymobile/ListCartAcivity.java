package com.example.healthifymobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthifymobile.adaptadores.CartListAdapter;
import com.example.healthifymobile.listener.CartListener;
import com.example.healthifymobile.listener.CartPayListener;
import com.example.healthifymobile.modelo.CartAPI;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;
import java.util.Objects;

public class ListCartAcivity extends AppCompatActivity implements CartListener, CartPayListener {
    private ListView list;
    private String method;
    private Context context;
    private int userprofilesid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        context = getApplicationContext();

        list = findViewById(R.id.listCart);
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userprofilesid = sharedPreferences.getInt("profileId",0);
        method = getIntent().getStringExtra("method");

        SingletonGestorBd.getInstance(getApplicationContext()).setCartListener(this);
        SingletonGestorBd.getInstance(getApplicationContext()).getCartApiAll(userprofilesid,context);
    }

    @Override
    public void onRefreshCart(ArrayList<CartAPI> cart) {
        if (cart!=null) {
            list.setAdapter(new CartListAdapter(getApplicationContext(), cart));

        }
    }

    public void onClickGoBack(View view){
        finish();
    }

    public void toPay(View view) {

        if(Objects.equals(method, "cash")){
            SingletonGestorBd.getInstance(context).payCash(userprofilesid,context);
            Intent intent = new Intent(getApplicationContext(), StaffMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, ListCartoesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCartPay(boolean cartPay, Context context) {
        if (cartPay) {
            Toast.makeText(context, "Pedido efetuado!\nPara pagamento o cliente deve dirigir-se à caixa", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Problemas com a plataforma de pagamento", Toast.LENGTH_SHORT).show();
        }
    }
}