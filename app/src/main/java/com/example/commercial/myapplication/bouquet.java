package com.example.commercial.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.commercial.myapplication.api.CreationAsync;
import com.example.commercial.myapplication.api.ImageDownloaderTask;
import com.example.commercial.myapplication.metier.Chaine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Balthazar on 04/05/2016.
 */
public class bouquet extends ListActivity {

    private List<Chaine> chaineList = new ArrayList<Chaine>();

    private MyAdapterChaine myAdapter;

    ImageView b1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the layout from video_main.xml
        setContentView(R.layout.bouquet_main);

        chaineList = new ArrayList<Chaine>();

        SharedPreferences sharedpreferences;

        final MainActivity la2 = new MainActivity();

        CreationAsync creationAsync = new CreationAsync(this);
        creationAsync.execute("","","http://www.tv.kabtel.com/AjoutVideo.php?");
        String resultat = null;
        try {
            resultat = creationAsync.get();

            JSONObject jsonObject = new JSONObject(resultat);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("resultat"));
            for (int i = 0; i<jsonArray.length(); i++){
                String nom = jsonArray.getJSONObject(i).getString("nom");
                String lienChaine = jsonArray.getJSONObject(i).getString("lienChaine");
                String lienLogo = jsonArray.getJSONObject(i).getString("logoChaine");

                Chaine chaine = new Chaine(nom, lienChaine);
                chaine.setLienLogo(lienLogo);
                chaine.setLogo(new ImageView(this));
                chaineList.add(chaine);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        b1 = (ImageView) findViewById(R.id.button2);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings;
                SharedPreferences.Editor editor;

                settings = getSharedPreferences(la2.MyPREFERENCES, Context.MODE_PRIVATE);
                editor = settings.edit();

                editor.clear();
                editor.commit();
                Intent intent = new Intent(bouquet.this, MainActivity.class);
                startActivity(intent);
            }
        });

        myAdapter = new MyAdapterChaine(this,  chaineList);

        getListView().setAdapter(myAdapter);

        EditText search = (EditText) findViewById(R.id.edit_recherche);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                MyAdapterChaine mac = bouquet.this.myAdapter.filter(editable.toString());
                getListView().setAdapter(mac);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //Toast.makeText(this, this.chaineList.get(position).getNom(), Toast.LENGTH_SHORT).show();
        Chaine c = chaineList.get(position);
        Intent it = new Intent(this, VideoViewActivity.class);
        it.putExtra("lien", c.getLienStream());
        it.putExtra("nom",c.getNom());

        startActivity(it);
    }

    public void Deconnexion(View view) {
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        for (int i = 0; i < chaineList.size(); i++) {
            ImageDownloaderTask imagedll = new ImageDownloaderTask(chaineList.get(i).getLogo());
//                imagedll.execute("http://nuclearpixel.com/content/icons/2010-02-09_stellar_icons_from_space_from_2005/earth_128.png");
            imagedll.execute(chaineList.get(i).getLienLogo());
            try {
                Bitmap b=imagedll.get();
                chaineList.get(i).setLogo(b);
                myAdapter = new MyAdapterChaine(this, chaineList);

                getListView().setAdapter(myAdapter);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
