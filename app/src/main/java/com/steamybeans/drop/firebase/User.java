package com.steamybeans.drop.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {

    private FirebaseUser firebaseUser;

    public String uid() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = (firebaseUser != null) ? firebaseUser.getUid() : null;
        return userUid;
    }
}
