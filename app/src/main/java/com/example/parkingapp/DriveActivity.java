package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DriveActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    LocationManager locManager;
    LocationListener li;
    TextView speedTextView;
    SensorEventListener sensorEventListener;
    LinearLayout myLayout;
    //Boolean illegal = false;
    Context that;

    private SensorManager sensorManager;
    private Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        myLayout = findViewById(R.id.myLayout);


        // create notification channel -> essential to send notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channelName";
            String description = "channelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123123", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // context to use outsite
        that = this;

        // check for permission
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(DriveActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }else{
                // ask for perimission
                ActivityCompat.requestPermissions(DriveActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
            }
        }else{
            // permission already granted
            speedTextView = findViewById(R.id.speedTextView);

            // start checking for location changes
            locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            li = new DriveActivity.speed();
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, li);
            System.out.println("goes");
        }


        // sensor for light events
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(light == null){
            Toast.makeText(this, getResources().getString(R.string.noLightSensor), Toast.LENGTH_SHORT).show();
        }

        sensorEventListener = new DriveActivity.light();

        sensorManager.registerListener(sensorEventListener, light, 13);

    }

    // when permission was given
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speedTextView = findViewById(R.id.speedTextView);
                    speedTextView.setText("loading...");

                    locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    li=new DriveActivity.speed();
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, li);
                    System.out.println("once more");
                } else {

                }
                return;
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        sensorManager.unregisterListener(sensorEventListener);
        locManager.removeUpdates(li);

    }


    class light implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.values[0] < 10){

                Intent intent = new Intent(that, DriveActivity.class);
                intent.setAction(Intent.ACTION_RUN);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "123123")
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle(getResources().getString(R.string.warning))
                        .setContentText("The lighting is low. Maybe you should open your lights.")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(that);
                notificationManagerCompat.notify((int)Math.ceil(Math.random()),builder.build());
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }



    // LOCATION LISTENER
    class speed implements LocationListener{
        @Override
        public void onLocationChanged(Location loc) {
            Float thespeed=loc.getSpeed() * 3.6f; // speed to km/h
            speedTextView.setText(String.valueOf((int)Math.ceil(thespeed)));
            System.out.println("goes here");
            if(loc.getSpeed() * 3.6 > -1){
              //  if(!illegal){
                    myLayout.setBackgroundColor(Color.rgb(255,0,0));

                    System.out.println("goes here too" + " " + loc.getSpeed());

                    Intent intent = new Intent(that, DriveActivity.class);
                    intent.setAction(Intent.ACTION_RUN);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "123123")
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setContentTitle(getResources().getString(R.string.warning))
                            .setContentText("You are above the speed limit!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(that);
                    notificationManagerCompat.notify((int)Math.ceil(Math.random()),builder.build());
             //   }

             //   illegal=true;

            }
        }

        @Override
        public void onProviderDisabled(String arg0) {}
        @Override
        public void onProviderEnabled(String arg0) {}
        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}

    }

}
