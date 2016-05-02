package guuber.cmu.edu.activities.common;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.dbLayout.TransactionDBHelper;
import guuber.cmu.edu.entities.User;
import guuber.cmu.edu.exception.SignUpException;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.ClientMessageKind;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.messageConst.ServerMessageKind;
import guuber.cmu.edu.service.GuuberService;
import android.util.Log;

public class CommonSignUpActivity extends AppCompatActivity {


    public static User userinfo = new User();

    // password must contain 8 to 20 characters
    // it must contain at least one uppercase, one lowercase, one digit,
    // one special character (@#$%!)
    // reference: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    private static final String PASSWORD_RESTRICT = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Context context;

    /* the information provided by user */
    private String username = "";
    private String password = "";
    private String userType = "";
    private String email = "";
    private String gender = "";
    private String carId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_sign_up);
        this.context = this;
        TransactionDBHelper transactionDBHelper =  new TransactionDBHelper(this);
        SQLiteDatabase sqLiteDatabase = transactionDBHelper.getWritableDatabase();
        transactionDBHelper.onCreate(sqLiteDatabase);
    }

    //sign up
    public void signUp(View view) {
        try {
            EditText userNameEditText = (EditText) this.findViewById(R.id.sign_up_username_editText);
            username = userNameEditText.getText().toString();
            if (username == null || username.length() < 6) {
                throw new SignUpException(1);
            }
            EditText passwordEditText = (EditText) this.findViewById(R.id.sign_up_password_editText);
            password = passwordEditText.getText().toString();
            EditText rePasswordEditText = (EditText) this.findViewById(R.id.sign_up_retype_password_editText);
            String rePassword = rePasswordEditText.getText().toString();
            if (password == null || rePassword == null || !password.equals(rePassword)) {
                throw new SignUpException(2);
            }
            if (!password.matches(PASSWORD_RESTRICT)) {
                throw new SignUpException(3);
            }
            Spinner userTypeSpinner = (Spinner) this.findViewById(R.id.sign_up_user_type_spinner);
            userType = (String) userTypeSpinner.getSelectedItem().toString();
            if (userType == null) {
                throw new SignUpException(4);
            }
            Spinner genderTypeSpinner = (Spinner) this.findViewById(R.id.sign_up_gender_spinner);
            gender = (String) genderTypeSpinner.getSelectedItem();
            if (gender == null) {
                throw new SignUpException(5);

            }
            EditText emailEditText = (EditText) this.findViewById(R.id.sign_up_email_editText);
            email = emailEditText.getText().toString();
            if (email == null || !email.matches(EMAIL_PATTERN)) {
                throw new SignUpException(6);

            }
            EditText carIdEditText = (EditText) this.findViewById(R.id.sign_up_car_id_editText);
            carId = carIdEditText.getText().toString();
            if (userType.equals("Driver") && (carId == null || carId.length() < 6)) {
                throw new SignUpException(7);

            }
        } catch (SignUpException e) {
            e.alert(this);
            return;
        }
        User user = new User(
                username,
                password,
                userType,
                email,
                gender,
                carId
        );
        ResultReceiver resultReceiver = new SignUpResultReceiver(null);
        Intent intent = new Intent(this, GuuberService.class);
        intent.putExtra("operation", Operation.SENDMESSAGE);
        intent.putExtra("message", ServerMessageKind.SIGNUP + ":" + user.toMessage());
        intent.putExtra("receiver", resultReceiver);
        intent.putExtra("activityName", ActivityNames.COMMONSIGNUPACTIVITY);
        startService(intent);
    }

    /**
     * Put user's information into intent
     * @param  intent   intent used to store information
     * */
    private void putInfoIntoIntent(Intent intent) {
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("userType", userType);
        intent.putExtra("email", email);
        intent.putExtra("gender", gender);
        intent.putExtra("carId", carId);
        userinfo.setUsername(username);
        userinfo.setPassword(password);
        userinfo.setUserType(userType);
        userinfo.setEmail(email);
        userinfo.setGender(gender);
        userinfo.setCarId(carId);
        Log.d("commonuser",userinfo.getEmail()+userinfo.getPassword());
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

    @SuppressLint("ParcelCreator")
    public class SignUpResultReceiver extends ResultReceiver {

        public SignUpResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String response = resultData.getString("response");
            if(response.equals(ClientMessageKind.SIGNUPOK)) {
                Intent intent = null;
                if(userType.equals("Driver")) {
                    intent = new Intent(context, FindPassengerActivity.class);
                } else {
                    intent = new Intent(context, FindDriverActivity.class);
                }
                putInfoIntoIntent(intent);
                startActivity(intent);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            throw new SignUpException(8);
                        } catch (SignUpException e) {
                            e.alert(CommonSignUpActivity.this);
                        }
                    }
                });
                return;
            }
        }
    }
}
