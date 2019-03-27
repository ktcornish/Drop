package com.steamybeans.drop.map;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.AchievementData;
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.Drop;
import com.steamybeans.drop.firebase.Vote;

import java.io.File;
import java.io.IOException;

public class Map extends AppCompatActivity {
    private final Context context;
    private DatabaseReference userRef;
    private StorageReference userProfileImageRef;
    private ImageView IVprofileImage;

    public Map(Context context) {
        this.context = context;
    }

    public void setUpMarkerClickListener(GoogleMap mMap, final Location location1) {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(final Marker marker) {
                Drop drop = new Drop();
                final Vote vote = new Vote();
                DistanceCalculator calc = new DistanceCalculator();
                LatLng latLng = new LatLng(location1.getLatitude(), location1.getLongitude());

//              calculating distance between pins
                if (calc.distanceBetweenDropsInMetres(marker.getPosition(), latLng) > 1000) {
                    //load dialog box
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_drop_out_of_range);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // set dialog box fields
                    final TextView TVvotes = dialog.findViewById(R.id.TVvotes);
                    vote.calculateVotesTotal(marker.getTitle(), marker.getSnippet(), TVvotes);

                    dialog.show();
                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialogue_view_drop);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    //find text view on dialog
                    TextView dropDialogTitle = dialog.findViewById(R.id.TVviewDialogTitle);
                    TextView dropUsername = dialog.findViewById(R.id.TVusername);
                    final TextView TVvotes = dialog.findViewById(R.id.TVvotes);
                    final ImageView IVprofileImage = dialog.findViewById(R.id.IVprofileImage);
                    userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
                    userRef = FirebaseDatabase.getInstance().getReference().child("users").child(marker.getTitle());


                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                StorageReference UserPic = userProfileImageRef.child(marker.getTitle() + ".jpg");
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


                    drop.setDropContent(marker.getTitle(), marker.getSnippet(), dropDialogTitle, dropUsername);
                    vote.calculateVotesTotal(marker.getTitle(), marker.getSnippet(), TVvotes);

                    ImageButton BTNupvote = dialog.findViewById(R.id.BTNupvote);
                    ImageButton BTNdownvote = dialog.findViewById(R.id.BTNdownvote);

                    BTNupvote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vote.makeAVote(1, marker.getTitle(), marker.getSnippet(), TVvotes);
                        }
                    });
                    BTNdownvote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vote.makeAVote(-1, marker.getTitle(), marker.getSnippet(), TVvotes);
                        }
                    });


                    dialog.show();
                }
                    return true;

            }
        });
    }
}
