package com.example.commercial.myapplication.api;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kabtel on 10/05/16.
 */
public class CreationAsync extends AsyncTask<String, Void, String> {

    private Dialog loadingDialog;
    Context context;

    public CreationAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(this.context, "Please wait", "..");
    }
//permet de se connecter en fonction des information recu donc du nombre de parametre envoyer si 1 affichage de le base de donner en mode liste
    //si 3 connexion si 5 creation de la base de donnee
    @Override
    protected String doInBackground(String... params) {
        int nbreParam = 5;

        int status;

        String uname = params[0];
        String pass = params[1];
        StringBuilder url = new StringBuilder(params[2]);
        String email = "";
        String date="";


        if(params.length >= nbreParam){
            email = params[3];
            date=params[4];
        }

        InputStream is = null;

        url.append("username="+uname+"&password="+pass+"&email_to_login="+email+"&date="+date);

        String result = null;

        try{
            //creation objet URL a partir de de la stringURL
            URL javaUrl = new URL(url.toString());
            //ouvre la connection a partir de lobjet javaUrl
            HttpURLConnection httpURLConnection= (HttpURLConnection) javaUrl.openConnection();
            //JE STIPULE QUE JE VAIS ENVOYER ET RECEVOIR DES INFORMATIONS
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            //jE ME CONNECTE
            httpURLConnection.connect();

            StringBuilder sb = new StringBuilder();

            String line = null;
            //Je recupere la reponse de la connection si elle est ok ou pas
            status =httpURLConnection.getResponseCode();

            switch (status) {
                //bufferReader permet de lire ce qui a ete recu
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
            }

            result = sb.toString();
        }  catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        loadingDialog.dismiss();
    }
}