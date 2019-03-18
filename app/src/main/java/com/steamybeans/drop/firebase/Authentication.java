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
import com.steamybeans.drop.views.home.HomeActivity;


public class Authentication extends AppCompatActivity {
    private final Context context;

    public Authentication(Context context) {
        this.context = context;
    }

    private FirebaseAuth firebaseAuth;

    public void signin(String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Authentication.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in success
                            Log.d("Sign In", "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent i1 = new Intent(context, HomeActivity.class);
                            context.startActivity(i1);
                        } else {
                            // Sign in fails
                            Log.w("Sign In", "signInWithEmail:fail", task.getException());
                            Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
