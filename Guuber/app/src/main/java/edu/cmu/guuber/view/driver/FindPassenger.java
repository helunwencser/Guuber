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


public class FindPassenger extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassenger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner dropSpinner = (Spinner)findViewById(R.id.drop_page);

        Button findButton =
                (Button) findViewById(R.id.findButton);
        findButton.setOnClickListener(findButtonClicked);
    }

    View.OnClickListener findButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent findPassenger =
                new Intent(FindPassenger.this, StartService.class);

        startActivity(findPassenger);
        }
    };

    // the following three methods are called based on user
    // selection in the drop menu (Spinner)
    // a click listener will be added to the Spinner during implementation phase
    public void updateProfile() {
        Intent update =
                new Intent(FindPassenger.this, UpdateProfile.class);

        startActivity(update);
    }

    public void viewHistory() {
        Intent history =
                new Intent(FindPassenger.this, ViewHistory.class);

        startActivity(history);
    }

    public void logOut() {
        Intent logout =
                new Intent(FindPassenger.this, WelcomeActivity.class);

        startActivity(logout);
    }
}
