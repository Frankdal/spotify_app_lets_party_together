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

import com.example.letspartytogether.Model.Artist;
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
    public String base_url;
//    public ArrayList<Artist> artistsName;
    public String jsonSongs;
    public ArrayList<Song> songs;

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
                Toast toast = Toast.makeText(getApplicationContext(), "We are searching your song ...", Toast.LENGTH_LONG);
                String _nameSong = etSongName.getText().toString();
                songs = new ArrayList<Song>();


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
                            final String myResponse = response.body().string();
                            jsonSongs = myResponse;
                            Gson gson = new Gson();
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(jsonSongs);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int n = 0; n < jsonArray.length(); n++) {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(n);
                                    Song song = gson.fromJson(object.toString(), Song.class);
                                    songs.add(song);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                                ResearchActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intentToAddSongActivity = new Intent("android.intent.action.AddSongActivity");
                                        intentToAddSongActivity.putExtra("jsonSongs",jsonSongs );
                                        intentToAddSongActivity.putExtra(getString(R.string.STRINGA_CreateCodice), code);
                                        startActivity(intentToAddSongActivity);
                                    }
                                });
                            }
                        }
                });
            }
        });
    }
}


