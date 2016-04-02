package edu.cmu.guuber.view.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;


public class EndService extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button endButton =
                (Button) findViewById(R.id.endButton);
        endButton.setOnClickListener(endButtonClicked);
    }

    View.OnClickListener endButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent endService =
                    new Intent(EndService.this, FindPassenger.class);

            startActivity(endService);
        }
    };
}
