package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button bttStart, bttCreate, bttJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttStart = findViewById(R.id.bttStart);
        bttCreate = findViewById(R.id.bttCreate);
        bttJoin = findViewById(R.id.bttJoin);

        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bttCreate.isShown() && bttJoin.isShown()) {
                    bttCreate.setVisibility(View.INVISIBLE);
                    bttJoin.setVisibility(View.INVISIBLE);
                }

                else {
                    bttCreate.setVisibility(View.VISIBLE);
                    bttJoin.setVisibility(View.VISIBLE);
                }

            }
        });

        bttCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "BELLAA", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
