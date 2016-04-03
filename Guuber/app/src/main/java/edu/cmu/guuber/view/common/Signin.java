package edu.cmu.guuber.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import edu.cmu.guuber.guuber.R;
import edu.cmu.guuber.view.driver.FindPassenger;
import edu.cmu.guuber.view.passenger.FindDriver;

public class Signin extends AppCompatActivity {

    public void signin(View view) {
        EditText userNameEditText = (EditText)findViewById(R.id.userNameSignIn);
        String userName = userNameEditText.getText().toString();
        EditText passwordEditText = (EditText)findViewById(R.id.passwordSignIn);
        String password = passwordEditText.getText().toString();
        /**
         * validate before start home page
         * */
        /**
         * get user type from backend
         * */
        String userType = "";
        Intent intent = null;
        if(userType == "user") {
            intent = new Intent(this, FindDriver.class);
        } else {
            intent = new Intent(this, FindPassenger.class);
        }
        intent.putExtra("userName", userName);
        intent.putExtra("password", password);
        this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
