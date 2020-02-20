package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    TextView goToSignUpTextView;
    EditText loginEmailEditText, loginPasswordEditText;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);

        loginEmailEditText = findViewById(R.id.loginEmalEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        goToSignUpTextView = findViewById(R.id.goToSignUpTextView);

        goToSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginEmailEditText.getText().toString().equals("")){
                    loginEmailEditText.setError(getResources().getString(R.string.requiredField));
                    return;
                }
                if(loginPasswordEditText.getText().toString().equals("")){
                    loginPasswordEditText.setError(getResources().getString(R.string.requiredField));
                    return;
                }

                String email = loginEmailEditText.getText().toString();
                String password = loginPasswordEditText.getText().toString();

                //firebase authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    System.out.println("user logged" + user);
                                    Intent intent = new Intent(MainActivity.this, ParkingActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    System.out.println("login error " + task.getException());
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            Toast.makeText(getApplicationContext(), "Already Logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ParkingActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
