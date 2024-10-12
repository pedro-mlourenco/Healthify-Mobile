package com.example.healthifymobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.healthifymobile.adaptadores.HistoricoListAdapter;
import com.example.healthifymobile.listener.RefeicoesListener;
import com.example.healthifymobile.modelo.Refeicao;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class HistoricoFragment extends Fragment implements RefeicoesListener {

    private ListView list;

    public HistoricoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico_list, container, false);
        setHasOptionsMenu(false);

        list=view.findViewById(R.id.listHistorico);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int userprofilesid = sharedPreferences.getInt("profileId",0);
        SingletonGestorBd.getInstance(getContext()).setRefeicaoListener(this);
        SingletonGestorBd.getInstance(getContext()).getHistorico(userprofilesid,getContext());
        return view;
    }

    @Override
    public void onRefreshHistorico(ArrayList<Refeicao> refeicoes) {
        if (refeicoes!=null) {
            list.setAdapter(new HistoricoListAdapter(getContext(), refeicoes));
        }
    }
}