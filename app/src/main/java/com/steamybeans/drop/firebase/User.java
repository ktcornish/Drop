package com.steamybeans.drop.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    public String uid() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return (firebaseUser != null) ? firebaseUser.getUid() : null;
    }

    public String getEmail() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return (firebaseUser != null) ? firebaseUser.getEmail() : null;
    }

    public void signOut(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }
}
