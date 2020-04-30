package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    private EditText etPartyCodeJoin, etJoinName;
    private Button bttJoin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etPartyCodeJoin = findViewById(R.id.etPartyCodeJoin);
        etJoinName = findViewById(R.id.etJoinName);
        bttJoin2 = findViewById(R.id.bttJoin2);

        bttJoin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
