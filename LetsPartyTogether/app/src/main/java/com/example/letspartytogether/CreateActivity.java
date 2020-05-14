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

    private EditText etPartyName, etPartyHost;
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
        etPartyHost = findViewById(R.id.etPartyHost);
        bttCreate2 = findViewById(R.id.bttCreate2);
        jsonObject = new JSONObject();
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        String userId = sharedPreferences.getString("userid", "No User");
        bttCreate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _nomeParty = etPartyName.getText().toString();
                String _nomeHost = etPartyHost.getText().toString();
                try {
                    jsonObject.put("name",_nomeHost);
                    jsonObject.put("userId", userId);
                    jsonObject.put("token",sharedPreferences.getString("token", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                data = jsonObject.toString();



                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                //RequestBody body = RequestBody.create(mediaType, "{\"name\":\"ddddd\",\"userId\":\"ddddd\",\"token\":\"BQD12Z6tBUHdNVT57paPu6kzp7FDDKeaE3rvENY1b5hIISYsxjHrHr-OUnqAlOra1_JTiwLlnHiBJE4nFgqp6fnLE95UXYaqTbR07tkx_chKK4-RfcXAG2_1DKXpUuBrFul3Tk4U_xRJ25oIhAE4LqcvkwIQa3fUkYc5bcwp4nG-TX9AjdZIEK9RV39tgdNOaHpIWhtLZB0xcr_xlw1G1GHnQ6VxxZyCB8MQNoaRjxabYQg3joAUJygdxSUmEXukyW07IQ\"}");
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
                                secretCode=jsonResponse.getString("partyId");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CreateActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(),"Party has been created! Your code is :"+secretCode,Toast.LENGTH_LONG);
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
