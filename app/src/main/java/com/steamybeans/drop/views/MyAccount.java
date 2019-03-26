package com.steamybeans.drop.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

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
                                                Toast.makeText(MyAccount.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(MyAccount.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            loadingBar.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(authentication, "Error Occured: Image can't be cropped", Toast.LENGTH_SHORT).show();
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

    private void updateAchievementGraphics() {
        final ImageView ivDownVotesGiven = findViewById(R.id.IVdownVotesGiven);
        final ImageView ivDownVotesReceived = findViewById(R.id.IVdownVotesReceived);
        final ImageView ivDropFirstPost = findViewById(R.id.IVdropFirstPost);
        final ImageView ivDropsPosted = findViewById(R.id.IVdropsPosted);
        final ImageView ivUpVotesGiven = findViewById(R.id.IVupVotesGiven);
        final ImageView ivUpVotesReceived = findViewById(R.id.IVupVotesReceived);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(user.getUid())
                .child("achievementdata");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long downVotesGiven = (long) dataSnapshot.child("downvotesgiven").getValue();
                long downVotesReceived = (long) dataSnapshot.child("downvotesreceived").getValue();
                long dropsPosted = (long) dataSnapshot.child("dropsposted").getValue();
                long upVotesGiven = (long) dataSnapshot.child("upvotesgiven").getValue();
                long upVotesReceived = (long) dataSnapshot.child("downvotesgiven").getValue();

                // Downvotes given, gold, silver and bronze
                if (downVotesGiven >= 5) {
                    ivDownVotesGiven.setImageResource(R.drawable.bron_placeholder);
                } else if (downVotesGiven >= 20 && downVotesGiven < 100) {
                    ivDownVotesGiven.setImageResource(R.drawable.silver_placeholder);
                } else if (downVotesGiven >= 100) {
                    ivDownVotesGiven.setImageResource(R.drawable.gold_placeholder);
                }

                // Downvotes received, gold, silver and bronze
                if (downVotesReceived >= 5) {
                    ivDownVotesReceived.setImageResource(R.drawable.bron_placeholder);
                } else if (downVotesReceived >= 20 && downVotesReceived < 100) {
                    ivDownVotesReceived.setImageResource(R.drawable.silver_placeholder);
                } else if (downVotesReceived >= 100) {
                    ivDownVotesReceived.setImageResource(R.drawable.gold_placeholder);
                }

                // First drop posted
                if (dropsPosted > 0) {
                    ivDropFirstPost.setImageResource(R.drawable.purple_placeholder);
                }

                // Drops posted, gold, silver & bronze
                if (dropsPosted >= 5) {
                    ivDropsPosted.setImageResource(R.drawable.bron_placeholder);
                } else if (downVotesReceived >= 20 && dropsPosted < 100) {
                    ivDropsPosted.setImageResource(R.drawable.silver_placeholder);
                } else if (dropsPosted >= 100) {
                    ivDropsPosted.setImageResource(R.drawable.gold_placeholder);
                }

                // Upvotes given, gold, silver and bronze
                if (upVotesGiven >= 5 && upVotesGiven < 20) {
                    ivUpVotesGiven.setImageResource(R.drawable.bron_placeholder);
                } else if (upVotesGiven >= 20 && upVotesGiven < 100) {
                    ivUpVotesGiven.setImageResource(R.drawable.silver_placeholder);
                } else if (upVotesGiven >= 100) {
                    ivUpVotesGiven.setImageResource(R.drawable.gold_placeholder);
                }

                // Upvotes received, gold, silver and bronze
                if (upVotesReceived >= 5 && upVotesReceived < 20) {
                    ivUpVotesReceived.setImageResource(R.drawable.bron_placeholder);
                } else if (upVotesReceived >= 20 && upVotesReceived < 100) {
                    ivUpVotesReceived.setImageResource(R.drawable.silver_placeholder);
                } else if (upVotesReceived >= 100) {
                    ivUpVotesReceived.setImageResource(R.drawable.gold_placeholder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
