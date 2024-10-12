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

public class MealsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MealsAPI> meals;
    private String tableid;

    public MealsListAdapter(Context context, ArrayList<MealsAPI> meals, String tableid) {
        this.context = context;
        this.meals = meals;
        this.tableid = tableid;
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

        ViewHolderLista viewHolder = (ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(view, context);
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

            btAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    int userprofilesid = sharedPreferences.getInt("profileId",0);
                    int mealid = Integer.parseInt(tvId.getText().toString());
                    //retira o sinal de € do preço para ser aceite pela API
                    String sellingprice = tvPrice.getText().toString();
                    String splitPrecoString[] = sellingprice.split(" ");

                    int itemquantity = 1;
                    SingletonGestorBd.getInstance(context).addCart(userprofilesid,mealid,splitPrecoString[0],itemquantity,context,tableid);
                    Toast.makeText(context, "Produto Adicionado ao Carrinho!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void update(MealsAPI meals) {
            tvName.setText(meals.getName());
            tvDescription.setText(meals.getDescription());
            tvPrice.setText(meals.getPrice() + " €");
            tvId.setText(meals.getId() + "");
        }
    }
}
