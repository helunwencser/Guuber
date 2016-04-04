package edu.cmu.guuber.view.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.cmu.guuber.guuber.R;

public class UpdateProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverupdate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button cancelButton =
                (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);

        Button saveButton =
                (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonClicked);
    }

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goBack =
                    new Intent(UpdateProfile.this, FindPassenger.class);

            startActivity(goBack);
        }
    };

    View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // get user inputs
            EditText userNameEditText = (EditText)findViewById(R.id.userName);
            String userName = userNameEditText.getText().toString();
            EditText passwordEditText = (EditText)findViewById(R.id.password);
            String password = passwordEditText.getText().toString();
            EditText retypePasswordEditText = (EditText)findViewById(R.id.retypePassword);
            String retypePassword = retypePasswordEditText.getText().toString();
            EditText emailEditText = (EditText)findViewById(R.id.email);
            String email = emailEditText.getText().toString();
            EditText carIDEditText = (EditText)findViewById(R.id.carID);
            String carID = carIDEditText.getText().toString();
            Spinner genderSpinner = (Spinner)findViewById(R.id.gender);
            String gender = (String) genderSpinner.getSelectedItem();

            if(!password.equals(retypePassword)) { // must match
                return;
            }

            Intent save =
                    new Intent(UpdateProfile.this, FindPassenger.class);
            save.putExtra("userName", userName);
            save.putExtra("password", password);
            save.putExtra("gender", gender);
            save.putExtra("email", email);
            save.putExtra("carID", carID);
            startActivity(save);
        }
    };
}
