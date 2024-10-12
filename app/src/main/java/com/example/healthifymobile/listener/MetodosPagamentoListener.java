package com.example.healthifymobile.listener;

import com.example.healthifymobile.modelo.CartaoPagamento;

import java.util.ArrayList;

public interface MetodosPagamentoListener {
    void onCardToView(ArrayList<CartaoPagamento> cartoes);
    void afterCardAdd(Boolean valid);
}
