package com.steamybeans.drop.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.steamybeans.drop.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification extends AppCompatActivity {

    private final Context context;

    public Notification(Context context) {
        this.context = context;
    }

    private static final String CHANNEL_ID = "drop";
    private static final String CHANNEL_NAME = "Drop";
    private static final String CHANNEL_DESC = "drop notifications";

    public void createNotification(String title, String content) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_nav_drop_24dp)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, mBuilder.build());
    }


    public void displayNotification() {


    }


}
