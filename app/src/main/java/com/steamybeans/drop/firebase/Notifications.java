package com.steamybeans.drop.firebase;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.steamybeans.drop.R;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications extends AppCompatActivity {

    private final Context context;
    private DatabaseReference databaseReference;

    public Notifications(Context context) {
        this.context = context;
    }

    private static final String CHANNEL_ID = "drop";

    public void checkIfNotificationHasAlreadyBeenSent(String userUID, final String achievementName, final String achievementLevel, final String content, final String imageName, final Intent myIntent) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID)
                .child("notifications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(achievementName) || !dataSnapshot.child(achievementName).getValue().toString().equals(achievementLevel)) {
                    createNotification(content, imageName, myIntent);
                    databaseReference.child(achievementName).setValue(achievementLevel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void createNotification(String content, String imageName, Intent myIntent) {
        int image = context.getResources().getIdentifier(imageName,"drawable", context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), image);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.dropstretch)
                        .setColor(Color.BLUE)
                        .setContentTitle("Achievement reached")
                        .setWhen(Calendar.getInstance().getTimeInMillis())
                        .setLargeIcon(bitmap)
                        .setChannelId("drop")
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, mBuilder.build());
    }

}
