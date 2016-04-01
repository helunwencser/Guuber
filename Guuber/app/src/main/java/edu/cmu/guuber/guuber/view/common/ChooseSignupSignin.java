package edu.cmu.guuber.guuber.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;

public class ChooseSignupSignin extends AppCompatActivity {

    public void chooseSignupSignin(View view) {
        Intent intent = null;
        if(view == findViewById(R.id.signInButton)) {
            intent = new Intent(this, Signin.class);
        } else {
            intent = new Intent(this, Signup.class);
        }
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_signup_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button signInButton = (Button)this.findViewById(R.id.signInButton);
        Button signUpButton = (Button)this.findViewById(R.id.signUpButton);
    }

}
