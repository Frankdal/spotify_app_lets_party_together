package com.example.letspartytogether;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.letspartytogether.Model.PartyCode;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JsonRequestPost {

    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private String data;


    public JsonRequestPost(Context context,String partyName) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        requestQueue = Volley.newRequestQueue(context);
        String token = sharedPreferences.getString("token", "");
        PartyCode partyCode = new PartyCode(partyName,token);
        Gson gson = new Gson();
        data = gson.toJson(partyCode);
        Submit(data);
    }

    public void Submit(String data)
    {
        final String savedata= data;
        String URL="https://lordip.ddns.net/party";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);


                } catch (JSONException e) {

                }
                //Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //Log.v("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }

}


