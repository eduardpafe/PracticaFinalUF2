package com.example.eduar.practicafinaluf2;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    MediaPlayer audioPlayer;

    Button btnVideo, btnInformacio, btnValoracio, btnLlibreVisites, btnPlayPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnPlayPause = (Button) findViewById(R.id.btnMusica);

        audioPlayer = new MediaPlayer();
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        audioPlayer.setOnPreparedListener(MenuActivity.this);

        //Àudio de fons un cop obrim l'activity.
        Uri uriMusica = Uri.parse("android.resource://com.example.eduar.practicafinaluf2/" + R.raw.musica_menu_los_de_marras);

        try {
            audioPlayer.setDataSource(this, uriMusica);
            audioPlayer.prepareAsync();
            audioPlayer.start();

            btnPlayPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (audioPlayer.isPlaying()) {
                        audioPlayer.pause();
                        btnPlayPause.setBackgroundResource(R.drawable.sound_of);
                    } else {
                        audioPlayer.start();
                        btnPlayPause.setBackgroundResource(R.drawable.sound_on);
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        btnVideo = (Button) findViewById(R.id.btnVideo);
        btnValoracio = (Button) findViewById(R.id.btnValoracio);
        btnInformacio = (Button) findViewById(R.id.btnInformacio);
        btnLlibreVisites = (Button) findViewById(R.id.btnLlibreVisites);

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoActivity = new Intent(getApplicationContext(), VideoActivty.class);
                startActivity(videoActivity);
            }
        });

        btnLlibreVisites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llibreVisitesActivity = new Intent(getApplicationContext(), LlibreVisitesActivity.class);
                startActivity(llibreVisitesActivity);
            }
        });

        btnInformacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent informacioActivity = new Intent(getApplicationContext(), InformacioActivity.class);
                startActivity(informacioActivity);
            }
        });

        btnValoracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent valoracioActivity = new Intent(getApplicationContext(), ValoracioActivity.class);
                startActivity(valoracioActivity);
            }
        });

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        audioPlayer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        audioPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        audioPlayer.start();
    }

}
