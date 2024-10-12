package com.example.healthifymobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.healthifymobile.modelo.SingletonGestorBd;

public class FragmentPedidosField extends Fragment{
    private TextView tvName, tvDescription, tvPrice,tvId;
    private ImageButton addToCart;

    public FragmentPedidosField(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedidos_field,container,false);
        tvId = view.findViewById(R.id.tvIdDish);
        tvName = view.findViewById(R.id.tvNameDish);
        tvDescription = view.findViewById(R.id.tvDescriptionDish);
        tvPrice = view.findViewById(R.id.tvPriceDish);

        addToCart= view.findViewById(R.id.btAddCartitem);

        return view;
    }
}