package com.steamybeans.drop.map;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Drop;
import com.steamybeans.drop.firebase.Vote;

public class Map extends AppCompatActivity {
    private final Context context;

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
                    final TextView TVvotes = dialog.findViewById(R.id.TVvotes);
                    drop.setDropContent(marker.getTitle(), marker.getSnippet(), dropDialogTitle);
                    vote.calculateVotesTotal(marker.getTitle(), marker.getSnippet(), TVvotes);

                    Button BTNupvote = dialog.findViewById(R.id.BTNupvote);
                    Button BTNdownvote = dialog.findViewById(R.id.BTNdownvote);

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
