package com.example.healthifymobile.listener;

import android.content.Context;

import com.example.healthifymobile.modelo.CartAPI;

import java.util.ArrayList;

public interface CartListener {
    void onRefreshCart(ArrayList<CartAPI> cart);
}
