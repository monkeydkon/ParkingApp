package com.example.parkingapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("hours",0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channelName";
            String description = "channelDescription";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("456456", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Intent intent2 = new Intent(context, AlarmReceiver.class);
            intent2.setAction(Intent.ACTION_RUN);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "456456")
                    .setSmallIcon(R.drawable.ic_stat_name)
                 //   .setContentTitle(getResources().getString(R.string.warning))
                    .setContentText(String.valueOf(pref.getInt("hours",1))+" hour(s)")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(456456,builder.build());
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("hours", pref.getInt("hours",1) + 1);
            editor.commit();
        }
    }
}
