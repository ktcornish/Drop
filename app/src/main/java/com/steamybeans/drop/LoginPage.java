package com.steamybeans.drop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
public class LoginPage extends AppCompatActivity {

    private Button BTNsignUp;
    private FloatingActionButton BTNlogin;
    private String email;
    private String password;
    private FirebaseAuth firebaseAuth;
    private EditText ETemail;
    private EditText ETpassword;
    private static final String TAG = "LoginPage";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loginpage);
        firebaseAuth = FirebaseAuth.getInstance();
        init();
        }

        private void init() {
        BTNsignUp = (Button)findViewById(R.id.BTNsignUp);
        ETemail = (EditText)findViewById(R.id.ETloginEmailAddress);
        ETpassword = (EditText)findViewById(R.id.ETloginPassword);
        BTNlogin = (FloatingActionButton)findViewById(R.id.BTNlogin);

        BTNsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginPage.this, Signup.class));
            }
        });

        BTNlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ETemail.getText().toString();
                password = ETpassword.getText().toString();
                signin();
            }
        });
    }



    private void signin(){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in success
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(LoginPage.this, "Logged in " + user, Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign in fails
                            Log.w(TAG, "signInWithEmail:fail", task.getException());
                            Toast.makeText(LoginPage.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
