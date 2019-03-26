package com.steamybeans.drop.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.steamybeans.drop.R;
import com.steamybeans.drop.views.MyAccount;

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
    private static final String CHANNEL_NAME = "Drop";
    private static final String CHANNEL_DESC = "drop notifications";

    public void checkIfAchievmentHasBeenReached(String userUID, final String achievementName, final String title, final String content) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID)
                .child("notifications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(achievementName)) {
                    createNotification(title, content);
                    databaseReference.child(achievementName).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void createNotification(String title, String content) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ross_test);
//        Uri soundPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rain);

        Intent myIntent = new Intent(context, MyAccount.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.dropstretch)
                        .setColor(Color.BLUE)
                        .setContentTitle(title)
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
