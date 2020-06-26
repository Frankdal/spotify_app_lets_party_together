package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateActivity extends AppCompatActivity {

    private EditText etPartyName;
    private Button bttCreate2;
    private static final String fileName = "PartyList.txt";
    private String secretCode;
    private JSONObject jsonObject;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        etPartyName = findViewById(R.id.etPartyName);
        bttCreate2 = findViewById(R.id.bttCreate2);
        jsonObject = new JSONObject();
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        String userId = sharedPreferences.getString("userid", "No User");
        bttCreate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creazione del file json
                String _nomeParty = etPartyName.getText().toString();
                try {
                    jsonObject.put("name",_nomeParty);
                    jsonObject.put("userId", userId);
                    jsonObject.put("token",sharedPreferences.getString("token", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                data = jsonObject.toString();

                //Richiesta di PUT del party al nostro server, passandogli un oggetto json contenente il nome del party, l'user id e il suo token d'accesso

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
                            final String myResponse = response.body().string();
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                secretCode = jsonResponse.getString("partyId");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CreateActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(),"Party has been created! Your code is: "+secretCode,Toast.LENGTH_LONG);
                                    toast.show();
                                    Intent intentToPartyActivity = new Intent("android.intent.action.PartyActivity");
                                    intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateToParty), _nomeParty);
                                    intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateCodice), secretCode);
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
