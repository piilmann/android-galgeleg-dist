package com.example.christofferpiilmann.galgeleg;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by christofferpiilmann
 */

public class galgeDistLogik {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public String getSynligtOrd(){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://gr100-galgeleg.herokuapp.com/rest/getSynligtOrd")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject jsonResult = new JSONObject(response.body().string());
            String result = jsonResult.getString("return");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getOrdet(){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://gr100-galgeleg.herokuapp.com/rest/getOrdet")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject jsonResult = new JSONObject(response.body().string());
            String result = jsonResult.getString("return");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getAntalForkerteBogstaver() {
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://gr100-galgeleg.herokuapp.com/rest/getAntalForkerteBogstaver")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject jsonResult = new JSONObject(response.body().string());
            int result = jsonResult.getInt("return");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean erSidsteBogstavKorrekt() {
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://gr100-galgeleg.herokuapp.com/rest/erSidsteBogstavKorrekt")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject jsonResult = new JSONObject(response.body().string());
            boolean result = jsonResult.getBoolean("return");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean erSpilletVundet() {
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://gr100-galgeleg.herokuapp.com/rest/erSpilletVundet")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject jsonResult = new JSONObject(response.body().string());
            boolean result = jsonResult.getBoolean("return");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean erSpilletSlut() {

        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://gr100-galgeleg.herokuapp.com/rest/erSpilletSlut")
                    .build();

            Response response = client.newCall(request).execute();
            JSONObject jsonResult = new JSONObject(response.body().string());
            boolean result = jsonResult.getBoolean("return");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static public void gætBogstav(String input){

        String hej = "{\"bogstav\":" + input + "}";
        try {

            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, hej);
            Request request = new Request.Builder()
                    .url("http://gr100-galgeleg.herokuapp.com/rest/guessBogstav")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("Der er gættet på: " + input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hentOrdFraDR(){

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0)  {
                try {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, "");
                    Request request = new Request.Builder()
                            .url("http://gr100-galgeleg.herokuapp.com/rest/hentOrdFraDr")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    System.out.println("Ordene fra dr hentet");
                    return response.body().string();

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ordene blev ikke hentet korrekt: "+e;
                }
            }

            @Override
            protected void onPostExecute(Object resultat) {
                System.out.println("resultat: \n" + resultat);
            }
        }.execute();

    }

    public void nulstil(){
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, "");
                    Request request = new Request.Builder()
                            .url("http://gr100-galgeleg.herokuapp.com/rest/nulstil")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    System.out.println("Spillet er nulstillet");
                    return response.body().string();

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Noget gik galt: " + e;
                }
            }
        }.execute();
    }

}
