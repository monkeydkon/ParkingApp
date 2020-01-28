package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ParkingActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button goToParkingSpotsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        mAuth = FirebaseAuth.getInstance();

       // button2 = findViewById(R.id.button2);
        goToParkingSpotsButton = findViewById(R.id.goToParkingSpotsButton);

        goToParkingSpotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParkingActivity.this, ParkingSpotsActivity.class));
            }
        });

        //Toast.makeText(getApplicationContext(),mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.getInstance().signOut();
//                Intent intent = new Intent(ParkingActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
