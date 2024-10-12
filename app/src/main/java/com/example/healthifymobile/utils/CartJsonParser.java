package com.example.healthifymobile.utils;

import com.example.healthifymobile.modelo.CartAPI;
import com.example.healthifymobile.modelo.MealsAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartJsonParser
{
    public static ArrayList<CartAPI> parserGetCartAll(JSONArray response) {
        ArrayList<CartAPI> cartArray =new ArrayList<>();
        try {
            for(int i=0; i<response.length(); i++) {
                JSONObject son = (JSONObject) response.get(i);
                int id= son.getInt("id");
                int userprofilesid= son.getInt("userprofilesid");
                int mealsid= son.getInt("mealsid");
                String sellingprice= son.getString("sellingprice");
                int itemquantity= son.getInt("itemquantity");
                String state= son.getString("state");

                CartAPI cart = new CartAPI(id,userprofilesid,mealsid,itemquantity,sellingprice,state);
                cartArray.add(cart);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return cartArray;
    }

    public static ArrayList<CartAPI> parserCart(String response) {
        ArrayList<CartAPI> cartArray =new ArrayList<>();
        try {
            String json = response.replace("\\\"", "'");
            JSONObject son = new JSONObject(json.substring(1,json.length()-1));

            int id= son.getInt("id");
            int userprofilesid= son.getInt("userprofilesid");
            int mealsid= son.getInt("mealsid");
            String sellingprice= son.getString("sellingprice");
            int itemquantity= son.getInt("itemquantity");
            String state= son.getString("state");

            CartAPI cart = new CartAPI(id,userprofilesid,mealsid,itemquantity,sellingprice,state);
            cartArray.add(cart);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return cartArray;
    }
}
