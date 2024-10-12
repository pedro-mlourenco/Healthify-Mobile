package com.example.healthifymobile.utils;

import com.example.healthifymobile.modelo.CartAPI;
import com.example.healthifymobile.modelo.Refeicao;
import com.example.healthifymobile.modelo.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewJsonParser {
    public static ArrayList<Review> parserGetReview(JSONArray response) {
        ArrayList<Review> revArray = new ArrayList<>();
        try {
            for(int i=0; i<response.length(); i++) {
                JSONObject ref = (JSONObject) response.get(i);
                int id = ref.getInt("id");
                Float rating = Float.parseFloat(ref.getString("rating"));
                String corpo = ref.getString("review");
                int fkIdRefeicao = ref.getInt("mealsid");

                Review aux = new Review(id,corpo,rating,fkIdRefeicao);
                revArray.add(aux);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return revArray;
    }
}
