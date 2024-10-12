package com.example.healthifymobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentCartField extends Fragment{

    private TextView tvName, tvQuantidade, tvPrice,tvId;
    private ListView list;

    public FragmentCartField() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_field,container,false);
        tvId = view.findViewById(R.id.tvIdHist);
        tvName = view.findViewById(R.id.tvDateHist);
        tvQuantidade = view.findViewById(R.id.quantidade);
        tvPrice = view.findViewById(R.id.tvPriceHist);
        list = view.findViewById(R.id.listCart);
        return view;
    }
}