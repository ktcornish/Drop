package com.steamybeans.drop.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.User;

public class MyAccount extends AppCompatActivity {

    private User user;
    private TextView TVEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        // Support toolbar in activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        user = new User();
        TVEmail = (TextView) findViewById(R.id.TVEmail);
        TVEmail.setText(user.email());
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
