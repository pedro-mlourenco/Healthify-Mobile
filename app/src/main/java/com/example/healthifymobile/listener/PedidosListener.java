package com.example.healthifymobile.listener;

import com.example.healthifymobile.modelo.MealsAPI;

import java.util.ArrayList;

public interface PedidosListener {
    void onRefreshListapedidos(ArrayList<MealsAPI> mealsAPI, String tableid);
}
