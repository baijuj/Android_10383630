/*
Student Number: 10383630
Student Name:   Baiju John
*/

package com.DBS.MobileApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("iBike");

        //For button (Search Station) click event.
        Button button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Call Actityvity to show citylist.
                Intent mainIntent = new Intent(Home.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
