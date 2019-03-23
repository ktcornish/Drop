package com.steamybeans.drop.views;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.User;

public class MyAccount extends AppCompatActivity {

    private User user;
    private Authentication authentication;
    private TextView TVemail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        // Support toolbar in activity
        Toolbar toolbar = findViewById(R.id.toolbar_myaccount_top);
        setSupportActionBar(toolbar);

        //Set up classes
        authentication = new Authentication(this);
        user = new User();

        //check if user account is still active
        authentication.checkAccountIsActive();

        //set text views to user email
        TVemail = (TextView) findViewById(R.id.TVEmail);
        TVemail.setText(user.getEmail());

        setUpButtons();

        //REMOVE BEFORE FINAL BUILD
        debugLayout();
    }

    private void debugLayout() {
        //TODO REMOVE BEFORE FINAL BUILD
        TextView TVuserName = findViewById(R.id.TVuserName);
        ImageView IVprofileImage = findViewById(R.id.IVprofileImage);
        String email = user.getEmail();
        if(email.equals("ross@rossmail.com")) {
            TVuserName.setText("RossyB");
            IVprofileImage.setImageResource(R.drawable.ross_test);
        }
        if(email.equals("jeddhoppo@gmail.com")) {
            TVuserName.setText("Jedd");
            IVprofileImage.setImageResource(R.drawable.jedd_avatar);
        }

    }

    private void setUpButtons() {
        Button BTNlogOut = findViewById(R.id.BTNlogOut);

        BTNlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.signOut();
                startActivity(new Intent(MyAccount.this, LoginPage.class));
                Toast.makeText(MyAccount.this, "User successfully signed out", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume(){
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

        //check if user account is still active
        authentication.checkAccountIsActive();
    }

    // Populate toolbar with buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_myaccount_top_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Support onClick actions on options in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.TBclose:
                startActivity(new Intent(MyAccount.this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_up_from_bottom, R.anim.slide_down_from_top);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
