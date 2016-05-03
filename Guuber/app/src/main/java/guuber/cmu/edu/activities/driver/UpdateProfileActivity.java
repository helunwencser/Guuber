package guuber.cmu.edu.activities.driver;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.common.CommonSignInActivity;
import guuber.cmu.edu.activities.common.CommonSignUpActivity;
import guuber.cmu.edu.exception.UpdateException;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.ClientMessageKind;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.messageConst.ServerMessageKind;
import guuber.cmu.edu.ws.remote.GuuberService;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wangziming on 4/9/16.
 */
public class UpdateProfileActivity extends AppCompatActivity {

    private TextView userNameEditText;
    private EditText passwordEditText;
    private EditText retypePasswordEditText;
    private Spinner genderSpinner;
    private EditText emailEditText;
    private EditText carIDEditText;
    private Context context;

    String username;
    String userType;
    String email;
    String gender;
    String carId;
    String password;
    String Repassword;

    // password must contain 8 to 20 characters
    // it must contain at least one uppercase, one lowercase, one digit,
    // one special character (@#$%!)
    // reference: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    private static final String PASSWORD_RESTRICT =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_update_profile);
        this.context = this;


        userNameEditText = (TextView) findViewById(R.id.driver_update_userName);
        passwordEditText = (EditText) findViewById(R.id.driver_update_password);
        retypePasswordEditText = (EditText) findViewById(R.id.driver_update_retypePassword);
        genderSpinner = (Spinner) findViewById(R.id.driver_update_gender);
        emailEditText = (EditText) findViewById(R.id.driver_update_email);
        carIDEditText = (EditText) findViewById(R.id.driver_update_carID);


         Intent intent = getIntent();
         username = intent.getStringExtra("username");
         password = intent.getStringExtra("password");
         userType = intent.getStringExtra("userType");
         email = intent.getStringExtra("email");
         gender = intent.getStringExtra("gender");
         carId = intent.getStringExtra("carId");

        if(!CommonSignInActivity.userinfo.getUsername().equals("")){
            username = CommonSignInActivity.userinfo.getUsername();
            password = CommonSignInActivity.userinfo.getPassword();
            userType = CommonSignInActivity.userinfo.getUserType();
            email = CommonSignInActivity.userinfo.getEmail();
            gender = CommonSignInActivity.userinfo.getGender();
            carId = CommonSignInActivity.userinfo.getCarId();
        }else{
            username = CommonSignUpActivity.userinfo.getUsername();
            password = CommonSignUpActivity.userinfo.getPassword();
            userType = CommonSignUpActivity.userinfo.getUserType();
            email = CommonSignUpActivity.userinfo.getEmail();
            gender = CommonSignUpActivity.userinfo.getGender();
            carId = CommonSignUpActivity.userinfo.getCarId();

        }


         userNameEditText.setText(username);
         emailEditText.setText(email);
         carIDEditText.setText(carId);
         passwordEditText.setText(password);
         retypePasswordEditText.setText(password);
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_type, android.R.layout.simple_spinner_item);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         genderSpinner.setAdapter(adapter);
         if (!gender.equals(null)) {
            int spinnerPosition = adapter.getPosition(gender);
            genderSpinner.setSelection(spinnerPosition);
         }




        Button cancelButton =
                (Button) findViewById(R.id.driver_update_cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);




    }


    private boolean validatePasswordMatch(String password,String retypePassword) {
        if (!password.equals(retypePassword)) {
            return false;
        }
        return true;
    }

    public void saveD(View view) {
        try {
            username = userNameEditText.getText().toString();
            if(username == null || username.length() < 6) {
                throw new UpdateException(1);
            }

            password = passwordEditText.getText().toString();
            Repassword = retypePasswordEditText.getText().toString();
            email = emailEditText.getText().toString();
            carId = carIDEditText.getText().toString();
            gender = genderSpinner.getSelectedItem().toString();

            if(password == null || password.length() <= 0 || !password.matches(PASSWORD_RESTRICT)) {
                throw new UpdateException(2);
            }
        } catch (UpdateException e) {
            e.alert(this);
            return;
        }
        if (validatePasswordMatch(password,Repassword)) {
            try {
                if (email == null || !email.matches(EMAIL_PATTERN)) {
                    throw new UpdateException(6);

                }
                if (userType.equals("Driver") && (carId == null || carId.length() < 6)) {
                    throw new UpdateException(7);

                }
            } catch (UpdateException e) {
                e.alert(UpdateProfileActivity.this);
                return;
            }
                UpdateDriverProfileReceiver updateDriverProfileReceiver = new UpdateDriverProfileReceiver(null);
                Intent intent = new Intent(this, GuuberService.class);
                intent.putExtra("operation", Operation.SENDMESSAGE);
                intent.putExtra("message", ServerMessageKind.UPDATEDRIVERPROFILE + ":"
                                                                + username + ":"
                                                                + password +":"
                                                                + userType + ":"
                                                                + email + ":"
                                                                + gender+ ":"
                                                                + carId);
                intent.putExtra("receiver", updateDriverProfileReceiver);
                intent.putExtra("activityName", ActivityNames.DRIVERUPDATEPROFILEACTIVITY);
                startService(intent);
                Toast.makeText(this, "Update Successfully!", Toast.LENGTH_SHORT).show();
                if(!CommonSignInActivity.userinfo.getUsername().equals("")){
                    CommonSignInActivity.userinfo.setPassword(password);
                    CommonSignInActivity.userinfo.setGender(gender);
                    CommonSignInActivity.userinfo.setEmail(email);
                    CommonSignInActivity.userinfo.setCarId(carId);
                }else{
                    CommonSignUpActivity.userinfo.setPassword(password);
                    CommonSignUpActivity.userinfo.setGender(gender);
                    CommonSignUpActivity.userinfo.setEmail(email);
                    CommonSignUpActivity.userinfo.setCarId(carId);
                }
        } else {
            try {
                throw new UpdateException(4);
            } catch (UpdateException e) {
                e.alert(UpdateProfileActivity.this);
            }
        }

    }


    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UpdateProfileActivity.this,FindPassengerActivity.class);
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
            if(response.equals(ClientMessageKind.UPDATEDRIVERPROFILEOKAY)) {

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            throw new UpdateException(3);
                        } catch (UpdateException e) {
                            e.alert(UpdateProfileActivity.this);
                        }
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
        intent.putExtra("carId", carId);
    }


}
