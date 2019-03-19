package com.steamybeans.drop.views.home_activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.steamybeans.drop.R;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.homemap);
        mapFragment.getMapAsync(this);
        // Support toolbar in activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // Loc to be reassignable - final only in test context
        final LatLng makers = new LatLng(51.524579,-0.098996);
        mMap.addMarker(new MarkerOptions().position(makers).title("Marker in Makers"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(makers));

        //Toastloc
        FloatingActionButton button = findViewById(R.id.fabTempToastLoc);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "" + makers.toString() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
