package edu.cmu.guuber.view.passenger;

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
        setContentView(R.layout.activity_passengerstart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cancelButton =
                (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(cancelButtonClicked);

        Button sendButton = (Button)findViewById(R.id.send);
        sendButton.setOnClickListener(sendButtonClicked);
    }


    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goFindDriverPage = new Intent(StartService.this, FindDriver.class);
            startActivity(goFindDriverPage);
        }
    };

    View.OnClickListener sendButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            EditText input = (EditText)findViewById(R.id.input);
            String message = input.getText().toString();
            TextView display = (TextView)findViewById(R.id.textView);
            display.append(message);
            input.setText("");
        }
    }
}
