package com.steamybeans.drop.views;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Authentication;

public class LoginPage extends AppCompatActivity {

    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginpage);

        //check if user is already signed in
        authentication = new Authentication(this);
        authentication.alreadySignedIn();

        setUpButtons();
        }

        private void setUpButtons() {
        //declare page items
        Button BTNsignUp = findViewById(R.id.BTNsignUp);
        FloatingActionButton BTNlogin = findViewById(R.id.BTNlogin);
        final EditText ETemail = findViewById(R.id.ETloginEmailAddress);
        final EditText ETpassword = findViewById(R.id.ETloginPassword);

        BTNsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, Signup.class));
            }
        });

        BTNlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETemail.getText().toString().trim().length() < 1) {
                    ETemail.setError("Email is empty");
                } else if (ETpassword.getText().toString().trim().length() < 1) {
                    ETpassword.setError("Password is empty");
                } else {
                    String email = ETemail.getText().toString();
                    String password = ETpassword.getText().toString();
                    authentication.login(email, password);
                }
            }
        });
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
