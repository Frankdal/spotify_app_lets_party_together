package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button bttStart, bttCreate1, bttJoin1;
    private Intent intentToCreateActivity, IntentToJoinActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttStart = findViewById(R.id.bttStart);
        bttCreate1 = findViewById(R.id.bttCreate1);
        bttJoin1 = findViewById(R.id.bttJoin1);

        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bttCreate1.isShown() && bttJoin1.isShown()) {
                    bttCreate1.setVisibility(View.INVISIBLE);
                    bttJoin1.setVisibility(View.INVISIBLE);
                }

                else {
                    bttCreate1.setVisibility(View.VISIBLE);
                    bttJoin1.setVisibility(View.VISIBLE);
                }

            }
        });

        bttCreate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "BELLAA", Toast.LENGTH_SHORT).show();
                Intent intentToCreateActivity = new Intent("android.intent.action.CreateActivity");
                startActivity(intentToCreateActivity);

            }
        });

        bttJoin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToJoinActivity = new Intent("android.intent.action.JoinActivity");
                startActivity(intentToJoinActivity);
            }
        });

    }
}
