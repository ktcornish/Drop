package com.steamybeans.drop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Signup extends AppCompatActivity {

    private Button BTNcompleteSignUp;
    private FirebaseAuth firebaseAuth;
    private EditText ETsignupEmailAddress;
    private EditText ETsignupPassword;
    private String email;
    private String password;
    private static final String TAG = "Signup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        init();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void init() {
        BTNcompleteSignUp = (Button)findViewById(R.id.BTNcompleteSignUp);

        BTNcompleteSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ETsignupEmailAddress = (EditText) findViewById(R.id.ETsignupEmailAddress);
                ETsignupPassword = (EditText) findViewById(R.id.ETsignupPassword);
                email = ETsignupEmailAddress.getText().toString();
                password = ETsignupPassword.getText().toString();
                createAccount();
            }
        });
    }

    public void createAccount() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(Signup.this, LoginPage.class));
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}