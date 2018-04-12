package com.example.eduar.practicafinaluf2.Firebase;

import java.util.ArrayList;

/**
 * Created by eduar on 23/03/2018.
 */

public class Registre {
    String nom, cognom;

    public Registre(){}

    public Registre(String nom, String cognom) {
        this.nom = nom;
        this.cognom = cognom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

}
