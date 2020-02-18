package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Button signupButton;
    EditText signUpFirstNameEditText, signUpLastNameEditText, signUpEmailEditText,signUpPasswordEditText,signUpConfirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        signupButton = findViewById(R.id.signupButton);
        signUpFirstNameEditText = findViewById(R.id.signUpFirstNameEditText);
        signUpLastNameEditText = findViewById(R.id.signUpLastNameEditText);
        signUpEmailEditText = findViewById(R.id.signUpEmailEditText);
        signUpPasswordEditText = findViewById(R.id.signUpPasswordEditText);
        signUpConfirmPasswordEditText = findViewById(R.id.signUpConfirmPasswordEditText);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUpFirstNameEditText.getText().toString().equals("")) {
                    signUpFirstNameEditText.setError(getResources().getString(R.string.requiredField));
                    return;
                }
                if (signUpLastNameEditText.getText().toString().equals("")) {
                    signUpLastNameEditText.setError(getResources().getString(R.string.requiredField));
                    return;
                }
                if (signUpEmailEditText.getText().toString().equals("")) {
                    signUpEmailEditText.setError(getResources().getString(R.string.requiredField));
                    return;
                }
                if (signUpPasswordEditText.getText().toString().equals("")) {
                    signUpPasswordEditText.setError(getResources().getString(R.string.requiredField));
                    return;
                }
                if(signUpPasswordEditText.getText().toString().length() < 8){
                    signUpPasswordEditText.setError(getResources().getString(R.string.passlesseight));
                    return;
                }
                if (!signUpConfirmPasswordEditText.getText().toString().equals(signUpPasswordEditText.getText().toString())) {
                    signUpConfirmPasswordEditText.setError(getResources().getString(R.string.equaltopassword));
                    return;
                }

                //firebase auth (is also auto login)
                mAuth.createUserWithEmailAndPassword(signUpEmailEditText.getText().toString(),signUpPasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println("user" + user.getUid());

                            try {
                                  String userObject = new JSONObject()
                                          .put("firstName",signUpFirstNameEditText.getText().toString())
                            .put("lastname", signUpLastNameEditText.getText().toString())
                            .put("email", signUpEmailEditText.getText().toString())
                            .toString();

                    Map<String, String> jsonMap = new Gson().fromJson(userObject, new TypeToken<HashMap<String, Object>>(){}.getType());
                    myRef.child(user.getUid()).setValue(jsonMap);

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                        }else {
                            System.out.println(task.getException());
                        }

                    }
                });
        }

        });
    }
}
