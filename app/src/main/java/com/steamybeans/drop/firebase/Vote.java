package com.steamybeans.drop.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vote {

    public void makeAVote(final int voteValue, String idOfDropper, String postId) {
        final User user = new User();
        final DatabaseReference databaseReference;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(idOfDropper)
                .child("posts").child(postId).child("votes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child(user.getUid()).setValue(voteValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
