package guuber.cmu.edu.activities.common;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.intent.SignInIntent;


/**
 * Created by wangziming on 4/9/16.
 */
public class SignInActivity extends AppCompatActivity {

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
        SignInIntent signInIntent = null;
        if(userType == "user") {
             signInIntent = new SignInIntent(0,this,FindDriverActivity.class,FindPassengerActivity.class,userName,password);
           // intent = new Intent(this, FindDriverActivity.class);
        } else {
             signInIntent = new SignInIntent(1,this,FindDriverActivity.class,FindPassengerActivity.class,userName,password);

        }
        intent = signInIntent.getIntent();
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

