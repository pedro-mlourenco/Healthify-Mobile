package com.example.healthifymobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MetodosPagamentoFieldFragment extends Fragment {
    private TextView tvNumber, tvName, tvDate;

    public MetodosPagamentoFieldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_metodos_pagamento_field,container,false);
        tvNumber = view.findViewById(R.id.tvCardNumber);
        tvName = view.findViewById(R.id.tvNameCardHolder);
        tvDate = view.findViewById(R.id.tvDateValid);
        return view;
    }
}