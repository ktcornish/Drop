package com.steamybeans.drop.views;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.Drop;
import com.steamybeans.drop.firebase.Firebasemarker;
import com.steamybeans.drop.firebase.User;
import com.steamybeans.drop.firebase.Vote;
import com.steamybeans.drop.map.Map;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private User user;
    private Authentication authentication;
    private BottomNavigationView BNbottomNavigationView;
    private TextView TVdialogTitle;
    private TextView TVviewDialogTitle;
    private Button BTNaddDrop;
    private EditText ETaddDrop;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int request_User_Location_Code = 99;
    public LatLng currentLocation;
    public double currentLatitude;
    public double currentLongitude;
    private ChildEventListener childEventListener;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String userId;
    private Vote vote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.homemap);
        mapFragment.getMapAsync(this);

        user = new User();
        authentication = new Authentication(this);

        // Support toolbar in activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        //check if user account is still active
        authentication.checkAccountIsActive();

        BNbottomNavigationView = (BottomNavigationView) findViewById(R.id.BNbottomNavigationView);
        BNbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //check if user account is still active
                authentication.checkAccountIsActive();

                switch (item.getItemId()) {
                    case R.id.NBdrop:
                        final Dialog dialog = new Dialog(HomeActivity.this);
                        dialog.setContentView(R.layout.dialogue_new_drop);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        //find button on dialog
                        TVdialogTitle = (TextView) dialog.findViewById(R.id.TVdialogTitle);
                        BTNaddDrop = (Button) dialog.findViewById(R.id.BTNaddDrop);
                        ETaddDrop = (EditText) dialog.findViewById(R.id.ETaddDrop);

                        //onclicklistener for button
                        BTNaddDrop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ETaddDrop.getText().toString().trim().length() > 0) {
                                    Drop drop = new Drop();
                                    drop.newDrop(ETaddDrop.getText().toString(), user.getUid(), currentLatitude, currentLongitude);
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(HomeActivity.this, "No Drop entered!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        dialog.show();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        //check if user account is still active
        authentication.checkAccountIsActive();
    }

    // Populate toolbar with buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_nav_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Support onClick actions on options in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.TBAccount:
                startActivity(new Intent(HomeActivity.this, MyAccount.class));
                overridePendingTransition(R.anim.slide_up_from_bottom, R.anim.slide_down_from_top);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiclient();
            mMap.setMyLocationEnabled(true);
            mMap.setPadding(0,200,0,200);

            addMarkersToMap(googleMap);
        }
    }

    private void addMarkersToMap(final GoogleMap googleMap) {
        vote = new Vote();
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userId = snapshot.getKey();

                    FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("posts")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Firebasemarker firebaseMarker = snapshot.getValue(Firebasemarker.class);
                                            String user = dataSnapshot.getRef().getParent().getKey();
                                            LatLng location = new LatLng(firebaseMarker.getLatitude(), firebaseMarker.getLongitude());
                                            googleMap.addMarker(new MarkerOptions().position(location).title(user).snippet(snapshot.getKey())
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                                            Map map = new Map(HomeActivity.this);
                                        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                            Location location1 = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                                            map.setUpMarkerClickListener(mMap, location1);
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean checkUserLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_User_Location_Code);
            }
            return false;
        } else {

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case request_User_Location_Code:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(googleApiClient == null) {
                            buildGoogleApiclient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiclient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

//        currentUserLocationMarker = mMap.addMarker(markerOptions);

//        this moves the map back to the original zoom and place
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        //add lat and Lon to variable for use with drops
        currentLocation = latLng;
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        //stop updating location if location already found
        addMarkersToMap(mMap);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
