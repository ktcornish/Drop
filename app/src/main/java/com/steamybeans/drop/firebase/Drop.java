package com.steamybeans.drop.firebase;

import androidx.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Drop {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public void newDrop(final String drop, String uid, final double currentLatitude, final double currentLongitude) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users")
                .child(uid).child("posts").push();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child("content").setValue(drop);
                databaseReference.child("latitude").setValue(currentLatitude);
                databaseReference.child("longitude").setValue(currentLongitude);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setDropContent(final String userUid, final String dropUid, final TextView dropContent, final TextView username) {
        System.out.println("SETTING DROP CONTENT!!!");
        System.out.println(dropUid);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users")
                .child(userUid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dropContent.setText(dataSnapshot.child("posts").child(dropUid).child("content").getValue().toString());
                username.setText(dataSnapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
