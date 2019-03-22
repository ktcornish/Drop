package com.steamybeans.drop.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Authentication;

public class Signup extends AppCompatActivity {

    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        setUpButtons();
    }

    private void setUpButtons() {
        authentication = new Authentication(this);
        Button BTNcompleteSignUp = findViewById(R.id.BTNcompleteSignUp);

        BTNcompleteSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ETsignupEmailAddress = findViewById(R.id.ETsignupEmailAddress);
                EditText ETsignupPassword = findViewById(R.id.ETsignupPassword);
                EditText ETsignupUsername = findViewById(R.id.ETsignupUsername);
                String email = ETsignupEmailAddress.getText().toString();
                String password = ETsignupPassword.getText().toString();
                String username = ETsignupUsername.getText().toString();
                CheckBox CBtermsAndConditions = findViewById(R.id.CBtermsAndConditions);
                if (CBtermsAndConditions.isChecked()) {
                    authentication.checkUsernameIsUnique(username, password, email);
                } else {
                    Toast.makeText(Signup.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}