package edu.cmu.guuber.guuber.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.cmu.guuber.guuber.R;

public class Signup extends AppCompatActivity {

    public void signup(View view) {
        EditText userNameEditText = (EditText)findViewById(R.id.userName);
        String userName = userNameEditText.getText().toString();
        EditText passwordEditText = (EditText)findViewById(R.id.password);
        String password = passwordEditText.getText().toString();
        EditText rePasswordEditText = (EditText)findViewById(R.id.retypePassword);
        String rePassword = rePasswordEditText.getText().toString();
        if(!password.equals(rePassword)) {
            return;
        }
        Spinner userTypeSpinner = (Spinner)findViewById(R.id.userType);
        String userType = (String)userTypeSpinner.getSelectedItem();
        Spinner genderSpinner = (Spinner)findViewById(R.id.gender);
        String gender = (String)genderSpinner.getSelectedItem();
        EditText emailEditText = (EditText)findViewById(R.id.email);
        String email = emailEditText.getText().toString();
        EditText carIDEditText = (EditText)findViewById(R.id.carID);
        String carID = carIDEditText.getText().toString();

        /**
         * validate before start welcomepage
         * */

        Intent intent = new Intent(this, WelcomePage.class);
        intent.putExtra("userName", userName);
        intent.putExtra("password", password);
        intent.putExtra("userType", userType);
        intent.putExtra("gender", gender);
        intent.putExtra("email", email);
        intent.putExtra("carID", carID);
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
