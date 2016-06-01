package com.example.commercial.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    android.app.ActionBar actionBar;

    // Insert your Video URL
    String VideoURL = null;

    Toast nom_chaine;

    String nom = null;
//cette classe permet d'utiliser le lecteur afin de streamer notre flux
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//tout les variable constructeur de la classe mere
        // Get the layout from video_main.xml
        setContentView(R.layout.videoview_main);
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(VideoViewActivity.this);
        // Set progressbar title
        pDialog.setTitle("Votre chaine est en cours de chargement");
        // Set progressbar message
        pDialog.setMessage("Chargement");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();
        Intent it = getIntent();
        //getActionBar().hide();
//on recupere les information envoyer par le intent de lautre classe java afin de les reutuliser
        nom = it.getStringExtra("nom");

        VideoURL = it.getStringExtra("lien");

        nom_chaine = Toast.makeText(getApplicationContext(), nom, Toast.LENGTH_SHORT);

        nom_chaine.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);

     try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoViewActivity.this,false);
            mediacontroller.setAnchorView(videoview);

            // Get the URL from String VideoURL

            videoview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                        nom_chaine.show();
                        return false;
                }
            });

            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);


        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }

        });

    }

}