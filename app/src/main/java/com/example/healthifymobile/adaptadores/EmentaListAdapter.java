package com.example.healthifymobile.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthifymobile.R;
import com.example.healthifymobile.modelo.MealsAPI;
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class EmentaListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MealsAPI> meals;

    public EmentaListAdapter(Context context, ArrayList<MealsAPI> meals) {
        this.context = context;
        this.meals = meals;
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @Override
    public Object getItem(int i) {
        return meals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return meals.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.fragment_pedidos_field, null);

        EmentaListAdapter.ViewHolderLista viewHolder = (EmentaListAdapter.ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new EmentaListAdapter.ViewHolderLista(view, context);
            view.setTag(viewHolder);
        }

        viewHolder.update(meals.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvName, tvDescription, tvPrice, tvId;
        private ImageButton btAddCart;

        public ViewHolderLista(View view, Context context) {
            tvName = view.findViewById(R.id.tvNameDish);
            tvDescription = view.findViewById(R.id.tvDescriptionDish);
            tvPrice = view.findViewById(R.id.tvPriceDish);
            tvId = view.findViewById(R.id.tvIdDish);
            btAddCart = view.findViewById(R.id.btAddCartitem);
            btAddCart.setVisibility(View.GONE);
        }

        public void update(MealsAPI meals) {
            tvName.setText(meals.getName());
            tvDescription.setText(meals.getDescription());
            tvPrice.setText(meals.getPrice() + " €");
            tvId.setText(meals.getId() + "");
        }
    }
}
