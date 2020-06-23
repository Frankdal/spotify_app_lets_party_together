package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.letspartytogether.Model.Artist;
import com.example.letspartytogether.Model.PartyName;
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
import okhttp3.MultipartReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JoinActivity extends AppCompatActivity {

    private EditText etPartyCodeJoin;
    private Button bttJoin2;
    public String base_url;
    public JSONObject jsonParty;
    private PartyName partyName;
    String _codeParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        base_url = "http://lordip.ddns.net:8124";
        etPartyCodeJoin = findViewById(R.id.etPartyCodeJoin);
        bttJoin2 = findViewById(R.id.bttJoin2);

        bttJoin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _codeParty = etPartyCodeJoin.getText().toString();
                jsonParty = new JSONObject();


                HttpUrl.Builder urlBuilder
                        = HttpUrl.parse(base_url + "/party").newBuilder();
                urlBuilder.addQueryParameter("ids", _codeParty);
                urlBuilder.addQueryParameter("ids", "0");

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

                            Gson gson = new Gson();
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            try {
                                JSONObject object = jsonArray.getJSONObject(0);
                                partyName = gson.fromJson(object.toString(), PartyName.class);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            JoinActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    Toast toast = Toast.makeText(getApplicationContext(),"You joined!!",Toast.LENGTH_LONG);
                                    toast.show();
                                    Intent intentToPartyActivity = new Intent("android.intent.action.PartyActivity");
                                    intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateToParty), partyName.getName().toString());
                                    intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateCodice), _codeParty);
                                    startActivity(intentToPartyActivity);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
