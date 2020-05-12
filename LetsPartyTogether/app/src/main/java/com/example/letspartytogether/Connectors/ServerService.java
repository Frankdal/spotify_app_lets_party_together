package com.example.letspartytogether.Connectors;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.letspartytogether.Model.PartyCode;
import com.example.letspartytogether.VolleyCallBack;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ServerService {
//    private ArrayList<Song> songs = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private RequestQueue queue;
    private PartyCode partyCode;

    public ServerService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);
    }

    public PartyCode getPartyCode() {
        return partyCode;
    }

//    public ArrayList<Song> getRecentlyPlayedTracks(final VolleyCallBack callBack) {
//        String endpoint = "https://api.spotify.com/v1/me/player/recently-played";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, endpoint, null, response -> {
//                    Gson gson = new Gson();
//                    JSONArray jsonArray = response.optJSONArray("items");
//                    for (int n = 0; n < jsonArray.length(); n++) {
//                        try {
//                            JSONObject object = jsonArray.getJSONObject(n);
//                            object = object.optJSONObject("track");
//                            Song song = gson.fromJson(object.toString(), Song.class);
//                            songs.add(song);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    callBack.onSuccess();
//                }, error -> {
//                    // TODO: Handle error
//
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                String token = sharedPreferences.getString("token", "");
//                String auth = "Bearer " + token;
//                headers.put("Authorization", auth);
//                return headers;
//            }
//        };
//        queue.add(jsonObjectRequest);
//        return songs;
//    }


//    public void addSongToLibrary(Song song) {
//        JSONObject payload = preparePutPayload(song);
//        JsonObjectRequest jsonObjectRequest = prepareSongLibraryRequest(payload);
//        queue.add(jsonObjectRequest);
//    }

    public void putCode(JSONObject payload , final VolleyCallBack callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, "https://lordip.ddns.net:8124/party ", payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                partyCode = gson.fromJson(response.toString(), PartyCode.class);
                callBack.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Failure Callback
            }
        });
        queue.add(jsonObjectRequest);
    }

//    private JSONObject preparePutPayload(Song song) {
//        JSONArray idarray = new JSONArray();
//        idarray.put(song.getId());
//        JSONObject ids = new JSONObject();
//        try {
//            ids.put("ids", idarray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return ids;
//    }
}
