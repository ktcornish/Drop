package com.steamybeans.drop.firebase;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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

    public void setDropContent(String userUid, String dropUid, final TextView dropContent) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users")
                .child(userUid).child("posts").child(dropUid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dropContent.setText(dataSnapshot.child("content").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
