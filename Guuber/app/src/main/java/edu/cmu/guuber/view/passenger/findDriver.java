package edu.cmu.guuber.view.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import edu.cmu.guuber.guuber.R;
import edu.cmu.guuber.view.common.WelcomeActivity;


public class FindDriver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finddriver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button findButton =  (Button) findViewById(R.id.findDriverButton);
        findButton.setOnClickListener(findButtonClicked);
    }

    View.OnClickListener findButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent findDriver = new Intent(FindDriver.this, StartService.class);
            startActivity(findDriver);
        }
    };

    public void updateProfile() {
        Intent updateprofile = new Intent(FindDriver.this, UpdateProfile.class);
        startActivity(updateprofile);
    }

    public void viewHistory() {
        Intent viewhistory = new Intent(FindDriver.this, ViewHistory.class);
        startActivity(viewhistory);
    }

    public void logOut() {
        Intent logout = new Intent(FindDriver.this, WelcomeActivity.class);
        startActivity(logout);
    }
}
