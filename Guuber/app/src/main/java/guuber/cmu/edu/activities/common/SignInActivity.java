package guuber.cmu.edu.activities.common;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.intent.ChooseSignInSignUpIntent;
import guuber.cmu.edu.intent.SignInIntent;


/**
 * Created by wangziming on 4/9/16.
 */
public class SignInActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

    private SignInIntent intentWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_sign_in);
        userNameEditText = (EditText) findViewById(R.id.SignIn_userName);
        passwordEditText = (EditText) findViewById(R.id.SignIn_password);

        Button signInButton =
                (Button) findViewById(R.id.common_signInButton);
        signInButton.setOnClickListener(signInButtonClicked);
    }

    View.OnClickListener signInButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validateCompleteness()) {
                int switches = validateCorrectness();
                if (switches < 2) {
                    String userName = userNameEditText.getText().toString();
                    logIn(switches, userName);
                } else {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SignInActivity.this);
                    // set dialog title & message, and provide Button to dismiss
                    builder.setTitle("Log-In Error");
                    builder.setMessage("Username/password is invalid");
                    builder.setPositiveButton("Back", null);
                    builder.show(); // display the Dialog
                }
            } else {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(SignInActivity.this);
                // set dialog title & message, and provide Button to dismiss
                builder.setTitle("Log-In Error");
                builder.setMessage("Username/password is incomplete");
                builder.setPositiveButton("Back", null);
                builder.show(); // display the Dialog
            }
        }
    };

    private boolean validateCompleteness() {
        if (userNameEditText.getText().length() == 0 || passwordEditText.getText().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    // user socket to communicate with server
    private int validateCorrectness() {
        // 0 for driver, 1 for passenger, 2 for invalid
        return 0;
    }

    private void logIn(int switches, String userName) {
        intentWrapper = new SignInIntent(switches, SignInActivity.this, FindDriverActivity.class,
                FindPassengerActivity.class, userName);
        this.startActivity(intentWrapper.getIntent());
    }
}

