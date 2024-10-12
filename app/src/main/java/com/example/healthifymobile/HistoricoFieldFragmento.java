package com.example.healthifymobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoricoFieldFragmento extends Fragment {
    private TextView tvData,tvPreco,tvId,tvIdPerfil,tvEstado,tvMetodoPagamento;
    public HistoricoFieldFragmento() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_historico_field, container, false);
        tvId = view.findViewById(R.id.tvIdHist);
        tvIdPerfil = view.findViewById(R.id.tvIdPerfilHist);
        tvData = view.findViewById(R.id.tvDateHist);
        tvMetodoPagamento = view.findViewById(R.id.tvMetodopagamentoHist);
        tvEstado = view.findViewById(R.id.tvStateHist);
        tvPreco = view.findViewById(R.id.tvPriceHist);

        return view;
    }
}