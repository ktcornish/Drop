package com.steamybeans.drop.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Authentication;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginPage extends AppCompatActivity {

    private Button BTNsignUp;
    private FloatingActionButton BTNlogin;
    private String email;
    private String password;
    private EditText ETemail;
    private EditText ETpassword;
    private Authentication authentication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loginpage);
        authentication = new Authentication(this);
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
                authentication.login(email, password);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        authentication = new Authentication(this);
        authentication.alreadySignedIn();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
