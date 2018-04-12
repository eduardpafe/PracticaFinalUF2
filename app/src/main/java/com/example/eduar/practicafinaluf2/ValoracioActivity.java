package com.example.eduar.practicafinaluf2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValoracioActivity extends AppCompatActivity {
    StorageReference myStorage;


    String miPath = null; //Carpeta on es guarda l'àudio

    private MediaRecorder gravador;
    private MediaPlayer reproductor;

    private boolean gravacioIniciada = true;
    private boolean reproduccioIniciada = true;

    private File fitxer;

    private Button btnEscoltarAudio, btnGravarAudio, btnEnviarAudio;

    public ValoracioActivity() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileNameStr = "valoracioAudio" + timeStamp + ".3gp";

        fitxer = new File(Environment.getExternalStorageDirectory(), "GravadorAudio");

        if (!fitxer.exists()) {
            fitxer.mkdirs();
        }

        fitxer = new File(fitxer, fileNameStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoracio);

        btnGravarAudio = (Button) findViewById(R.id.btnGravarAudio);
        btnEscoltarAudio = (Button) findViewById(R.id.btnEscoltarAudio);
        btnEnviarAudio = (Button) findViewById(R.id.btnEnviarAudio);

        myStorage = FirebaseStorage.getInstance().getReference();

        btnGravarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(gravacioIniciada);

                if (gravacioIniciada) {
                    btnGravarAudio.setText("Aturar gravació");
                } else {
                    btnGravarAudio.setText("Iniciar gravació");
                }
                gravacioIniciada = !gravacioIniciada;
            }
        });

        btnEscoltarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(reproduccioIniciada);

                if (reproduccioIniciada) {
                    btnEscoltarAudio.setText("Aturar reproducció");
                } else {
                    btnEscoltarAudio.setText("Escoltar gravació");
                }
                reproduccioIniciada = !reproduccioIniciada;
            }
        });

        btnEnviarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isOnlineNet() == true) {
                        guardarDades();

                    } else {
                        Toast.makeText(ValoracioActivity.this, "No hi ha connexió a internet", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void onRecord(boolean iniciar) {
        if (iniciar) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        crearGravador();
    }

    private void stopRecording() {
        gravador.stop();
        gravador.release();
        gravador = null;
    }

    private void crearGravador() {
        gravador = new MediaRecorder();
        gravador.setAudioSource(MediaRecorder.AudioSource.MIC);
        gravador.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        gravador.setOutputFile(fitxer.getAbsolutePath());
        gravador.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            gravador.prepare();
            gravador.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onPlay(boolean iniciar) {
        if (iniciar) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        reproductor = new MediaPlayer();

        try {
            reproductor.setDataSource(fitxer.getAbsolutePath());
            reproductor.prepare();
            reproductor.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        reproductor.stop();
        reproductor.release();
        reproductor = null;
    }

    private void guardarDades() throws FileNotFoundException, IOException {
        //Pujem la foto
        StorageReference filePath = myStorage.child("valoracions").child(fitxer.getAbsolutePath());


        Uri fitxerAudio = Uri.fromFile(fitxer.getAbsoluteFile());

        filePath.putFile(fitxerAudio).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
        Toast.makeText(this, "Valoració enviada.", Toast.LENGTH_LONG).show();
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
