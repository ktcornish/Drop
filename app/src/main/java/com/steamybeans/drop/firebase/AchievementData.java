package com.steamybeans.drop.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class AchievementData {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public void setDownVotesGiven(String userUid, final Integer change) {
        System.out.println(userUid);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("downvotesgiven");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("IN ON CHANGE");
                String currentVal = dataSnapshot.getValue().toString();
//                Integer currentVal = (Integer) dataSnapshot.getValue();
//                databaseReference.setValue(change + currentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {
                System.out.println("IN ON CANCELED");
            }
        });

    }


    /*
    Schema
    // Schematic attributes
    private String uid;
    private long downVotesGiven;
    private long downVotesReceived;
    private long dropsPosted;
    private long dropsViewed;
    private long upVotesGiven;
    private long upVotesReceived;
    private long xp;

    // Local getters
    public String getUid() { return this.uid; }
    public long getDownVotesGiven() { return  this.downVotesGiven; }
    public long getDownVotesReceived() { return this.downVotesReceived; }
    public long getDropsPosted() { return this.dropsPosted; }
    public long getDropsViewed() { return this.dropsViewed; }
    public long getUpVotesGiven() { return this.upVotesGiven; }
    public long getUpVotesReceived() { return this.upVotesReceived; }
    public long getXp() { return this.xp; }

    // Local setters
    public void setUid(String uid) { this.uid = uid; }
    public void setDvg(long dvg) { this.downVotesGiven = dvg; }
    public void setDvr(long dvr) { this.downVotesReceived = dvr; }
    public void setDp(long dp) { this.dropsPosted = dp; }
    public void setDv(long dv) { this.dropsViewed = dv; }
    public void setUvg(long uvg) { this.upVotesGiven = uvg; }
    public void setUvr(long uvr) { this.upVotesReceived = uvr; }
    public void setXp(long xp) { this.xp = xp; }
    */
}
