package com.example.commercial.myapplication;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commercial.myapplication.metier.Chaine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kabtel on 17/05/16.
 */
public class MyAdapterChaine extends BaseAdapter {

    private List<Chaine> listChaine;
    private Context context;

    public MyAdapterChaine(Context context, List<Chaine> listChaine){

        this.listChaine = listChaine;
        this.context = context;
    }

    public MyAdapterChaine filter (String chaine)
    {
        ArrayList<Chaine> listC = new ArrayList<Chaine>();
        //TODO ajouter les chaine


        for (Chaine c : this.listChaine) { //Parcour la liste de chaine de base qui est stoqué dans la BDD
            if(c.getNom().toLowerCase().contains(chaine.toLowerCase())) //compare les lettre entrer par chaque nom de chaine
            {
                listC.add(c); //ajoute dans la nouvelle list si elle existe
            }
        }

        //Afficher en mode Chaine et logo
        MyAdapterChaine mac = new MyAdapterChaine(this.context, listC);

        return  mac;
    }

    @Override
    public int getCount() {
        return listChaine.size();
    }

    @Override
    public Object getItem(int position) {
        return listChaine.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MyViewChaine holder;

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView =layoutInflater.inflate(R.layout.custom, null);
            holder = new MyViewChaine();
            holder.nom =  (TextView) convertView.findViewById(R.id.nomChaine);
            holder.logo = (ImageView) convertView.findViewById(R.id.logoChaine);

            convertView.setTag(holder);

        } else {
            holder = (MyViewChaine) convertView.getTag();
        }

        Chaine chaine = (Chaine)getItem(position);
        holder.nom.setText(chaine.getNom());
        holder.logo.setImageBitmap(chaine.getLogoBitmap());

        return convertView;
        
    }

    private static class MyViewChaine{
        TextView nom;
        ImageView logo;
        String link;
    }

}
