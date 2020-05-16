package com.example.letspartytogether;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.letspartytogether.Model.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddSongActivity extends AppCompatActivity {
    public ArrayList<Song> songs;
    private String code;
    public ListView songList;
    private String data;
    private JSONObject jsonObject;
    public String jsonSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        jsonSongs = getIntent().getStringExtra("jsonSongs");
        code = getIntent().getStringExtra(getString((R.string.STRINGA_CreateCodice)));
        songList = (ListView)findViewById(R.id.songList);
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





        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,songs);


        songList.setAdapter(arrayAdapter);


        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddSongActivity.this,"Adding"+songs.get(i).getName()+"to our party's playlist!!", Toast.LENGTH_SHORT).show();

                try {
                    jsonObject.put("partyId",code);
                    jsonObject.put("trackUri", songs.get(i).getUri());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data = jsonObject.toString();



                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType,data);
                Request request = new okhttp3.Request.Builder()
                        .url("http://lordip.ddns.net:8124/party ")
                        .method("PUT", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            AddSongActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(),"Song Added !!!",Toast.LENGTH_LONG);
                                    toast.show();

                                }
                            });
                        }
                    }
                });
            }
        });

    }
}