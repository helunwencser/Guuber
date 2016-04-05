package edu.cmu.guuber.view.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        Button sendButton =
                (Button) findViewById(R.id.sendButton);
        cancelButton.setOnClickListener(sendButtonClicked);
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

    View.OnClickListener sendButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText inputEditText = (EditText)findViewById(R.id.input);
            String input = inputEditText.getText().toString();

            TextView display = (TextView) findViewById(R.id.textView);
            display.append(input); // append message to the chat screen

            inputEditText.setText(""); // empty the input box
        }
    };
}
