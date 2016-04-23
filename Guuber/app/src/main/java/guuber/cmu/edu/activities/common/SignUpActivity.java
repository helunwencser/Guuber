package guuber.cmu.edu.activities.common;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.entities.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;
    private Spinner userTypeSpinner;
    private Spinner genderSpinner;
    private EditText emailEditText;
    private EditText carIDEditText;

    private SignUpIntent intentWrapper;

    // password must contain 8 to 20 characters
    // it must contain at least one uppercase, one lowercase, one digit,
    // one special character (@#$%!)
    // reference: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    private static final String PASSWORD_RESTRICT =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_sign_up);

        userNameEditText = (EditText) findViewById(R.id.SignUp_userName);
        passwordEditText = (EditText) findViewById(R.id.SignUp_password);
        retypePasswordEditText = (EditText) findViewById(R.id.SignUp_retypePassword);
        userTypeSpinner = (Spinner) findViewById(R.id.SignUp_userType);
        genderSpinner = (Spinner) findViewById(R.id.SignUp_gender);
        emailEditText = (EditText) findViewById(R.id.SignUp_email);
        carIDEditText = (EditText) findViewById(R.id.SignUp_carID);

        Button signUpButton =
                (Button) findViewById(R.id.SignUp_signUpButton);
        signUpButton.setOnClickListener(signUpButtonClicked);
    }

    View.OnClickListener signUpButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validateCompleteness()) {
                if (validatePasswordMatch()) {
                    if (validatePasswordComplexity()) {
                        if (sendUser()) {
                            logIn();
                            pop("Congratulations", "You have successfully signed up!", "Continue");
                        } else {
                            pop("Sign-Up Error", "UserName already exist", "Back");
                        }
                    } else {
                        pop("Sign-Up Error", "Password doesn't meet requirement", "Back");
                    }
                } else {
                    pop("Sign-Up Error", "Password and retype don't match", "Back");
                }
            } else {
                pop("Sign-Up Error", "Information is incomplete", "Back");
            }
        }
    };

    private boolean validateCompleteness() {
        if (userNameEditText.getText().length() == 0 || passwordEditText.getText().length() == 0
                || retypePasswordEditText.getText().length() == 0 || emailEditText.getText().length() == 0
                || userTypeSpinner.getSelectedItemPosition() < 0 || genderSpinner.getSelectedItemPosition() < 0) {
            return false;
        }
        String userType = userTypeSpinner.getSelectedItem().toString();
        if (userType.equals(getResources().getString(R.string.type_driver))) {
            if (carIDEditText.getText().length() == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean validatePasswordMatch() {
        String password = passwordEditText.getText().toString();
        String retypePassword = retypePasswordEditText.getText().toString();
        if (!password.equals(retypePassword)) {
            return false;
        }
        return true;
    }

    private boolean validatePasswordComplexity() {
        String password = passwordEditText.getText().toString();

        Pattern pattern = Pattern.compile(password);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private boolean sendUser() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String carID = "null";
        String userType = userTypeSpinner.getSelectedItem().toString();
        if (userType.equals(getResources().getString(R.string.type_driver))) {
            carID = carIDEditText.getText().toString();
        }
        String gender = genderSpinner.getSelectedItem().toString();

        User user = new User(userName, password, userType, email, gender, carID);

        // send user object with socket

        return true;
    }

    public void logIn() {
        String userName = userNameEditText.getText().toString();
        String userType = userTypeSpinner.getSelectedItem().toString();
        int switches = 0;
        if (userType.equals(getResources().getString(R.string.type_passenger))) {
            switches = 1;
        }
        intentWrapper = new SignUpIntent(switches, SignUpActivity.this, FindDriverActivity.class,
                FindPassengerActivity.class, userName);
        this.startActivity(intentWrapper.getIntent());
    }

    private void pop(String title, String message, String button) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(SignUpActivity.this);
        // set dialog title & message, and provide Button to dismiss
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, null);
        builder.show(); // display the Dialog
    }
}
