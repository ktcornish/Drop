package com.steamybeans.drop.views;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

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

    protected void onResume() {
        super.onResume();

        VideoView video = findViewById(R.id.VIDloginBG);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rain);
        video.setVideoURI(videoPath);
        video.start();
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
                CheckBox CBtermsAndConditions = findViewById(R.id.CBtermsAndConditions);

                if (ETsignupEmailAddress.getText().toString().trim().length() < 1) {
                    ETsignupEmailAddress.setError("Email is empty");
                } else if (ETsignupUsername.getText().toString().trim().length() < 1) {
                    ETsignupUsername.setError("Username is empty");
                } else if (ETsignupPassword.getText().toString().trim().length() < 1) {
                    ETsignupPassword.setError("Password is empty");
                } else {
                    String email = ETsignupEmailAddress.getText().toString();
                    String password = ETsignupPassword.getText().toString();
                    String username = ETsignupUsername.getText().toString();
                    if (CBtermsAndConditions.isChecked()) {
                        authentication.checkUsernameIsUnique(username, password, email);
                    } else {
                        Toast.makeText(Signup.this, "Please agree to the terms and conditions", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}