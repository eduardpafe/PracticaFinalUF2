package com.example.eduar.practicafinaluf2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.eduar.practicafinaluf2.Firebase.Registre;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 3;

    File tempImageFile;

    DatabaseReference databaseRegistre;
    StorageReference myStorage;

    Uri miPath = null; //Carpeta on es guardara la imatge
    ImageView mImageView;

    Button btnAfegir, btnFoto;
    EditText txtNom, txtCognom;

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        txtNom = (EditText) findViewById(R.id.txtNom);
        txtCognom = (EditText) findViewById(R.id.txtCognom);
        btnAfegir = (Button) findViewById(R.id.btnAfegir);
        btnFoto = (Button) findViewById(R.id.btnFoto);

        mImageView = (ImageView) findViewById(R.id.imgFoto);

        //Agafant la referència de la base de dades node
        databaseRegistre = FirebaseDatabase.getInstance().getReference("Usuaris");

        myStorage = FirebaseStorage.getInstance().getReference();


        btnAfegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent menuActivity = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(menuActivity);*/
                guardarDades();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

    }

    private void mostrarDialogOpciones() {
        final CharSequence[] opcions = {"Fer foto", "Escull de la galeria", "Cancel·lar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Escull una opció");
        builder.setItems(opcions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opcions[i].equals("Fer foto")) {
                    try {
                        ferFoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (opcions[i].equals("Escull de la galeria")) {
                    seleccionaFoto();
                } else {
                    dialogInterface.cancel();
                }
            }
        });
        builder.show();
    }

    private void seleccionaFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Tria"), COD_SELECCIONA);
    }

    private void ferFoto() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            tempImageFile = crearFitxer();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImageFile));
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "No hi ha cap aplicació per capturar fotos.", Toast.LENGTH_LONG).show();
        }

    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private File crearFitxer() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "foto_" + timeStamp + ".jpg";
        File path = new File(Environment.getExternalStorageDirectory(), this.getPackageName());

        if (!path.exists()) {
            path.mkdirs();
        }

        return new File(path, imageFileName);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COD_SELECCIONA && resultCode == RESULT_OK) {
            miPath = data.getData();
            mImageView.setImageURI(miPath);
        } else if (requestCode == COD_FOTO && resultCode == RESULT_OK) {
            miPath = data.getData();
            mImageView.setImageURI(miPath);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            miPath = Uri.fromFile(tempImageFile);
            mImageView.setImageURI(miPath);
        }
    }


    private void guardarDades() {
        //Agafant els valors per guardar les dades
        String nom = txtNom.getText().toString().trim();
        String cognom = txtCognom.getText().toString().trim();

        if (!TextUtils.isEmpty(nom) && !TextUtils.isEmpty(cognom) && miPath != null) {
            String id = databaseRegistre.push().getKey();

            //Creem el nou objectee, nom i cognom d'usuari
            Registre nouRegistre = new Registre(nom, cognom);
            databaseRegistre.child(id).setValue(nouRegistre);

            //Pujem la foto
            StorageReference filePath = myStorage.child("fotos").child(miPath.getLastPathSegment());

            filePath.putFile(miPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "No s'ha pujat correctament", Toast.LENGTH_LONG).show();

                }
            });

            Toast.makeText(this, "Registre guardat", Toast.LENGTH_LONG).show();


            //Obrim l'activitat nova, perquè ja haurem registrat les dades
            Intent segonaActivity = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(segonaActivity);

        } else {
            Toast.makeText(this, "No s'ha guardat el registre, posa totes les dadaes.", Toast.LENGTH_LONG).show();
        }
    }

}
