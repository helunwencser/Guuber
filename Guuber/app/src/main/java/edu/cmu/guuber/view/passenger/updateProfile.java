package edu.cmu.guuber.view.passenger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;

public class UpdateProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverupdate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonClicked);
    }

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoFindDriver = new Intent(UpdateProfile.this, FindDriver.class);
            startActivity(gotoFindDriver);
        }
    };

    View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText userNameEditText = (EditText)findViewById(R.id.userName);
            String userName = userNameEditText.getText().toString();

            EditText passwordEditText = (EditText)findViewById(R.id.password);
            String password = passwordEditText.getText().toString();

            EditText retypePasswordEditText = (EditText)findViewById(R.id.retypePassword);
            String retypePassword = retypePasswordEditText.getText().toString();

            if(!password.equals(retypePassword)) {
                return;
            }

            EditText emailEditText = (EditText)findViewById(R.id.email);
            String email = emailEditText.getText().toString();


            Spinner genderSpinner = (Spinner)findViewById(R.id.gender);
            String gender = (String) genderSpinner.getSelectedItem();



            Intent saveChanged = new Intent(UpdateProfile.this, FindDriver.class);
            saveChanged.putExtra("userName", userName);
            saveChanged.putExtra("password", password);
            saveChanged.putExtra("gender", gender);
            saveChanged.putExtra("email", email);
            startActivity(saveChanged);
        }
    };
}
