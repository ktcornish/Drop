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

    User user  = new User();

    public void checkIfAchievementHasBeenReached(final Intent intent) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(user.getUid())
                .child("achievementdata");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long downVotesGiven = (long) dataSnapshot.child("downvotesgiven").getValue();
                long downVotesReceived = (long) dataSnapshot.child("downvotesreceived").getValue();
                long dropsPosted = (long) dataSnapshot.child("dropsposted").getValue();
                long upVotesGiven = (long) dataSnapshot.child("upvotesgiven").getValue();
                long upVotesReceived = (long) dataSnapshot.child("downvotesgiven").getValue();

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

//    public void upvotesRecievedAchievement(Long upVotesReceived, ImageView ivUpVotesReceived) {
//        // Upvotes received, gold, silver and bronze
//        if (upVotesReceived >= 5 && upVotesReceived < 20) {
//            ivUpVotesReceived.setImageResource(R.drawable.bron_placeholder);
//        } else if (upVotesReceived >= 20 && upVotesReceived < 100) {
//            ivUpVotesReceived.setImageResource(R.drawable.silver_placeholder);
//        } else if (upVotesReceived >= 100) {
//            ivUpVotesReceived.setImageResource(R.drawable.gold_placeholder);
//        }
//    }

    public void upvotesReceivedAchievement(Long downVotesGiven, Intent intent) {
        // Drops posted, gold, silver & bronze
        if (downVotesGiven < 5) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_none");
        } else if (downVotesGiven >= 5 && downVotesGiven < 20) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_bronze");
        } else if (downVotesGiven >= 20 && downVotesGiven < 100) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_silver");
        } else if (downVotesGiven >= 100 && downVotesGiven < 200 ) {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_gold");
        } else {
            intent.putExtra("upvotesReceivedAchievement", "upvotes_received_plat");
        }
    }

    public void upvotesGivenAchievement(Long downVotesGiven, Intent intent) {
        // Drops posted, gold, silver & bronze
        if (downVotesGiven < 5) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_none");
        } else if (downVotesGiven >= 5 && downVotesGiven < 20) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_bronze");
        } else if (downVotesGiven >= 20 && downVotesGiven < 100) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_silver");
        } else if (downVotesGiven >= 100 && downVotesGiven < 200 ) {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_gold");
        } else {
            intent.putExtra("upvotesGivenAchievement", "upvotes_given_plat");
        }
    }

    public void dropsPostedAchievement(Long downVotesGiven, Intent intent) {
        // Drops posted, gold, silver & bronze
        if (downVotesGiven < 5) {
            intent.putExtra("dropsPostedAchievement", "no_drops");
        } else if (downVotesGiven >= 5 && downVotesGiven < 20) {
            intent.putExtra("dropsPostedAchievement", "r_5_drops");
        } else if (downVotesGiven >= 20 && downVotesGiven < 50) {
            intent.putExtra("dropsPostedAchievement", "r_20_drops");
        } else if (downVotesGiven >= 50 && downVotesGiven < 100 ) {
            intent.putExtra("dropsPostedAchievement", "r_50_drops");
        } else {
            intent.putExtra("dropsPostedAchievement", "r_100_drops");
        }
    }

    public void firstDropPosted(Long dropsPosted, Intent intent) {
        // First drop posted
        if (dropsPosted > 0) {
            intent.putExtra("firstDropPostedAchievement", "r_1st_drop");
        } else {
            intent.putExtra("firstDropPostedAchievement", "r_1st_drop_none");
        }
    }


    public void downVotesReceivedAchievement(Long downVotesGiven, Intent intent) {
        // Drops posted, gold, silver & bronze
        if (downVotesGiven < 5) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_none");
        } else if (downVotesGiven >= 5 && downVotesGiven < 20) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_bronze");
        } else if (downVotesGiven >= 20 && downVotesGiven < 100) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_silver");
        } else if (downVotesGiven >= 100 && downVotesGiven < 200 ) {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_gold");
        } else {
            intent.putExtra("downVotesReceivedAchievement", "downvotes_received_plat");
        }
    }

    public void downVotesGivenAchievement(Long downVotesGiven, Intent intent) {
        // Drops posted, gold, silver & bronze
        if (downVotesGiven < 5) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_none");
        } else if (downVotesGiven >= 5 && downVotesGiven < 20) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_bronze");
        } else if (downVotesGiven >= 20 && downVotesGiven < 100) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_silver");
        } else if (downVotesGiven >= 100 && downVotesGiven < 200 ) {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_gold");
        } else {
            intent.putExtra("downVotesGivenAchievement", "downvotes_given_plat");
        }
    }


}