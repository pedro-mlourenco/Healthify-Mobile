package com.example.healthifymobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthifymobile.adaptadores.CartoesListAdapter;
import com.example.healthifymobile.listener.CartPayListener;
import com.example.healthifymobile.listener.MetodosPagamentoListener;
import com.example.healthifymobile.modelo.CartaoPagamento;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class ListCartoesActivity extends AppCompatActivity implements CartPayListener, MetodosPagamentoListener {

    private String numero;
    private int userprofilesid;
    private Context context;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cartoes);

        list = findViewById(R.id.listCartoesDiv);

        SingletonGestorBd.getInstance(context).setCartPayListener(this);
        SingletonGestorBd.getInstance(context).setMetodoPagamentoListener(this);

        context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userprofilesid = sharedPreferences.getInt("profileId",0);

        SingletonGestorBd.getInstance(context).getCartoesPagamentoBD(context);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                CartaoPagamento cartaoPagamento = SingletonGestorBd.getInstance(context).getCartaoBD((int) id);
                numero = cartaoPagamento.getNumeroCartao();

                SingletonGestorBd.getInstance(context).payCart(userprofilesid,numero,context);
            }
        });
    }

    @Override
    public void onCartPay(boolean cartPay, Context context) {
        if (cartPay) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HistoricoFragment historicoFragment = new HistoricoFragment();
            fragmentTransaction.add(R.id.frameLayout, historicoFragment);

            Intent intent = new Intent(this, ReviewActivity.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(context, "Problemas com a plataforma de pagamento", Toast.LENGTH_SHORT).show();
    }
    public void onClickGoBack(View view){
        finish();
    }

    public void onClickAddCartao(View view) {
        Intent intent = new Intent(this, AddPagamentoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCardToView(ArrayList<CartaoPagamento> cartoes) {
        if (cartoes!=null) {
            list.setAdapter(new CartoesListAdapter(context,cartoes));
        }
    }

    @Override
    public void afterCardAdd(Boolean valid) {

    }
}