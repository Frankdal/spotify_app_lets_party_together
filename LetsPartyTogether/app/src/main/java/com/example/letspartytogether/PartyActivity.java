package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PartyActivity extends AppCompatActivity {

    private TextView tvPartyName, tvPartyCode;
    private Button bttCopyPartyCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        tvPartyName = findViewById(R.id.tvPartyName);
        tvPartyCode = findViewById(R.id.tvPartyCode);
        bttCopyPartyCode = findViewById(R.id.bttCopyPartyCode);

        bttCopyPartyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
