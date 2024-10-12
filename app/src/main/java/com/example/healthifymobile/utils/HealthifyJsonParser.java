package com.example.healthifymobile.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.healthifymobile.modelo.InfoPerfil;
import com.example.healthifymobile.modelo.Refeicao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HealthifyJsonParser {

    public static ArrayList<Refeicao> parserJsonRefeicao(JSONArray response) {
        ArrayList<Refeicao> arrayRefeicao = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject ref = (JSONObject) response.get(i);
                int id = ref.getInt("id");
                String data = ref.getString("salesday");
                Float preco = Float.parseFloat(ref.getString("paidamount"));
                String metodoPagamento = ref.getString("paymentmethod");
                String estado = ref.getString("paymentstate");
                int fkIdPerfil = ref.getInt("userprofilesid");

                Refeicao aux = new Refeicao(id, data, preco, metodoPagamento, estado, fkIdPerfil);
                arrayRefeicao.add(aux);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayRefeicao;
    }

    public static InfoPerfil parserPerfil(String response) {
        InfoPerfil perfil = null;
        try {
            String json = response.replace("\\\"", "'");
            JSONObject son = new JSONObject(json.substring(0, json.length()));
            int perfilId = son.getInt("id");
            int nif = son.getInt("nif");
            String nome = son.getString("name");
            int telemovel = son.getInt("cellphone");
            String rua = son.getString("street");
            int porta = son.getInt("door");
            String andar = "R/C";
            if (son.getString("floor").length() > 0) {
                andar = son.getString("floor");
            }
            String cidade = son.getString("city");
            int userid = son.getInt("userid");
            perfil = new InfoPerfil(perfilId, nif, nome, "email", telemovel, rua, porta, andar, cidade, userid, "token", "role");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return perfil;
    }

    //verifica se existe acesso à internet
    public static boolean isConnectionInternet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }

    //login na API
    public static InfoPerfil parserJsonLogin(String response) {
        InfoPerfil perfil = null;
        try {
            String json = response.replace("\\\"", "'");
            JSONObject son = new JSONObject(json.substring(1, json.length() - 1));
            if (son.getBoolean("success")) {

                int perfilId = son.getInt("profileId");
                int nif = son.getInt("nif");
                String nome = son.getString("name");
                String email = son.getString("email");
                int telemovel = son.getInt("cellphone");
                String rua = son.getString("street");
                int porta = son.getInt("door");
                String andar = "R/C";
                if (son.getString("floor").length() > 0) {
                    andar = son.getString("floor");
                }
                String cidade = son.getString("city");
                int userid = son.getInt("userId");
                String token = son.getString("token");
                String role = son.getString("role");

                perfil = new InfoPerfil(perfilId, nif, nome, email, telemovel, rua, porta, andar, cidade, userid, token, role);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return perfil;
    }
}
