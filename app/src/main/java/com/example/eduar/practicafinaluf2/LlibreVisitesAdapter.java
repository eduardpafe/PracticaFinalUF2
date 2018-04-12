package com.example.eduar.practicafinaluf2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduar.practicafinaluf2.Firebase.Registre;

import java.util.ArrayList;

/**
 * Created by eduar on 10/04/2018.
 */

public class LlibreVisitesAdapter extends ArrayAdapter<Registre> {


    // Classe que guarda les referències als elements de la vista
    class Vista {
        public TextView nom;
        public TextView cognom;
    }

    private Registre[] dades;

    LlibreVisitesAdapter(Activity context, Registre[] dades) {
        super(context, R.layout.llibre_visites_list_row, dades);
        this.dades = dades;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        Vista vista;

        if (element == null) {
            LayoutInflater inflater = ((Activity) getContext())
                    .getLayoutInflater();
            element = inflater.inflate(R.layout.llibre_visites_list_row, null);

            vista = new Vista();
            vista.nom = (TextView) element.findViewById(R.id.txtNom);
            vista.cognom = (TextView) element.findViewById(R.id.txtCognom);

            element.setTag(vista);
        } else {
            vista = (Vista) element.getTag();
        }

        vista.nom.setText(dades[position].getNom());
        vista.cognom.setText(dades[position].getCognom());

        return element;
    }

}
