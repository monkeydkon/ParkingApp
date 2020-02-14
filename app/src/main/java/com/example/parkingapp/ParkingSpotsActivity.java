package com.example.parkingapp;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ParkingSpotsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    View spot01,spot02,spot03,spot04,spot05,spot06,spot07,spot08,spot09,spot10,spot11,spot12,spot13,spot14,spot15,spot16,spot17,spot18,spot19,spot20,spot21,spot22,spot23,spot24;
    ArrayList<Spot> spotList;
    ArrayList<View> spotViewList;
    Context context;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spots);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("spots");

        mAuth = FirebaseAuth.getInstance();
        reference = database.getReference();

        context = this;

        spotList = new ArrayList<>();
        spotViewList = new ArrayList<>();

        spotInits();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) return;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Spot spot;
                    if(ds.child("by").exists()){
                        spot = new Spot(ds.getKey(), (Boolean) ds.child("available").getValue(), ds.child("by").getValue().toString());
                    }else{
                        spot = new Spot(ds.getKey(), (Boolean) ds.child("available").getValue());
                    }

                    spotList.add(spot);
                }

                for(int i = 0; i<spotList.size(); i++){
                    if (spotList.get(i).getValue()){
                        spotViewList.get(i).setBackgroundColor(Color.parseColor("#e2ffc4"));
                    }else{
                        spotViewList.get(i).setBackgroundColor(Color.parseColor("#f28574"));
                    }

                    if(spotList.get(i).getBy().equals(mAuth.getUid())){
                        spotViewList.get(i).setBackgroundColor(Color.parseColor("#111111"));
                    }
                }

//                reference.child("users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(!dataSnapshot.exists()) return;
//
//                        if (dataSnapshot.child("car").exists()){
//                           String ownSpot =  dataSnapshot.child("car").child("spot").getValue().toString();
//                           for(int j = 0; j<spotViewList.size(); j++){
//                               if(ownSpot.equals(getResources().getResourceEntryName(spotViewList.get(j).getId()))){
//                                   spotViewList.get(j).setBackgroundColor(Color.parseColor("#111111"));
//                               }
//                           }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
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

                    ColorDrawable viewColor = (ColorDrawable) spotViewList.get(i).getBackground();
                    int color = viewColor.getColor();


                    if(spotList.get(i).getBy().equals(mAuth.getUid())){
//                        Toast.makeText(getApplicationContext(), "yours bro ;)", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        builder.setMessage("You want to ")
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        database.getReference("users").child(mAuth.getUid()).child("car").child("datetime").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                                    Calendar cal = Calendar.getInstance();

                                    Date now = null;
                                    Date then = null;
                                    try {
                                        now = dateFormat.parse(dateFormat.format(cal.getTime()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    try {
                                        then = dateFormat.parse(dataSnapshot.getValue().toString());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    long diffInMillies = Math.abs(now.getTime() - then.getTime());
                                    long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

                                    Toast.makeText(getApplicationContext(), String.valueOf(diff), Toast.LENGTH_SHORT).show();

                                    }
//
                                }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                    if(spotList.get(i).getValue()){

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("You wanna close this spot? The price is 5$ per hour.");
                        builder.setCancelable(true);

                        final int finalI = i;
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(ParkingSpotsActivity.this, ParkingActivity.class));
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Calendar cal = Calendar.getInstance();
                            //    System.out.println();
                                reference.child("users").child(mAuth.getUid()).child("car").setValue(new Car(spotList.get(finalI).getKey(), dateFormat.format(cal.getTime())));
                                reference.child("spots").child(spotList.get(finalI).getKey()).child("available").setValue(false);
                                reference.child("spots").child(spotList.get(finalI).getKey()).child("by").setValue(mAuth.getUid());

                                dialog.cancel();
                                finish();
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
