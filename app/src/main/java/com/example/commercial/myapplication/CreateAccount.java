package com.example.commercial.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commercial.myapplication.api.CreationAsync;

import java.util.Calendar;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DJEASSIDJESS CRETIN on 25/04/2016.
 */
public class CreateAccount extends AppCompatActivity {

    EditText password;
    EditText username;
    EditText email_to_login;
    EditText Confpassword;

    NumberPicker np;
    NumberPicker np2;
    NumberPicker np3;

    TextView JJ;
    TextView MM;
    TextView YYYY;
    int yearnow;

    private static String PHPurl = "http://www.tv.kabtel.com/new2.php?";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the layout from video_main.xml
        setContentView(R.layout.create_account);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        email_to_login = (EditText) findViewById(R.id.email_to_login);
        Confpassword = (EditText) findViewById(R.id.Confirmpassword);

        np = (NumberPicker) findViewById(R.id.numberPicker1);
        np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        np3 = (NumberPicker) findViewById(R.id.numberPicker3);

        JJ = (TextView) findViewById(R.id.textView1);
        MM = (TextView) findViewById(R.id.textView2);
        YYYY = (TextView) findViewById(R.id.textView3);

        np.setMinValue(0);
        np.setMaxValue(31);
        np.setWrapSelectorWheel(false);

        np2.setMinValue(0);
        np2.setMaxValue(12);
        np2.setWrapSelectorWheel(false);

        np3.setMinValue(1950);
        np3.setMaxValue(2020);
        np3.setWrapSelectorWheel(false);

        Calendar dateyear = Calendar.getInstance();


        yearnow = dateyear.get(dateyear.YEAR);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > 9) {
                    JJ.setText(String.valueOf(newVal) + "/");
                } else {
                    JJ.setText("0" + String.valueOf(newVal) + "/");
                }
            }
        });

        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > 9) {
                    MM.setText(String.valueOf(newVal) + "/");
                } else {
                    MM.setText("0" + String.valueOf(newVal) + "/");
                }
            }
        });

        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                YYYY.setText(String.valueOf(newVal));
            }
        });

    }

    public void Retourhome(View view) {
        Intent intent3 = new Intent(CreateAccount.this, MainActivity.class);
        startActivity(intent3);
        finish();
    }

    public void Creationbdd(View view) {

        String usernametxt = username.getText().toString();
        String passwordtxt = password.getText().toString();
        String emailtxt = email_to_login.getText().toString();
        String Confpasswordtxt = Confpassword.getText().toString();
        String datetxt = JJ.getText().toString() + MM.getText().toString() + YYYY.getText().toString();

        Log.i("CLE A VOIR", "VALEUR DE DATETXT " + datetxt);

        if (!usernametxt.equals("") && !passwordtxt.equals("") && !emailtxt.equals("") && !Confpasswordtxt.equals("")) {
            if (!isEmailValid(emailtxt)) {
                Toast.makeText(getApplicationContext(), "Adresse mail invalid", Toast.LENGTH_LONG).show();
                return;
            }

            if (!Lengthword(passwordtxt)) {
                Toast.makeText(getApplicationContext(), "Mots de passe minimum 8 caracteres", Toast.LENGTH_LONG).show();
                return;
            }

            if (!Lengthword(usernametxt)) {
                Toast.makeText(getApplicationContext(), "Identifiant minimum 8 caracteres", Toast.LENGTH_LONG).show();
                return;
            }

            if (!Confpasswordtxt.equals(passwordtxt)) {
                Toast.makeText(getApplicationContext(), "Mots de passe différent", Toast.LENGTH_LONG).show();
                return;
            }


            if (JJ.getText().toString().equals("JJ/ ") || MM.getText().toString().equals("MM/ ") || YYYY.getText().toString().equals("AAAA ")) {
                Toast.makeText(getApplicationContext(), "Date invalid", Toast.LENGTH_LONG).show();
                return;
            }

        } else {
            Toast.makeText(getApplicationContext(), "Veuillez remplir les champs", Toast.LENGTH_LONG).show();
            return;
        }

        creation(usernametxt, passwordtxt, emailtxt, datetxt);
        Toast.makeText(getApplicationContext(), "Compte Créer", Toast.LENGTH_LONG).show();
    }

    //methode qui permet de savoir si ladresse mail rentré est valide ou pas
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean Lengthword(String word) {
        int taille = word.length();
        boolean result = false;

        if (taille < 8) {
            result = false;
        } else
            result = true;

        return (result);
    }

    private void creation(final String username, String password, String email, String datetxt) {
        CreationAsync la = new CreationAsync(CreateAccount.this);
        la.execute(username, password, PHPurl, email, datetxt);
        String s = null;
        try {
            s = la.get().trim();

            if (s.equalsIgnoreCase("ajouter")) {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                //intent.putExtra(USER_NAME, username);

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Identifiant existant", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}