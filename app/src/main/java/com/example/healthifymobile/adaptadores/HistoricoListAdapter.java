package com.example.healthifymobile.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healthifymobile.R;
import com.example.healthifymobile.modelo.CartAPI;
import com.example.healthifymobile.modelo.Refeicao;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class HistoricoListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Refeicao> refeicoes;

    public HistoricoListAdapter(Context context, ArrayList<Refeicao> refeicoes) {
        this.context = context;
        this.refeicoes = refeicoes;
    }

    @Override
    public int getCount() {
        return refeicoes.size();
    }

    @Override
    public Object getItem(int i) {
        return refeicoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return refeicoes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.fragment_historico_field, null);

        HistoricoListAdapter.ViewHolderLista viewHolder = (HistoricoListAdapter.ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new HistoricoListAdapter.ViewHolderLista(view,context);
            view.setTag(viewHolder);
        }

        viewHolder.update(refeicoes.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvData,tvPreco,tvId,tvIdPerfil,tvEstado,tvMetodoPagamento;


        public ViewHolderLista(View view, Context context) {
            tvId = view.findViewById(R.id.tvIdHist);
            tvIdPerfil = view.findViewById(R.id.tvIdPerfilHist);
            tvData = view.findViewById(R.id.tvDateHist);
            tvMetodoPagamento= view.findViewById(R.id.tvMetodopagamentoHist);
            tvEstado = view.findViewById(R.id.tvStateHist);
            tvPreco = view.findViewById(R.id.tvPriceHist);

        }

        public void update(Refeicao refeicao) {
            tvId.setText(refeicao.getId()+"");
            tvIdPerfil.setText(refeicao.getFkIdPerfil()+"");
            tvData.setText(refeicao.getData()+"");
            tvEstado.setText(refeicao.getEstadoRefeicao()+"");
            tvPreco.setText(refeicao.getPreco()+" €");
            tvMetodoPagamento.setText(refeicao.getMetodoRefeicaco()+"");

        }
    }

}
