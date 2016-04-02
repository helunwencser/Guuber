package edu.cmu.guuber.view.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import edu.cmu.guuber.guuber.R;
import edu.cmu.guuber.view.common.WelcomeActivity;


public class findDriver extends AppCompatActivity {
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
            Intent findDriver = new Intent(findDriver.this, startService.class);
            startActivity(findDriver);
        }
    };

    public void updateProfile() {
        Intent updateprofile = new Intent(findDriver.this, updateProfile.class);
        startActivity(updateprofile);
    }

    public void viewHistory() {
        Intent viewhistory = new Intent(findDriver.this, viewHistory.class);
        startActivity(viewhistory);
    }

    public void logOut() {
        Intent logout = new Intent(findDriver.this, WelcomeActivity.class);
        startActivity(logout);
    }
}
