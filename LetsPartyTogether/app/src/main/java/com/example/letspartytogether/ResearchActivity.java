package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ResearchActivity extends AppCompatActivity {

    private EditText etSongName;
    private ImageButton bttSearchSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        etSongName = findViewById(R.id.etSongName);
        bttSearchSong = findViewById(R.id.bttSearchSong);

        bttSearchSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
