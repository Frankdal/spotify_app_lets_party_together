package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letspartytogether.Model.Song;
import com.example.letspartytogether.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResearchActivity extends AppCompatActivity {

    private EditText etSongName;
    private ImageButton bttSearchSong;
    private String code;
    private String data;
    public ArrayList<Song> songs;
    public String base_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        base_url = "http://lordip.ddns.net:8124";
        etSongName = findViewById(R.id.etSongName);
        bttSearchSong = findViewById(R.id.bttSearchSong);
        Intent researchIntent = getIntent();
        code = researchIntent.getStringExtra(getString((R.string.STRINGA_CreateCodice)));
        bttSearchSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _nameSong = etSongName.getText().toString();
                songs = new ArrayList<>();


                HttpUrl.Builder urlBuilder
                        = HttpUrl.parse(base_url + "/search").newBuilder();
                urlBuilder.addQueryParameter("key", _nameSong);
                urlBuilder.addQueryParameter("partyId", code);

                String url = urlBuilder.build().toString();

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                Request request = new okhttp3.Request.Builder()
                        .url(url)
                        .method("GET", null)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Toast toast = Toast.makeText(getApplicationContext(),"OH YESSSSSSSSSSS ",Toast.LENGTH_LONG);
                            toast.show();
                            Gson gson = new Gson();
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(response.body().string());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONArray jsonArray = jsonResponse.optJSONArray("items");
                            for (int n = 0; n < jsonArray.length(); n++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(n);
                                    object = object.optJSONObject("track");
                                    Song song = gson.fromJson(object.toString(), Song.class);
                                    songs.add(song);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ResearchActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        Toast toast = Toast.makeText(getApplicationContext(), myResponse, Toast.LENGTH_LONG);
//                                        toast.show();
//                                        Intent intentToPartyActivity = new Intent("android.intent.action.PartyActivity");
//                                        intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateToParty), _nomeParty);
//                                        intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateCodice), secretCode);
//                                        startActivity(intentToPartyActivity);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }
}


