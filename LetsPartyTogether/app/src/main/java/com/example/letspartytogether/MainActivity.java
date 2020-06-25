package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.letspartytogether.R;

public class MainActivity extends AppCompatActivity {

    private Button bttStart, bttCreate1, bttJoin1;
    private Intent intentToCreateActivity, IntentToJoinActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getLocalClassName(), "Created Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttStart = findViewById(R.id.bttStart);
        bttCreate1 = findViewById(R.id.bttCreate1);
        bttJoin1 = findViewById(R.id.bttJoin1);

        bttStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bttCreate1.isShown() && bttJoin1.isShown()) { //Se clicco + mi appaiono i bottoni
                    bttCreate1.setVisibility(View.INVISIBLE);     //Se sono già visibili, scompaiono
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
                Intent intentToSplashActivity = new Intent("android.intent.action.SplashActivity");
                startActivity(intentToSplashActivity);

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

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        Log.i("Main", sharedPreferences.getString("userid", "No User"));
    }
}
