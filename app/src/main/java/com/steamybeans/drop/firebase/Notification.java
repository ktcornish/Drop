package com.steamybeans.drop.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.steamybeans.drop.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification extends AppCompatActivity {

    private final Context context;
    private DatabaseReference databaseReference;

    public Notification(Context context) {
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

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_nav_drop_24dp)
                        .setContentTitle(title)
                        .setChannelId("drop")
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, mBuilder.build());
    }

}
