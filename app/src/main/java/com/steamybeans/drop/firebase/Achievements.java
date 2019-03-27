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

//                // Downvotes received, gold, silver and bronze
//                downVotesReceivedAchievement(downVotesReceived, intent);
//
//                // First drop posted
//                firstDropPosted(dropsPosted, intent);
//
//                // Drops posted, gold, silver & bronze
//                dropsPostedAchievement(dropsPosted, intent);
//
//                // Upvotes given, gold, silver and bronze
//                upvotesGivenAchievement(upVotesGiven, intent);
//
//                // Upvotes received, gold, silver and bronze
//                upvotesRecievedAchievement(upVotesReceived, intent);
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
//
//    public void upvotesGivenAchievement(Long upVotesGiven, ImageView ivUpVotesGiven) {
//        // Upvotes given, gold, silver and bronze
//        if (upVotesGiven >= 5 && upVotesGiven < 20) {
//            ivUpVotesGiven.setImageResource(R.drawable.bron_placeholder);
//        } else if (upVotesGiven >= 20 && upVotesGiven < 100) {
//            ivUpVotesGiven.setImageResource(R.drawable.silver_placeholder);
//        } else if (upVotesGiven >= 100) {
//            ivUpVotesGiven.setImageResource(R.drawable.gold_placeholder);
//        }
//    }
//
//    public void dropsPostedAchievement(Long dropsPosted, ImageView ivDropsPosted) {
//        // Drops posted, gold, silver & bronze
//        if (dropsPosted >= 5) {
//            ivDropsPosted.setImageResource(R.drawable.bron_placeholder);
//        } else if (dropsPosted >= 20 && dropsPosted < 100) {
//            ivDropsPosted.setImageResource(R.drawable.silver_placeholder);
//        } else if (dropsPosted >= 100) {
//            ivDropsPosted.setImageResource(R.drawable.gold_placeholder);
//        }
//    }
//
//    public void firstDropPosted(Long dropsPosted, ImageView ivDropFirstPost) {
//        // First drop posted
//        if (dropsPosted > 0) {
//            ivDropFirstPost.setImageResource(R.drawable.purple_placeholder);
//        }
//    }
//
//    public void downVotesReceivedAchievement(Long downVotesReceived, ImageView ivDownVotesReceived) {
//        // Drops posted, gold, silver & bronze
//        if (downVotesReceived >= 5) {
//            ivDownVotesReceived.setImageResource(R.drawable.bron_placeholder);
//        } else if (downVotesReceived >= 20 && downVotesReceived < 100) {
//            ivDownVotesReceived.setImageResource(R.drawable.silver_placeholder);
//        } else if (downVotesReceived >= 100) {
//            ivDownVotesReceived.setImageResource(R.drawable.gold_placeholder);
//        }
//    }

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