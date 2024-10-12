package com.example.healthifymobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.healthifymobile.adaptadores.CartoesListAdapter;
import com.example.healthifymobile.listener.MetodosPagamentoListener;
import com.example.healthifymobile.modelo.CartaoPagamento;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class PagamentosFragment extends Fragment implements MetodosPagamentoListener {

    private ListView list;
    public PagamentosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico_list, container, false);
        setHasOptionsMenu(false);

        list=view.findViewById(R.id.listHistorico);
        SingletonGestorBd.getInstance(getContext()).setMetodoPagamentoListener(this);
        SingletonGestorBd.getInstance(getContext()).getCartoesPagamentoBD(getContext());
        return view;
    }


    @Override
    public void onCardToView(ArrayList<CartaoPagamento> cartoes) {
        if (cartoes!=null) {
            list.setAdapter(new CartoesListAdapter(getContext(),cartoes));
        }
    }

    @Override
    public void afterCardAdd(Boolean valid) {

    }
}