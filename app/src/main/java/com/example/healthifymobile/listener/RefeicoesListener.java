package com.example.healthifymobile.listener;

import com.example.healthifymobile.modelo.Refeicao;

import java.util.ArrayList;

public interface RefeicoesListener {
    void onRefreshHistorico(ArrayList<Refeicao> refeicoes);
}
