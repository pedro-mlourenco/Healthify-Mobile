package com.example.healthifymobile.utils;

import com.example.healthifymobile.modelo.InfoPerfil;

import org.json.JSONException;
import org.json.JSONObject;

public class StaffOperationsParser {

    public static boolean WorkedTime(String response) {
        Boolean bool = false;
        try {
            String json = response.replace("\\\"", "'");
            JSONObject son = new JSONObject(json.substring(1, json.length() - 1));
            if (son.getBoolean("success")) {

                bool = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bool;
    }
}
