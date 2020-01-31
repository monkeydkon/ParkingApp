package com.example.parkingapp;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParkingSpotsActivity extends AppCompatActivity {

    View spot01,spot02,spot03,spot04,spot05,spot06,spot07,spot08,spot09,spot10,spot11,spot12,spot13,spot14,spot15,spot16,spot17,spot18,spot19,spot20,spot21,spot22,spot23,spot24;
    ArrayList<Spot> spotList;
    ArrayList<View> spotViewList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spots);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("spots");

        context = this;

        spotList = new ArrayList<>();
        spotViewList = new ArrayList<>();

        spotInits();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) return;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Spot spot = new Spot(ds.getKey(), (Boolean) ds.child("available").getValue());
                    spotList.add(spot);
                }

                for(int i = 0; i<spotList.size(); i++){
                    if (spotList.get(i).getValue()){
                        spotViewList.get(i).setBackgroundColor(Color.parseColor("#e2ffc4"));
                    }else{
                        spotViewList.get(i).setBackgroundColor(Color.parseColor("#f28574"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setListeners();




    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("wow "+ getResources().getResourceEntryName(v.getId()));
            for(int i = 0; i<spotList.size(); i++){
                if(spotList.get(i).getKey().equals(getResources().getResourceEntryName(v.getId()))){
                    if(spotList.get(i).getValue()){

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("You wanna close this spot? The price is 5$ per hour.");
                        builder.setCancelable(true);

                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        Toast.makeText(getApplicationContext(), "This spot is not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };


    private void setListeners(){
        spot01.setOnClickListener(listener);
        spot02.setOnClickListener(listener);
        spot03.setOnClickListener(listener);
        spot04.setOnClickListener(listener);
        spot05.setOnClickListener(listener);
        spot06.setOnClickListener(listener);
        spot07.setOnClickListener(listener);
        spot08.setOnClickListener(listener);
        spot09.setOnClickListener(listener);
        spot10.setOnClickListener(listener);
        spot11.setOnClickListener(listener);
        spot12.setOnClickListener(listener);
        spot13.setOnClickListener(listener);
        spot14.setOnClickListener(listener);
        spot15.setOnClickListener(listener);
        spot16.setOnClickListener(listener);
        spot17.setOnClickListener(listener);
        spot18.setOnClickListener(listener);
        spot19.setOnClickListener(listener);
        spot20.setOnClickListener(listener);
        spot21.setOnClickListener(listener);
        spot22.setOnClickListener(listener);
        spot23.setOnClickListener(listener);
        spot24.setOnClickListener(listener);

    }

    private void spotInits() {
        spot01 = findViewById(R.id.spot01);
        spot02 = findViewById(R.id.spot02);
        spot03 = findViewById(R.id.spot03);
        spot04 = findViewById(R.id.spot04);
        spot05 = findViewById(R.id.spot05);
        spot06 = findViewById(R.id.spot06);
        spot07 = findViewById(R.id.spot07);
        spot08 = findViewById(R.id.spot08);
        spot09 = findViewById(R.id.spot09);
        spot10 = findViewById(R.id.spot10);
        spot11 = findViewById(R.id.spot11);
        spot12 = findViewById(R.id.spot12);
        spot13 = findViewById(R.id.spot13);
        spot14 = findViewById(R.id.spot14);
        spot15 = findViewById(R.id.spot15);
        spot16 = findViewById(R.id.spot16);
        spot17 = findViewById(R.id.spot17);
        spot18 = findViewById(R.id.spot18);
        spot19 = findViewById(R.id.spot19);
        spot20 = findViewById(R.id.spot20);
        spot21 = findViewById(R.id.spot21);
        spot22 = findViewById(R.id.spot22);
        spot23 = findViewById(R.id.spot23);
        spot24 = findViewById(R.id.spot24);

        spotViewList.add(spot01);
        spotViewList.add(spot02);
        spotViewList.add(spot03);
        spotViewList.add(spot04);
        spotViewList.add(spot05);
        spotViewList.add(spot06);
        spotViewList.add(spot07);
        spotViewList.add(spot08);
        spotViewList.add(spot09);
        spotViewList.add(spot10);
        spotViewList.add(spot11);
        spotViewList.add(spot12);
        spotViewList.add(spot13);
        spotViewList.add(spot14);
        spotViewList.add(spot15);
        spotViewList.add(spot16);
        spotViewList.add(spot17);
        spotViewList.add(spot18);
        spotViewList.add(spot19);
        spotViewList.add(spot20);
        spotViewList.add(spot21);
        spotViewList.add(spot22);
        spotViewList.add(spot23);
        spotViewList.add(spot24);



    }
}
