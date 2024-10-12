package com.example.healthifymobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.healthifymobile.adaptadores.HistoricoListAdapter;
import com.example.healthifymobile.adaptadores.ReviewListAdapter;
import com.example.healthifymobile.listener.ReviewListener;
import com.example.healthifymobile.modelo.Review;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class CriticasFragment extends Fragment implements ReviewListener {

    private ListView list;

    public CriticasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historico_list, container, false);
        list=view.findViewById(R.id.listHistorico);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int userprofilesid = sharedPreferences.getInt("profileId",0);
        SingletonGestorBd.getInstance(getContext()).setReviewListener(this);
        SingletonGestorBd.getInstance(getContext()).getReviews(userprofilesid,getContext());
        return view;
    }

    @Override
    public void onRefreshReview(ArrayList<Review> review) {
        if (review!=null) {
            list.setAdapter(new ReviewListAdapter(getContext(), review));
        }
    }
}