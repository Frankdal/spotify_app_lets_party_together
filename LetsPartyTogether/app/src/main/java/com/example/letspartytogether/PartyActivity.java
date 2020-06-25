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

import com.example.letspartytogether.Model.Song;
import com.example.letspartytogether.R;

import java.util.ArrayList;

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
        String code = partyIntent.getStringExtra(getString((R.string.STRINGA_CreateCodice)));
        tvPartyName.setText(title);
        tvPartyCode.setText(code);

        bttCopyPartyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bottone che ci permette di copiare il codice del party nella clipboard del telefono
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Code: ", tvPartyCode.getText().toString());
                clipboard.setPrimaryClip(clip);

            }
        });


        bttAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si va nell'activity di ricerca della canzone, portandoci dietro il codice del nostro party
                Intent intentAddSongToResearch = new Intent("android.intent.action.ResearchActivity");
                intentAddSongToResearch.putExtra(getString(R.string.STRINGA_CreateCodice),code);
                startActivity(intentAddSongToResearch);
            }
        });

    }
}
