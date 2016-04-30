package guuber.cmu.edu.activities.driver;

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
import guuber.cmu.edu.entities.User;
import android.content.Intent;

/**
 * Created by wangziming on 4/9/16.
 */
public class UpdateProfileActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;
    private Spinner genderSpinner;
    private EditText emailEditText;
    private EditText carIDEditText;


    // password must contain 8 to 20 characters
    // it must contain at least one uppercase, one lowercase, one digit,
    // one special character (@#$%!)
    // reference: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    private static final String PASSWORD_RESTRICT =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_update_profile);

        userNameEditText = (EditText) findViewById(R.id.driver_update_userName);
        passwordEditText = (EditText) findViewById(R.id.driver_update_password);
        retypePasswordEditText = (EditText) findViewById(R.id.driver_update_retypePassword);
        genderSpinner = (Spinner) findViewById(R.id.driver_update_gender);
        emailEditText = (EditText) findViewById(R.id.driver_update_email);
        carIDEditText = (EditText) findViewById(R.id.driver_update_carID);

        Button saveButton =
                (Button) findViewById(R.id.driver_update_saveButton);
        saveButton.setOnClickListener(saveButtonClicked);

        Button cancelButton =
                (Button) findViewById(R.id.driver_update_cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);
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

    private void pop(String title, String message, String button) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(UpdateProfileActivity.this);
        // set dialog title & message, and provide Button to dismiss
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, null);
        builder.show(); // display the Dialog
    }

    private boolean validateCompleteness() {
        if (userNameEditText.getText().length() == 0 || passwordEditText.getText().length() == 0
                || retypePasswordEditText.getText().length() == 0 || emailEditText.getText().length() == 0
                || carIDEditText.getText().length() == 0 || genderSpinner.getSelectedItemPosition() < 0) {
            return false;
        }
        return true;
    }

    private boolean sendUser() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String carID = carIDEditText.getText().toString();
        //String userType = getResources().getString(R.string.type_driver);
        String userType = "Driver";
        String gender = genderSpinner.getSelectedItem().toString();

        User user = new User(userName, password, userType, email, gender, carID);

        // send user object with socket

        return true;
    }

    View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validateCompleteness()) {
                if (validatePasswordMatch()) {
                    if (validatePasswordComplexity()) {
                        if (sendUser()) {
                            pop("Congratulations", "You have successfully updated profile!", "Continue");
                        } else {
                            pop("Update Error", "UserName already exist", "Back");
                        }
                    } else {
                        pop("Update Error", "Password doesn't meet requirement", "Back");
                    }
                } else {
                    pop("Update Error", "Password and retype don't match", "Back");
                }
            } else {
                pop("Update Error", "Information is incomplete", "Back");
            }
        }
    };


    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UpdateProfileActivity.this,FindPassengerActivity.class);
            startActivity(intent);
        }
    };


}
