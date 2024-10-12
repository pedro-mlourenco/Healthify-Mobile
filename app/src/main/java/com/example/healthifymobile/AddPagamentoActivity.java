package com.example.healthifymobile;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.braintreepayments.cardform.view.CardForm;
import com.example.healthifymobile.listener.CartPayListener;
import com.example.healthifymobile.listener.MetodosPagamentoListener;
import com.example.healthifymobile.modelo.CartaoPagamento;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class AddPagamentoActivity extends AppCompatActivity implements CartPayListener, MetodosPagamentoListener {

    Button addCard;
    private EditText etNome,etNumero, etCvv, etmes, etAno;
    private String nome,numero, tableid;
    private int cvv,mes,ano,userprofilesid;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.lightlisse));
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        SingletonGestorBd.getInstance(context).setMetodoPagamentoListener(this);

        setContentView(R.layout.card_form);
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userprofilesid = sharedPreferences.getInt("profileId",0);
        addCard = findViewById(R.id.btnAddCard);
        etNome = findViewById(R.id.etNomeCartaoAdd);
        etNumero = findViewById(R.id.etNmeroCartaoAdd);
        etCvv = findViewById(R.id.etCvvCartaoAdd);
        etmes = findViewById(R.id.etMesCartaoAdd);
        etAno = findViewById(R.id.etAnoCartaoAdd);
    }

    public void onClickGoBack(View view) {
        finish();
    }

    public void addPaymentCard(View view) {
        numero = String.valueOf(etNumero.getText());
        nome = String.valueOf(etNome.getText());
        mes = Integer.parseInt(String.valueOf(etmes.getText()));
        ano = Integer.parseInt(String.valueOf(etAno.getText()));
        cvv = Integer.parseInt(String.valueOf(etCvv.getText()));

        CartaoPagamento cartaoPagamento= new CartaoPagamento(1,numero,mes,ano,cvv,nome);
        SingletonGestorBd.getInstance(context).inserirCartaoPagamentoBG(cartaoPagamento,context);
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

    @Override
    public void onCardToView(ArrayList<CartaoPagamento> cartoes) {

    }

    @Override
    public void afterCardAdd(Boolean valid) {
        Toast.makeText(getApplicationContext(), "Cartão inserido com sucesso!", Toast.LENGTH_LONG).show();

        SingletonGestorBd.getInstance(context).payCart(userprofilesid,numero,context);
    }
}