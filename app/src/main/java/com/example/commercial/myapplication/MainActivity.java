package com.example.commercial.myapplication;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commercial.myapplication.api.CreationAsync;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button button;

    Button button2;

    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;


    private static String PHPurl = "http://www.tv.kabtel.com/new1.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_main);

        // Locate the button in activity_main.xml
        button = (Button) findViewById(R.id.MyButton);

        button2 = (Button) findViewById(R.id.MyButton2);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


    }

    //methode permettant de verifier si le smartphone  a active le reseaux ou pas
    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }


    public void invokeLogin(View view) {

        if (isNetworkOnline()) {
            usernametxt = username.getText().toString();
            passwordtxt = password.getText().toString();

            login(usernametxt, passwordtxt);
        } else {
            Toast.makeText(getApplicationContext(), "Aucune connexion internet", Toast.LENGTH_LONG).show();
        }

    }

    public void invokeCreate(View view) {

        if (isNetworkOnline()) {
            Intent intent2 = new Intent(MainActivity.this, CreateAccount.class);
            startActivity(intent2);
        } else {
            Toast.makeText(getApplicationContext(), "Aucune connexion internet", Toast.LENGTH_LONG).show();
        }

        // finish();
    }


    private void login(final String username, String password) {
        CreationAsync la = new CreationAsync(MainActivity.this);
        la.execute(username, password, PHPurl);

        String s = null;
        try {
            s = la.get().trim();
            if (s.equalsIgnoreCase("success")) {
                Intent intent = new Intent(MainActivity.this, bouquet.class);
                //intent.putExtra(USER_NAME, username);

                startActivity(intent);
                //finish();
            } else {
                Toast.makeText(getApplicationContext(), "Identifiant ou mot de passe invalide", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
