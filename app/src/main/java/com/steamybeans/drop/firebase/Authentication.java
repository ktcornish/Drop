package com.steamybeans.drop.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.steamybeans.drop.views.LoginPage;
import com.steamybeans.drop.views.home_activity.HomeActivity;


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
                            Log.d("Log In", "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            context.startActivity(new Intent(context, HomeActivity.class));
                        } else {
                            // Sign in fails
                            Log.w("Log In", "signInWithEmail:fail", task.getException());
                            Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signup(String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Sign Up", "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            context.startActivity(new Intent(context, LoginPage.class));
                        } else {
                            Log.w("Sign Up", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
