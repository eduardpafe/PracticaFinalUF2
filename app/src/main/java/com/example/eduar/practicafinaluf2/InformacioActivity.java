package com.example.eduar.practicafinaluf2;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class InformacioActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacio);

        textView = (TextView) findViewById(R.id.txtExplicacioVinya);

        textView.setText(Html.fromHtml("<h1 align='center'>VIÑA ROCK</h1><p> El Festival d’Art Natiu Viña Rock és un festival musical espanyol organitzat anualment" +
                "el cap de setmana previ a l’1 de maig. Des dels seus inicis en 1996 s’ha celebrat en la ciutat manxega de Villarobledo." +
                "        </p>" +
                "        <p>" +
                "            En l’edició del 2007 l’empresa que organitzava el festival va decidir traslladar-lo a Benicàssim, a la Planta Alta," +
                "            però l’Audiència Provincial de València va concedir a l’ajuntament de Villarobledo la titularitat de la marca ‘Viña Rock’." +
                "            En l’edició de 2008 es va celebrar com Viña Rock a Villarrobledo, organitzat pel seu ajuntament, i com El Viña a Paoporta" +
                "            (Horta Oest), organitzat per una empresa privada." +
                "        </p>" + "<h2 align=\"center\">Història</h2>\n" +
                "          <p>\n" +
                "           Viña Rock té els seus orígens en el Festival Nacional de Música Apocalíptica celebrat l’any 1996 al\n" +
                "              Campo Municipal de Deportes Nuestra Señora de la Claridad, on hi varen participar destacats artístes\n" +
                "              del moment com Extremoduro, Los Planetas o Platero y tú, entre d’altres. Aquell mateix any, es reuneixen\n" +
                "              a l’ajuntament de Villarobledo un grup de joves per tal d’organitzar en la localitat un festival de\n" +
                "              música d’art-natiu.\n" +
                "          </p>\n" +
                "        <p>\n" +
                "            El nom de festival, Viña Rock, respon a la gran quantitat de vinyes característiques de la localitat.\n" +
                "        </p>"
        ));


//        @string/explicacio_vinyarock
    }

}
