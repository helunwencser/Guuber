package guuber.cmu.edu.activities.common;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.entities.User;

public class CommonSignUpActivity extends AppCompatActivity {

    // password must contain 8 to 20 characters
    // it must contain at least one uppercase, one lowercase, one digit,
    // one special character (@#$%!)
    // reference: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    private static final String PASSWORD_RESTRICT = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_sign_up);

    }

    //sign up
    public void signUp(View view) {
        EditText userNameEditText = (EditText)this.findViewById(R.id.sign_up_username_editText);
        String username = userNameEditText.getText().toString();
        if(username == null || username.length() < 6) {
            pop("Invalid user name", "User name must have at least 6 characters", "Back");
        }
        EditText passwordEditText = (EditText)this.findViewById(R.id.sign_up_password_editText);
        String password = passwordEditText.getText().toString();
        EditText rePasswordEditText = (EditText)this.findViewById(R.id.sign_up_retype_password_editText);
        String rePassword = rePasswordEditText.getText().toString();
        if(password == null || rePassword == null || !password.equals(rePassword)) {
            pop("Invalide password", "Password and retyped password must be same", "Back");
        }
        if(!password.matches(PASSWORD_RESTRICT)) {
            pop(
                    "Invalid password",
                    "password must contain 8 to 20 characters," +
                    "it must contain at least one uppercase, one lowercase, one digit," +
                    "one special character (@#$%!)",
                    "Back"
            );
        }
        Spinner userTypeSpinner = (Spinner)this.findViewById(R.id.sign_up_user_type_spinner);
        String userType = (String)userTypeSpinner.getSelectedItem();
        if(userType == null) {
            pop("No user type selected", "Please select your user type", "Back");
        }
        Spinner genderTypeSpinner = (Spinner)this.findViewById(R.id.sign_up_gender_spinner);
        String gender = (String)genderTypeSpinner.getSelectedItem();
        if(gender == null) {
            pop("No gender selected", "Please select your gender", "Back");
        }
        EditText emailEditText = (EditText)this.findViewById(R.id.sign_up_email_editText);
        String email = emailEditText.getText().toString();
        if(email == null || email.matches(EMAIL_PATTERN)) {
            pop("Invalid email", "Please input valid email address", "Back");
        }
        EditText carIdEditText = (EditText)this.findViewById(R.id.sign_up_car_id_editText);
        String carId = carIdEditText.getText().toString();
        if(carId == null || carId.length() < 6) {
            pop("Invalid carId", "Please input valid carId", "Back");
        }
        User user = new User(
                username,
                password,
                userType,
                email,
                gender,
                carId
        );
        if(!validateWithServer(user)) {
            pop("Invalid information", "Please input valid information", "Back");
        }
        Intent intent;
        if(userType.equals("Driver")) {
            intent = new Intent(this, FindPassengerActivity.class);
        } else {
            intent = new Intent(this, FindDriverActivity.class);
        }
        putInfoIntoIntent(intent, user);
        this.startActivity(intent);
    }

    /**
     * Put user's information into intent
     * @param  intent   intent used to store information
     *
     * @param user  user's information
     * */
    private void putInfoIntoIntent(Intent intent, User user) {
        intent.putExtra("username", user.getUsername());
        intent.putExtra("password", user.getPassWord());
        intent.putExtra("userType", user.getUserType());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("gender", user.getGender());
        if(user.getCarId() == null) {
            intent.putExtra("carId", user.getCarId());
        } else {
            intent.putExtra("carId", user.getCarId());
        }
    }

    //TODO add logical for validating data with server
    /**
     * Validate user's information with server
     * @param user  user information
     *
     * @return return true if user's information is valid;
     *          otherwise, return false
     * */
    private boolean validateWithServer(User user) {
        return true;
    }


    /**
     * pop up message when input incorrect information
     * @param title the title of pop up
     *
     * @param message message to show
     *
     * @param button operation
     * */
    private void pop(String title, String message, String button) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(CommonSignUpActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, null);
        builder.show();
    }
}
