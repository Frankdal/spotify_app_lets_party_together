package com.example.letspartytogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PartyActivity extends AppCompatActivity {

    private TextView tvFinalPartyCode;
    private TextView tvPartyName, tvPartyCode;
    private Button bttCopyPartyCode, bttAddSong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        tvFinalPartyCode = findViewById(R.id.tvFinalPartyCode);
        tvPartyName = findViewById(R.id.tvPartyName);
        tvPartyCode = findViewById(R.id.tvPartyCode);
        bttCopyPartyCode = findViewById(R.id.bttCopyPartyCode);
        bttAddSong = findViewById(R.id.bttAddSong);

        Intent partyIntent = getIntent();
        String title = partyIntent.getStringExtra(getString(R.string.STRINGA_CreateToParty));
        tvPartyName.setText(title);

        bttCopyPartyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Code: ", tvPartyCode.getText().toString());
                clipboard.setPrimaryClip(clip);

            }
        });


        bttAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddSongToResearch = new Intent("android.intent.action.ResearchActivity");
                startActivity(intentAddSongToResearch);
            }
        });

    }
}
