package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Language extends AppCompatActivity {

    Button englishButton, greekButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        englishButton = findViewById(R.id.englishButton);
        greekButton = findViewById(R.id.greekButton);

        // get current language and disable button accordingly
        Locale current = getResources().getConfiguration().locale;
        System.out.println("LANGA" + current);
        if(current.toString().equals("gr")){
            greekButton.setEnabled(false);
        }else if(current.toString().equals("en_US")){
            englishButton.setEnabled(false);
        }


        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
            }
        });

        greekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("gr");
            }
        });
    }


    //set language
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        finish();
        startActivity(refresh);


    }
}
