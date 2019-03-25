package com.steamybeans.drop.firebase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
//import
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//end
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.steamybeans.drop.R;
import com.steamybeans.drop.views.HomeActivity;
import com.steamybeans.drop.views.LoginPage;

import java.util.ArrayList;


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

    public void checkUsernameIsUnique(final String username, final String password, final String email) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList usernames = new ArrayList();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    usernames.add(snapshot.child("username").getValue().toString());
                }

                if (usernames.contains(username)) {
                    Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(email, password, username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void signUp(String email, String password, final String username) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User signedUpUser= new User();
                            String uid = signedUpUser.getUid();
                            addUserToDatabase(uid, username);
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


    private void addUserToDatabase(final String uid, final String username) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child(uid).child("username").setValue(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
