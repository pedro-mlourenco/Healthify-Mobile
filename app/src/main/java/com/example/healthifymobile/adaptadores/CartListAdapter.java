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
import com.example.healthifymobile.modelo.SingletonGestorBd;

import java.util.ArrayList;

public class CartListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CartAPI> cart;

    public CartListAdapter(Context context, ArrayList<CartAPI> cart) {
        this.context = context;
        this.cart = cart;
    }

    @Override
    public int getCount() {
        return cart.size();
    }

    @Override
    public Object getItem(int i) {
        return cart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return cart.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.fragment_cart_field, null);


        CartListAdapter.ViewHolderLista viewHolder = (CartListAdapter.ViewHolderLista) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(view,context);
            view.setTag(viewHolder);
        }

        viewHolder.update(cart.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView tvName, tvQuantidade, tvPrice, tvId;
        private ImageButton btSubCart,btAddCart;


        public ViewHolderLista(View view, Context context) {
            tvName = view.findViewById(R.id.tvDateHist);
            tvQuantidade = view.findViewById(R.id.tvMetodopagamentoHist);
            tvPrice = view.findViewById(R.id.tvPriceHist);
            tvId = view.findViewById(R.id.tvIdHist);
            btAddCart = view.findViewById(R.id.btAddCartitem);
            btSubCart = view.findViewById(R.id.btSubFromCart);

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            int userprofilesid = sharedPreferences.getInt("profileId",0);

            btAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemquantity = Integer.parseInt(tvQuantidade.getText().toString());
                    int cartItemId = Integer.parseInt(tvId.getText().toString());
                    itemquantity++;
                    SingletonGestorBd.getInstance(context).cartPutRequest(cartItemId, itemquantity,userprofilesid, context);
                }
            });
            btSubCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemquantity = Integer.parseInt(tvQuantidade.getText().toString());
                    int cartItemId = Integer.parseInt(tvId.getText().toString());
                    itemquantity--;
                    if (itemquantity <1){
                        SingletonGestorBd.getInstance(context).deleteCartItem(cartItemId,userprofilesid, context);
                    }else{
                        SingletonGestorBd.getInstance(context).cartPutRequest(cartItemId, itemquantity,userprofilesid, context);
                    }
                }
            });
        }

        public void update(CartAPI cart) {
            tvName.setText(cart.getState()+"");
            tvQuantidade.setText(cart.getItemquantity()+"");
            tvPrice.setText(cart.getSellingprice() + " €");
            tvId.setText(cart.getId() + "");
        }
    }
}
