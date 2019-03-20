package com.steamybeans.drop.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.steamybeans.drop.R;
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.User;

public class MyAccount extends AppCompatActivity {

    private User user;
    private TextView TVEmail;
    private Button BTNlogOut;
    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        // Support toolbar in activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_myaccount_top);
        setSupportActionBar(toolbar);

        user = new User();
        authentication = new Authentication(this);

        //check if user account is still active
        authentication.checkAccountIsActive();

        TVEmail = (TextView) findViewById(R.id.TVEmail);
        TVEmail.setText(user.email());

        BTNlogOut = (Button) findViewById(R.id.BTNlogOut);

        BTNlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.signOut();
                startActivity(new Intent(MyAccount.this, LoginPage.class));
                Toast.makeText(MyAccount.this, "User successfully signed out", Toast.LENGTH_LONG).show();
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
