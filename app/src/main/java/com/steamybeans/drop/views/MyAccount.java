package com.steamybeans.drop.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.AchievementData;
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MyAccount extends AppCompatActivity {

    private User user;
    private Authentication authentication;
    private TextView TVemail;
    private TextView TVuserName;
    final static int gallery_image = 1;
    private StorageReference userProfileImageRef;
    private DatabaseReference userRef;
    private ProgressDialog loadingBar;
    private ImageView IVprofileImage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        //Set up classes
        authentication = new Authentication(this);
        user = new User();
        IVprofileImage = (ImageView) findViewById(R.id.IVprofileImage);

        //Set Up database & Storage
        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        // Support toolbar in activity
        Toolbar toolbar = findViewById(R.id.toolbar_myaccount_top);
        setSupportActionBar(toolbar);

        //check if user account is still active
        authentication.checkAccountIsActive();

        //set text views to user email & Username;
        TVemail = (TextView) findViewById(R.id.TVEmail);
        TVuserName = (TextView) findViewById(R.id.TVuserName);
        userRef.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TVuserName.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TVemail.setText(user.getEmail());


        setUpButtons();
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
        // THIS UPDATES THE PROFILE IMAGE BOX
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StorageReference UserPic = userProfileImageRef.child(user.getUid() + ".jpg");
                    try {
                        final File localFile = File.createTempFile("images", "jpg");
                        UserPic.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                IVprofileImage.setImageBitmap(bitmap);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                            }
                        });
                    } catch (IOException e) {
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallery_image && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                loadingBar = new ProgressDialog(this);

                loadingBar.setTitle("Uploading Image");
                loadingBar.setMessage("Just a sec while we upload your image");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri croppedImageUri = result.getUri();

                StorageReference filePath = userProfileImageRef.child(user.getUid() + ".jpg");
                filePath.putFile(croppedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(MyAccount.this, "Profile Image Uploaded", Toast.LENGTH_SHORT).show();

                            final String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                            userRef.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(MyAccount.this, "Profile Updated", Toast.LENGTH_LONG).show();
                                                AchievementData achievementData = new AchievementData();
                                                achievementData.setProfilePicture(user.getUid());
                                            } else {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(MyAccount.this, "Error:" + message, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            loadingBar.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(authentication, "Error Occured: Image can't be cropped", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onResume() {
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

        updateAchievementGraphics();

        setAcheivementDescriptionText();

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
        switch (item.getItemId()) {
            case R.id.TBclose:
                startActivity(new Intent(MyAccount.this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_up_from_bottom, R.anim.slide_down_from_top);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Set achievement description text when user clicks achievement badge
    private void setAcheivementDescriptionText() {
        final ImageView ivDownVotesGiven = findViewById(R.id.IVdownVotesGiven);
        final ImageView ivDownVotesReceived = findViewById(R.id.IVdownVotesReceived);
        final ImageView ivDropsPosted = findViewById(R.id.IVdropsPosted);
        final ImageView ivDropsViewed = findViewById(R.id.IVdropsViewed);
        final ImageView ivDropFirstPost = findViewById(R.id.IVdropFirstPost);
        final ImageView ivProfilePic = findViewById(R.id.IVprofilePicture);
        final ImageView ivUpVotesGiven = findViewById(R.id.IVupVotesGiven);
        final ImageView ivUpVotesReceived = findViewById(R.id.IVupVotesReceived);
        final TextView et = findViewById(R.id.TVachievmentDescription);
        final TextView title = findViewById(R.id.TVachievementTitle);


        ivDownVotesGiven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_downvotes_given));
                title.setText(getString(R.string.achievement_title_downvotes_given));
            }
        });

        ivDownVotesReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_downvotes_received));
                title.setText(getString(R.string.achievement_title_downvotes_received));
            }
        });

        ivDropsPosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_drops_posted));
                title.setText(getString(R.string.achievement_title_drops_posted));
            }
        });

        ivDropsViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_drops_viewed));
                title.setText(getString(R.string.achievement_title_drops_viewed));
            }
        });

        ivDropFirstPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_first_drop));
                title.setText(getString(R.string.achievement_title_first_drop));
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_profile_pic));
                title.setText(getString(R.string.achievement_title_profile_pic));
            }
        });

        ivUpVotesGiven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_upvotes_given));
                title.setText(getString(R.string.achievement_title_upvotes_given));
            }
        });

        ivUpVotesReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(getString(R.string.achievement_desc_upvotes_received));
                title.setText(getString(R.string.achievement_title_upvotes_received));
            }
        });
    }

    private void updateAchievementGraphics() {
        ImageView ivDownVotesGiven = findViewById(R.id.IVdownVotesGiven);
        ImageView ivDownVotesReceived = findViewById(R.id.IVdownVotesReceived);
        ImageView ivDropFirstPost = findViewById(R.id.IVdropFirstPost);
        ImageView ivDropsPosted = findViewById(R.id.IVdropsPosted);
        ImageView ivUpVotesGiven = findViewById(R.id.IVupVotesGiven);
        ImageView ivUpVotesReceived = findViewById(R.id.IVupVotesReceived);
        ImageView ivDropsViewed = findViewById(R.id.IVdropsViewed);
        ImageView ivProfilePicture = findViewById(R.id.IVprofilePicture);
        Bundle extras = getIntent().getExtras();
        Context context = MyAccount.this;

        //set images from intent
        int DownVotesGivenImage = getResources().getIdentifier(extras.getString("downVotesGivenAchievement"),"drawable", context.getPackageName());
        int DownVotesReceivedImage = getResources().getIdentifier(extras.getString("downVotesReceivedAchievement"),"drawable", context.getPackageName());
        int FirstDropImage = getResources().getIdentifier(extras.getString("firstDropPostedAchievement"),"drawable", context.getPackageName());
        int DropPostedImage = getResources().getIdentifier(extras.getString("dropsPostedAchievement"),"drawable", context.getPackageName());
        int UpVotesReceived = getResources().getIdentifier(extras.getString("upvotesReceivedAchievement"),"drawable", context.getPackageName());
        int UpVotesGiven = getResources().getIdentifier(extras.getString("upvotesGivenAchievement"),"drawable", context.getPackageName());
        int DropsViewed = getResources().getIdentifier(extras.getString("dropsViewedAchievement"),"drawable", context.getPackageName());
        int ProfilePicture = getResources().getIdentifier(extras.getString("profilePictureAchievement"),"drawable", context.getPackageName());

        //set ImageViews
        ivDownVotesGiven.setImageResource(DownVotesGivenImage);
        ivDownVotesReceived.setImageResource(DownVotesReceivedImage);
        ivDropFirstPost.setImageResource(FirstDropImage);
        ivDropsPosted.setImageResource(DropPostedImage);
        ivUpVotesGiven.setImageResource(UpVotesReceived);
        ivUpVotesReceived.setImageResource(UpVotesGiven);
        ivDropsViewed.setImageResource(DropsViewed);
        ivProfilePicture.setImageResource(ProfilePicture);
    }
}
