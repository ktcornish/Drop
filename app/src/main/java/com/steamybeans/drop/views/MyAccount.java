package com.steamybeans.drop.views;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MyAccount extends AppCompatActivity {

    private User user;
    private Authentication authentication;
    private TextView TVemail;
    final static int gallery_image = 1;



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
        ImageButton BTNupload = findViewById(R.id.BTNupload);

        BTNlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.signOut();
                startActivity(new Intent(MyAccount.this, LoginPage.class));
                Toast.makeText(MyAccount.this, "User successfully signed out", Toast.LENGTH_LONG).show();
            }
        });

        BTNupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, gallery_image);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==gallery_image && resultCode==RESULT_OK && data!=null){

            Uri ImageUri = data.getData();

            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(requestCode == RESULT_OK) {
                Uri resultUri = result.getUri();
            }

        }
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
