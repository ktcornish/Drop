package com.steamybeans.drop.views.home_activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Drop;
import com.steamybeans.drop.firebase.User;
import com.steamybeans.drop.views.MyAccount;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private User user;
    private BottomNavigationView BNbottomNavigationView;
    private Button BTNaddDrop;
    private EditText ETaddDrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = new User();

        // Support toolbar in activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        BNbottomNavigationView = (BottomNavigationView) findViewById(R.id.BNbottomNavigationView);
        BNbottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NBdrop:
                        final Dialog dialog = new Dialog(HomeActivity.this);
                        dialog.setContentView(R.layout.dialogue_new_drop);

                        //find button on dialog
                        BTNaddDrop = (Button)dialog.findViewById(R.id.BTNaddDrop);
                        ETaddDrop = (EditText)dialog.findViewById(R.id.ETaddDrop);

                        //onclicklistener for button
                        BTNaddDrop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Drop drop = new Drop();
                                drop.newDrop(ETaddDrop.getText().toString(), user.uid());
                            }
                        });
                        dialog.show();
                        return true;
                }
                return false;
            }
        });
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
        switch(item.getItemId()) {
            case R.id.TBAccount:
                startActivity(new Intent(HomeActivity.this, MyAccount.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
