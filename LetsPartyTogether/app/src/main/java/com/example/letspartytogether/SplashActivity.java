package com.example.letspartytogether;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.example.letspartytogether.Connectors.UserService;
import com.example.letspartytogether.Model.User;

/**
 * Start Activity, authenticate Spotify
 */
public class SplashActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;

    private RequestQueue queue;

    //Inizializzazione dei parametri di connessione all'API di Spotify
    private static final String CLIENT_ID = "5a7933f0c4da42ae8a33a0041257cbfd";
    private static final String REDIRECT_URI = "com.example.letspartytogether://callback";
    private static final int REQUEST_CODE = 1337;
    //Specificando le autorizzazioni che chiediamo a Spotify
    private static final String SCOPES = "user-read-recently-played,user-library-modify,user-library-read,playlist-modify-public,playlist-modify-private,user-read-email,user-read-private,playlist-read-private,playlist-read-collaborative";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getLocalClassName(), "Created Activity");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Inizio l'autenticazione di Spotify
        authenticateSpotify();

        msharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(this);
    }


    private void waitForUserInfo() {
        UserService userService = new UserService(queue, msharedPreferences);
        userService.get(() -> {
            User user = userService.getUser();
            editor = getSharedPreferences("SPOTIFY", 0).edit();
            editor.putString("userid", user.id);
            Log.d("STARTING", "GOT USER INFORMATION");
            // We use commit instead of apply because we need the information stored immediately
            editor.commit();
            startCreateActivity();
        });
    }

    private void startCreateActivity() {
        Intent newintent = new Intent("android.intent.action.CreateActivity");
        startActivity(newintent);
    }


    private void authenticateSpotify() {   //Costruiamo una richiesta di autenticazione
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES}); //Settiamo le autorizzazioni
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request); //Si apre la finestra di login
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Controlliamo se il risultato proviene dall'attività corretta
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Se la risposta da esito positivo, contiene il token di autenticazione
                case TOKEN:
                    editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("STARTING", "GOT AUTH TOKEN");
                    editor.apply();
                    waitForUserInfo();
                    break;


                case ERROR:

                    break;

                default:

            }
        }
    }

}