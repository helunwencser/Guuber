package guuber.cmu.edu.activities.passenger;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.entities.User;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.ClientMessageKind;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.messageConst.ServerMessageKind;
import guuber.cmu.edu.service.GuuberService;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by wangziming on 4/9/16.
 */
public class UpdateProfileActivity extends AppCompatActivity {

    private TextView userNameEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;
    private Spinner genderSpinner;
    private EditText emailEditText;
    private Context context;

    String username;
    String userType;
    String email;
    String gender;
    String carId = "";
    String password;
    String Repassword;

    // password must contain 8 to 20 characters
    // it must contain at least one uppercase, one lowercase, one digit,
    // one special character (@#$%!)
    // reference: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    private static final String PASSWORD_RESTRICT =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_update_profile);
        this.context = this;

        //定位
        userNameEditText = (TextView)findViewById(R.id.passenger_update_userName);
        passwordEditText = (EditText) findViewById(R.id.passenger_update_password);
        retypePasswordEditText = (EditText) findViewById(R.id.passenger_update_retypePassword);
        genderSpinner = (Spinner) findViewById(R.id.passenger_update_gender);
        emailEditText = (EditText) findViewById(R.id.passenger_update_email);


        //接住前面的传参
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        userType = intent.getStringExtra("userType");
        email = intent.getStringExtra("email");
        gender = intent.getStringExtra("gender");
        passwordEditText.setText(password);
        retypePasswordEditText.setText(password);
        // carId = intent.getStringExtra("carId");

        Log.d("UserInfoPUPDATE",username+"\t"+password+"\t"+userType+"\t"+email+"\t"+gender+"\t"+carId);
        //设置显示
        userNameEditText.setText(username);
        emailEditText.setText(email);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        if (!gender.equals(null)) {
            int spinnerPosition = adapter.getPosition(gender);
            genderSpinner.setSelection(spinnerPosition);
        }

        Log.d("UserInfoPUPDATEPPP",username+"\t"+password+"\t"+userType+"\t"+email+"\t"+gender+"\t"+carId);


        Button cancelButton =
                (Button) findViewById(R.id.passenger_update_cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);

        //新的取值
        username = userNameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        Repassword = retypePasswordEditText.getText().toString();
        email = emailEditText.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();

    }


    /////////////////////////////////////////
    private boolean validatePasswordMatch(String password,String retypePassword) {
        if (!password.equals(retypePassword)) {
            return false;
        }
        return true;
    }

    private boolean validatePasswordComplexity(String password) {

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
                || genderSpinner.getSelectedItemPosition() < 0) {
            return false;
        }
        return true;
    }
    /////////////////////////////////////////


    ///////////////
    public void saveP(View view) {
        username = userNameEditText.getText().toString();
        if(username == null || username.length() < 6) {
            pop("Invalid user name", "User name must have at least 6 characters", "Back");
        }
        EditText passwordEditText = (EditText)this.findViewById(R.id.passenger_update_password);
        password = passwordEditText.getText().toString();

        EditText passwordEditTextre = (EditText)this.findViewById(R.id.passenger_update_retypePassword);
        Repassword = passwordEditTextre.getText().toString();


        if(password == null || password.length() <= 0 || !password.matches(PASSWORD_RESTRICT)) {
            pop(
                    "Invalid password",
                    "password must contain 8 to 20 characters," +
                            "it must contain at least one uppercase, one lowercase, one digit," +
                            "one special character (@#$%!)",
                    "Back"
            );
            return;
        }

        if (validateCompleteness()) {
            if (validatePasswordMatch(password,Repassword)) {
                if (validatePasswordComplexity(password)) {
                    UpdateDriverProfileReceiver updateDriverProfileReceiver = new UpdateDriverProfileReceiver(null);
                    Intent intent = new Intent(this, GuuberService.class);
                    intent.putExtra("operation", Operation.SENDMESSAGE);
                    intent.putExtra("message", ServerMessageKind.UPDATEPASSENGERPROFILE + ":"
                            + username + ":"
                            + password + ":"
                            + userType + ":"
                            + email + ":"
                            + gender);
                    intent.putExtra("receiver", updateDriverProfileReceiver);
                    intent.putExtra("activityName", ActivityNames.PASSENGERUPDATEPROFILEACTIVITY);
                    startService(intent);
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



    //////////////////

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UpdateProfileActivity.this,FindDriverActivity.class);
            startActivity(intent);
        }
    };

    @SuppressLint("ParcelCreator")
    public class UpdateDriverProfileReceiver extends ResultReceiver {

        public UpdateDriverProfileReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String response = resultData.getString("response");
            if(response.equals(ClientMessageKind.UPDATEPASSENGERPROFILEOKAY)) {
               /* Intent intent = null;
                if(userType.equals("Passenger")) {
                    putInfoIntoIntent(intent);
                    startActivity(intent);
                }*/
                Log.d("updateOkayP","uuuuu");
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pop(
                                "Updated failed",
                                "Please Try Again",
                                "Save"
                        );
                    }
                });
                return;
            }
        }
    }

    private void putInfoIntoIntent(Intent intent) {
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("userType", userType);
        intent.putExtra("email", email);
        intent.putExtra("gender", gender);
    }


}
