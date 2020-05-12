package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.letspartytogether.Connectors.ServerService;
import com.example.letspartytogether.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CreateActivity extends AppCompatActivity {

    private EditText etPartyName, etPartyHost;
    private Button bttCreate2;
    private static final String fileName = "PartyList.txt";
    private ServerService serverService;
    private String secretCode;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        etPartyName = findViewById(R.id.etPartyName);
        etPartyHost = findViewById(R.id.etPartyHost);
        bttCreate2 = findViewById(R.id.bttCreate2);
        serverService = new ServerService(getApplicationContext());
        jsonObject = new JSONObject();
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);

        bttCreate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _nomeParty = etPartyName.getText().toString();
                String _nomeHost = etPartyHost.getText().toString();
                try {
                    jsonObject.put("name",_nomeHost);
                    jsonObject.put("userId",_nomeHost);
                    jsonObject.put("token",sharedPreferences.getString("token", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent intentToPartyActivity = new Intent("android.intent.action.PartyActivity");
                intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateToParty), _nomeParty);
                startActivity(intentToPartyActivity);

            }
        });

    }

    private void getTracks() {
        serverService.putCode(jsonObject, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                secretCode = serverService.getPartyCode().partyId;
            }
        });

    }
}
