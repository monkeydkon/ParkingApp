package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ParkingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button goToParkingSpotsButton, goToMapsButton, logoutButton, languageButton, driveModeButton;
    Boolean showCarButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        goToParkingSpotsButton = findViewById(R.id.goToParkingSpotsButton);
        goToMapsButton = findViewById(R.id.goToMapsButton);
        logoutButton = findViewById(R.id.logoutButton);
        driveModeButton = findViewById(R.id.driveModeButton);
        languageButton = findViewById(R.id.languageButton);

        goToMapsButton.setEnabled(showCarButton);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Boolean paid = intent.getBooleanExtra("paid",false);

        if(paid){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "123123")
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentText(getResources().getString(R.string.paid))
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify((int)Math.ceil(Math.random()),builder.build());
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users").child(mAuth.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.toString());
                if(dataSnapshot.child("car").exists()){
                    showCarButton = true;
                    System.out.println("hi");
                }else{
                    showCarButton = false;
                }
                goToMapsButton.setEnabled(showCarButton);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        goToParkingSpotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkingActivity.this, ParkingSpotsActivity.class));
            }
        });

        goToMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkingActivity.this, MapsActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ParkingActivity.this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent1);
                finish();
                mAuth.signOut();
            }
        });

        driveModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ParkingActivity.this, DriveActivity.class));
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkingActivity.this, Language.class));
            }
        });

    }}

