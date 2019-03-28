package com.steamybeans.drop.firebase;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Achievements extends AppCompatActivity {

    final private Notifications notifications;
    private User user  = new User();

    public Achievements(Notifications notifications) {
        this.notifications = notifications;
    }

    public void checkIfAchievementHasBeenReached(final Intent intent) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(user.getUid())
                .child("achievementdata");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long downVotesGiven = (long) dataSnapshot.child("downvotesgiven").getValue();
                long downVotesReceived = (long) dataSnapshot.child("downvotesreceived").getValue();
                long dropsPosted = (long) dataSnapshot.child("dropsposted").getValue();
                long upVotesGiven = (long) dataSnapshot.child("upvotesgiven").getValue();
                long upVotesReceived = (long) dataSnapshot.child("upvotesreceived").getValue();
                long dropsViewed = (long) dataSnapshot.child("dropsviewed").getValue();
                long profilePicture = (long) dataSnapshot.child("profilepicture").getValue();


                // Downvotes given, gold, silver and bronze
                downVotesGivenAchievement(downVotesGiven, intent);

                // Downvotes received, gold, silver and bronze
                downVotesReceivedAchievement(downVotesReceived, intent);

                // First drop posted
                firstDropPosted(dropsPosted, intent);

                // Drops posted, gold, silver & bronze
                dropsPostedAchievement(dropsPosted, intent);

                // Upvotes given, gold, silver and bronze
                upvotesGivenAchievement(upVotesGiven, intent);

                // Upvotes received, gold, silver and bronze
                upvotesReceivedAchievement(upVotesReceived, intent);

                // Drops viewed
                dropsViewedAchievement(dropsViewed, intent);

                //profile picture updated
                profilePictureAchievement(profilePicture, intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void upvotesReceivedAchievement(Long upvotesReceived, Intent intent) {
        String result  = "none";

        // Drops posted, gold, silver & bronze
        if (upvotesReceived < 5) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_none");
        } else if (upvotesReceived >= 5 && upvotesReceived < 20) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_bronze");
            result = "bronze";
        } else if (upvotesReceived >= 20 && upvotesReceived < 100) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_silver");
            result = "silver";
        } else if (upvotesReceived >= 100 && upvotesReceived < 200 ) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_gold");
            result = "gold";
        } else {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_plat");
            result = "plat";
        }

        if (!result.equals("none")) {
            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "upvotesReceivedAchievement",
                    result,
                    "You've received the " + result + " achievement for upvotes received",
                    "upvotes_received_" + result,
                    intent
            );
        }

    }

    private void upvotesGivenAchievement(Long downVotesReceived, Intent intent) {
        String result = "none";

        // Drops posted, gold, silver & bronze
        if (downVotesReceived < 5) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_none");
        } else if (downVotesReceived >= 5 && downVotesReceived < 20) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_bronze");
            result = "bronze";
        } else if (downVotesReceived >= 20 && downVotesReceived < 100) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_silver");
            result = "silver";
        } else if (downVotesReceived >= 100 && downVotesReceived < 200 ) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_gold");
            result = "gold";
        } else {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_plat");
            result = "plat";
        }

        if (!result.equals("none")) {
            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "upvotesGivenAchievement",
                    result,
                    "You've received the " + result + " achievement for upvotes given",
                    "upvotes_given_" + result,
                    intent
            );
        }

    }

    private void dropsPostedAchievement(Long dropsPosted, Intent intent) {
        String result = "none";

        // Drops posted, gold, silver & bronze
        if (dropsPosted < 5) {
            intent.putExtra("dropsPostedAchievement", "drops_dropped_none");
        } else if (dropsPosted >= 5 && dropsPosted < 20) {
            intent.putExtra("dropsPostedAchievement", "drops_dropped_bronze");
            result = "bronze";
        } else if (dropsPosted >= 20 && dropsPosted < 50) {
            intent.putExtra("dropsPostedAchievement", "drops_dropped_silver");
            result = "silver";
        } else if (dropsPosted >= 50 && dropsPosted < 100 ) {
            intent.putExtra("dropsPostedAchievement", "drops_dropped_gold");
            result = "gold";
        } else {
            intent.putExtra("dropsPostedAchievement", "drops_dropped_plat");
            result = "plat";
        }

        if (!result.equals("none")) {
            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "dropsPostedAchievement",
                    result,
                    "You've received the " + result + " achievement for number of drops dropped",
                    "drops_dropped_" + result,
                    intent
            );
        }
    }

    private void firstDropPosted(Long dropsPosted, Intent intent) {
        // First drop posted
        if (dropsPosted > 0) {
            intent.putExtra("firstDropPostedAchievement", "r_1st_drop");

            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "firstDropPostedAchievement",
                    "true",
                    "You've received an achievement for your first drop",
                    "r_1st_drop",
                    intent
            );

        } else {
            intent.putExtra("firstDropPostedAchievement", "r_1st_drop_none");
        }
    }


    private void downVotesReceivedAchievement(Long downVotesReceived, Intent intent) {
        String result = "none";

        // Drops posted, gold, silver & bronze
        if (downVotesReceived < 5) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_none");
        } else if (downVotesReceived >= 5 && downVotesReceived < 20) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_bronze");
            result = "bronze";
        } else if (downVotesReceived >= 20 && downVotesReceived < 100) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_silver");
            result = "silver";
        } else if (downVotesReceived >= 100 && downVotesReceived < 200 ) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_gold");
            result = "gold";
        } else {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_plat");
            result = "plat";
        }

        if (!result.equals("none")) {
            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "downVotesReceivedAchievement",
                    result,
                    "You've received the " + result + " achievement for number of downvotes received",
                    "downvotes_received_" + result,
                    intent
            );
        }
    }

    private void downVotesGivenAchievement(Long downVotesGiven, Intent intent) {
        String result = "none";

        // Drops posted, gold, silver & bronze
        if (downVotesGiven < 5) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_none");
        } else if (downVotesGiven >= 5 && downVotesGiven < 20) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_bronze");
            result = "bronze";
        } else if (downVotesGiven >= 20 && downVotesGiven < 100) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_silver");
            result = "silver";
        } else if (downVotesGiven >= 100 && downVotesGiven < 200 ) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_gold");
            result = "gold";
        } else {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_plat");
            result = "plat";
        }

        if (!result.equals("none")) {
            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "downVotesGivenAchievement",
                    result,
                    "You've received the " + result + " achievement for number of downvotes given",
                    "downvotes_given_" + result,
                    intent
            );
        }
    }

    private void dropsViewedAchievement(Long dropsViewed, Intent intent) {
        String result = "none";

        // Drops posted, gold, silver & bronze
        if (dropsViewed < 5) {
            intent.putExtra("dropsViewedAchievement", "drops_viewed_none");
        } else if (dropsViewed >= 5 && dropsViewed < 20) {
            intent.putExtra("dropsViewedAchievement", "drops_viewed_bronze");
            result = "bronze";
        } else if (dropsViewed >= 20 && dropsViewed < 100) {
            intent.putExtra("dropsViewedAchievement", "drops_viewed_silver");
            result = "silver";
        } else if (dropsViewed >= 100 && dropsViewed < 200 ) {
            intent.putExtra("dropsViewedAchievement", "drops_viewed_gold");
            result = "gold";
        } else {
            intent.putExtra("dropsViewedAchievement", "drops_viewed_plat");
            result = "plat";
        }

        if (!result.equals("none")) {
            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "dropsViewedAchievement",
                    result,
                    "You've received the " + result + " achievement for number of drops viewed",
                    "drops_viewed_" + result,
                    intent
            );
        }
    }

    private void profilePictureAchievement(Long profilePicture, Intent intent) {
        // First drop posted
        if (profilePicture == 1) {
            intent.putExtra("profilePictureAchievement", "profile_picture_achievement");

            notifications.checkIfNotificationHasAlreadyBeenSent(
                    user.getUid(),
                    "profilePictureAchievement",
                    "true",
                    "You've received an achievement for uploading a profile picture",
                    "r_1st_drop",
                    intent
            );

        } else {
            intent.putExtra("profilePictureAchievement", "r_1st_drop_none");
        }
    }

}