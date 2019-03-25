package com.steamybeans.drop.firebase;

import androidx.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vote {
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    public void makeAVote(final int voteValue, final String idOfDropper, final String postId, final TextView TVvotes) {
        final User user = new User();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(idOfDropper)
                .child("posts").child(postId).child("votes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).getValue() == null) {
                    databaseReference.child(user.getUid()).setValue(voteValue);
                    calculateVotesTotal(idOfDropper, postId, TVvotes);
                } else if (dataSnapshot.child(user.getUid()).getValue(Integer.class) == voteValue) {
                    databaseReference.child(user.getUid()).setValue(0);
                    calculateVotesTotal(idOfDropper, postId, TVvotes);
                } else {
                    databaseReference.child(user.getUid()).setValue(voteValue);
                    calculateVotesTotal(idOfDropper, postId, TVvotes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void calculateVotesTotal(String userUid, String dropUid, final TextView TVvotes) {
        firebaseDatabase = FirebaseDatabase.getInstance();
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

}
