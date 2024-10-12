package com.example.healthifymobile.utils;

import com.example.healthifymobile.modelo.MealsAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MealsJsonParser {

    public static ArrayList<MealsAPI> parserMeal(JSONArray response) {
        ArrayList<MealsAPI> mealsAPI=new ArrayList<>();
        try {
            for(int i=0; i<response.length(); i++) {
                JSONObject son = (JSONObject) response.get(i);
                int id = son.getInt("id");
                String name = son.getString("name");
                String price = son.getString("price");
                String description = son.getString("description");
                int categoryid = son.getInt("categoryid");

                MealsAPI meal = new MealsAPI(id, name, price, description, categoryid);
                mealsAPI.add(meal);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return mealsAPI;
    }
}
