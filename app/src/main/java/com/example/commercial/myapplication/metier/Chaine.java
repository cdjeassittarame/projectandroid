package com.example.commercial.myapplication.metier;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by kabtel on 10/05/16.
 */
//fichier construicteur qui permet de creer les chaine pour notre liste avec setteur et getteur
public class Chaine {

    private ImageView logo;
    private Bitmap logoBitmap;
    private String nom;
    private String lienStream;
    private String lienLogo;

    public Chaine() {
    }

    public Chaine(ImageView logo, String nom, String lienStream) {
        this.logo = logo;
        this.lienStream = lienStream;
        this.nom = nom;
    }

    public Chaine(ImageView imageView, String nom) {
        this.logo = imageView;
        this.nom = nom;
    }

    public Chaine(String nom, String lienChaine) {
        this.nom = nom;
        this.lienStream = lienChaine;
    }

    public ImageView getLogo() {

        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public String getLienStream() {
        return lienStream;
    }

    public void setLienStream(String lienStream) {
        this.lienStream = lienStream;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLogo(Bitmap b) {
        logo.setImageBitmap(b);
        logoBitmap=b;

    }

    public String getLienLogo() {
        return lienLogo;
    }

    public void setLienLogo(String lienLogo) {
        this.lienLogo = lienLogo;
    }

    public void setLogoBitmap(Bitmap logoBitmap) {
        this.logoBitmap = logoBitmap;
    }

    public Bitmap getLogoBitmap() {
        return logoBitmap;
    }

}
