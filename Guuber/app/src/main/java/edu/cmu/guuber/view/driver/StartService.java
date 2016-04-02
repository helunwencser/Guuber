package edu.cmu.guuber.view.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;

public class StartService extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverstart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button startButton =
                (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(startButtonClicked);

        Button cancelButton =
                (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);
    }


    View.OnClickListener startButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startService =
                    new Intent(StartService.this, EndService.class);

            startActivity(startService);
        }
    };

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goBack =
                    new Intent(StartService.this, FindPassenger.class);

            startActivity(goBack);
        }
    };
}
