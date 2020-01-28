package com.example.parkingapp;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParkingSpotsActivity extends AppCompatActivity {

    View spot1,spot2,spot3,spot4,spot5,spot6,spot7,spot8,spot9,spot10,spot11,spot12,spot13,spot14,spot15,spot16,spot17,spot18,spot19,spot20,spot21,spot22,spot23,spot24;
    ArrayList<Spot> spotList;
    ArrayList<View> spotViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spots);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("spots");

        spotList = new ArrayList<>();
        spotViewList = new ArrayList<>();

        spotInits();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) return;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Spot spot = new Spot(ds.getKey(), (Boolean) ds.getValue());
                    spotList.add(spot);
                }

                for(int i = 0; i<spotList.size(); i++){
                    if (spotList.get(i).getValue()){
                        spotViewList.get(i).setBackgroundColor(Color.BLACK);
                    }else{
                        spotViewList.get(i).setBackgroundColor(Color.RED);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }



    private void spotInits() {
        spot1 = findViewById(R.id.spot1);
        spot2 = findViewById(R.id.spot2);
        spot3 = findViewById(R.id.spot3);
        spot4 = findViewById(R.id.spot4);
        spot5 = findViewById(R.id.spot5);
        spot6 = findViewById(R.id.spot6);
        spot7 = findViewById(R.id.spot7);
        spot8 = findViewById(R.id.spot8);
        spot9 = findViewById(R.id.spot9);
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

        spotViewList.add(spot1);
        spotViewList.add(spot2);
        spotViewList.add(spot3);
        spotViewList.add(spot4);
        spotViewList.add(spot5);
        spotViewList.add(spot6);
        spotViewList.add(spot7);
        spotViewList.add(spot8);
        spotViewList.add(spot9);
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
