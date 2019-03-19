package com.steamybeans.drop.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    public String uid() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = (firebaseUser != null) ? firebaseUser.getUid() : null;
        return userUid;
    }

    public String email() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = (firebaseUser != null) ? firebaseUser.getEmail() : null;
        return userEmail;
    }

    public void signOut(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }
}
