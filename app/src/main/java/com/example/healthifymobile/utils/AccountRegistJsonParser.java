package com.example.healthifymobile.utils;

import com.example.healthifymobile.modelo.InfoPerfil;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountRegistJsonParser {
    //registo na API
    public static Integer parserJsonRegister(String response) {
        int token = 0;
        try {
            String json = response.replace("\\\"", "'");
            JSONObject login = new JSONObject(json.substring(1,json.length()-1));
            if (login.getBoolean("success")) {
                token = login.getInt("id");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }
}
