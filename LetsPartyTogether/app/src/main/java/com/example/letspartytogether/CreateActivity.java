package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.letspartytogether.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        etPartyName = findViewById(R.id.etPartyName);
        etPartyHost = findViewById(R.id.etPartyHost);
        bttCreate2 = findViewById(R.id.bttCreate2);

        bttCreate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _nomeParty = etPartyName.getText().toString();

                Intent intentToPartyActivity = new Intent("android.intent.action.PartyActivity");
                intentToPartyActivity.putExtra(getString(R.string.STRINGA_CreateToParty), _nomeParty);
                startActivity(intentToPartyActivity);

            }
        });

    }
}
