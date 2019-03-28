package com.steamybeans.drop.firebase;

import androidx.annotation.NonNull;

import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.steamybeans.drop.R;

public class Vote {
    private DatabaseReference databaseReference;


    public void makeAVote(final int voteValue, final String idOfDropper, final String postId, final TextView TVvotes, final ImageButton mainButton, final ImageButton otherButton, final int background) {
        final User user = new User();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(idOfDropper)
                .child("posts").child(postId).child("votes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).getValue() == null) {
                    databaseReference.child(user.getUid()).setValue(voteValue);
                    calculateVotesTotal(idOfDropper, postId, TVvotes);
                    addGiveVoteAchievementLogic(voteValue);
                    addReceiveVoteAchievementLogic(voteValue, idOfDropper);
                    mainButton.setBackgroundResource(background);
                } else if (dataSnapshot.child(user.getUid()).getValue(Integer.class) == voteValue) {
                    databaseReference.child(user.getUid()).setValue(null);
                    calculateVotesTotal(idOfDropper, postId, TVvotes);
                    cancelGiveVoteAchievementLogic(voteValue);
                    cancelReceiveVoteAchievementLogic(voteValue, idOfDropper);
                    mainButton.setBackgroundResource(R.drawable.buttonbackground);
                } else {
                    databaseReference.child(user.getUid()).setValue(voteValue);
                    calculateVotesTotal(idOfDropper, postId, TVvotes);
                    addGiveVoteAchievementLogic(voteValue);
                    cancelGiveVoteAchievementLogic(- voteValue);
                    addReceiveVoteAchievementLogic(voteValue, idOfDropper);
                    cancelReceiveVoteAchievementLogic(-voteValue, idOfDropper);
                    mainButton.setBackgroundResource(background);
                    otherButton.setBackgroundResource(R.drawable.buttonbackground);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void calculateVotesTotal(String userUid, String dropUid, final TextView TVvotes) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users")
                .child(userUid).child("posts").child(dropUid).child("votes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    counter += snapshot.getValue(Integer.class);
                }
                TVvotes.setText(String.valueOf(counter));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addGiveVoteAchievementLogic(int voteValue) {
        User user = new User();
        AchievementData adV = new AchievementData();

        if (voteValue == - 1) { adV.setDownVotesGiven(user.getUid(), 1); }
        else if (voteValue == 1) { adV.setUpVotesGiven(user.getUid(), 1); }
    }

    private void cancelGiveVoteAchievementLogic(int voteValue) {
        User user = new User();
        AchievementData adV = new AchievementData();

        if (voteValue == - 1) { adV.setDownVotesGiven(user.getUid(), - 1); }
        else if (voteValue == 1) { adV.setUpVotesGiven(user.getUid(), - 1); }
    }

    private void addReceiveVoteAchievementLogic(int voteValue, String idOfDropper) {
        AchievementData adV = new AchievementData();

        if (voteValue == - 1) { adV.setDownVotesReceived(idOfDropper, 1); }
        else if (voteValue == 1) { adV.setUpVotesReceived(idOfDropper,1); }
    }

    private void cancelReceiveVoteAchievementLogic(int voteValue, String idOfDropper) {
        AchievementData adV = new AchievementData();

        if (voteValue == - 1) { adV.setDownVotesReceived(idOfDropper, - 1); }
        else if (voteValue == 1) { adV.setUpVotesReceived(idOfDropper,- 1); }
    }
}
