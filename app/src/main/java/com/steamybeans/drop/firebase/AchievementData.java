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

    public void setDownVotesGiven(String userUid, final long change) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("downvotesgiven");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentVal = (long) dataSnapshot.getValue();
                databaseReference.setValue(change + currentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {}
        });
    }

    public void setDownVotesReceived(String userUid, final long change) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("downvotesreceived");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentVal = (long) dataSnapshot.getValue();
                databaseReference.setValue(change + currentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {}
        });
    }

    public void setDropsPosted(String userUid, final long change) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("dropsposted");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentVal = (long) dataSnapshot.getValue();
                databaseReference.setValue(change + currentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {}
        });
    }


    public void setDropsViewed(String userUid, final long change) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("dropsviewed");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentVal = (long) dataSnapshot.getValue();
                databaseReference.setValue(change + currentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {}
        });
    }

    public void setUpVotesGiven(String userUid, final long change) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("upvotesgiven");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentVal = (long) dataSnapshot.getValue();
                databaseReference.setValue(change + currentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {}
        });
    }

    public void setUpVotesReceived(String userUid, final long change) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("upvotesreceived");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long currentVal = (long) dataSnapshot.getValue();
                databaseReference.setValue(change + currentVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {}
        });
    }

    public void setProfilePicture(String userUid) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(userUid).child("achievementdata")
                .child("profilepicture");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.setValue(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError database) {}
        });
    }
}
