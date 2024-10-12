package com.example.healthifymobile.utils;

import com.example.healthifymobile.modelo.TableReserveAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TableReserveParser {
    public static TableReserveAPI parserGetReserve(String response) {
        TableReserveAPI reserve = null;
        try {
            String json = response.replace("\\\"", "'");
            JSONObject son = new JSONObject(json.substring(1, json.length() - 1));
            String state= son.getString("state");
            String message= son.getString("message");
            reserve = new TableReserveAPI(state,message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reserve;
    }
}
