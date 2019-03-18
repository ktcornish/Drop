package com.steamybeans.drop.views.home_activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.steamybeans.drop.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}
