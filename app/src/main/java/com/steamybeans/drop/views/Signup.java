package com.steamybeans.drop.views;

import android.os.Bundle;
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
public class Signup extends AppCompatActivity {

    private Button BTNcompleteSignUp;
    private EditText ETsignupEmailAddress;
    private EditText ETsignupPassword;
    private String email;
    private String password;
    private Authentication authentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup);
        init();
        authentication = new Authentication(this);
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
                authentication.signUp(email, password);
            }
        });
    }
}