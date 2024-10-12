package com.example.healthifymobile.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthifymobile.R;
import com.example.healthifymobile.modelo.CartaoPagamento;
import com.example.healthifymobile.modelo.Refeicao;

import java.util.ArrayList;

public class CartoesListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CartaoPagamento> cartoes;

    public CartoesListAdapter(Context context, ArrayList<CartaoPagamento> cartoes) {
        this.context = context;
        this.cartoes = cartoes;
    }

    @Override
    public int getCount() {
        return cartoes.size();
    }

    @Override
    public Object getItem(int i) {
        return cartoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return cartoes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.fragment_metodos_pagamento_field, null);

        CartoesListAdapter.ViewHolderLista viewHolder = (CartoesListAdapter.ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new CartoesListAdapter.ViewHolderLista(view,context);
            view.setTag(viewHolder);
        }

        viewHolder.update(cartoes.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvNome,tvNumeroCartao,tvDataValidade;


        public ViewHolderLista(View view, Context context) {
            tvNome = view.findViewById(R.id.tvNameCardHolder);
            tvNumeroCartao = view.findViewById(R.id.tvCardNumber);
            tvDataValidade = view.findViewById(R.id.tvDateValid);
        }

        public void update(CartaoPagamento cartaoPagamento) {
            tvNome.setText(cartaoPagamento.getNome());
            tvNumeroCartao.setText(cartaoPagamento.getNumeroCartao());
            tvDataValidade.setText(cartaoPagamento.getMes()+"/"+cartaoPagamento.getAno());
        }
    }
}
