package com.steamybeans.drop.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.steamybeans.drop.views.HomeActivity;
import com.steamybeans.drop.views.LoginPage;


public class Authentication extends AppCompatActivity {
    private final Context context;

    public Authentication(Context context) {
        this.context = context;
    }

    private FirebaseAuth firebaseAuth;

    public void login(String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in success
                            context.startActivity(new Intent(context, HomeActivity.class));
                        } else {
                            // Sign in fails
                            Toast.makeText(context, "This email and password combination is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signUp(String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            context.startActivity(new Intent(context, LoginPage.class));
                        } else {
                            // Sign up fails
                            Toast.makeText(context, "Can't create account:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void alreadySignedIn() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {context.startActivity(new Intent(context, HomeActivity.class));}
    }

    public void checkAccountIsActive() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.reload().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidUserException) {
                    context.startActivity(new Intent(context, LoginPage.class));
                }
            }
        });
    }

}
