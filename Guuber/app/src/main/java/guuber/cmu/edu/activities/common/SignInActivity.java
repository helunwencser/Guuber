package guuber.cmu.edu.activities.common;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;


/**
 * Created by wangziming on 4/9/16.
 */
public class SignInActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_sign_in);
        userNameEditText = (EditText) findViewById(R.id.SignIn_userName);
        passwordEditText = (EditText) findViewById(R.id.SignIn_password);

    }
}

