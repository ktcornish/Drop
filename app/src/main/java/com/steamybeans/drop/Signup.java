package com.steamybeans.drop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Signup extends AppCompatActivity {

    private Button BTNcompleteSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        init();
    }

    public void init() {
        BTNcompleteSignUp = (Button)findViewById(R.id.BTNcompleteSignUp);

        BTNcompleteSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, LoginPage.class));
            }
        });
    }
}