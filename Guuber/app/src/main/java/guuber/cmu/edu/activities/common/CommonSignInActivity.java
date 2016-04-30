package guuber.cmu.edu.activities.common;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.MessageKind;
import guuber.cmu.edu.messageConst.MessageReply;
import guuber.cmu.edu.resultCode.ResultCode;
import guuber.cmu.edu.service.GuuberService;

public class CommonSignInActivity extends AppCompatActivity {

    private Context context;

    // password must contain 8 to 20 characters
    // it must contain at least one uppercase, one lowercase, one digit,
    // one special character (@#$%!)
    // reference: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    private static final String PASSWORD_RESTRICT = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{8,20})";

    /* the information of user */
    private String username = "";
    private String password = "";
    private String userType = "";
    private String email = "";
    private String gender = "";
    private String carId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_sign_in);
        context = this;
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
                new AlertDialog.Builder(CommonSignInActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(button, null);
        builder.show();
    }

    public void signIn(View view) {
        EditText usernameEditText = (EditText)this.findViewById(R.id.sign_in_username_editText);
        username = usernameEditText.getText().toString();
        if(username == null || username.length() < 6) {
            pop("Invalid user name", "User name must have at least 6 characters", "Back");
        }
        EditText passwordEditText = (EditText)this.findViewById(R.id.sign_in_password_editText);
        password = passwordEditText.getText().toString();
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
        ResultReceiver resultReceiver = new SignInResultReceiver(null);
        Intent intent = new Intent(this, GuuberService.class);
        intent.putExtra("operation", MessageKind.SENDMESSAGE);
        intent.putExtra("message", MessageKind.SIGNIN + ":" + username + ":" + password);
        intent.putExtra("receiver", resultReceiver);
        intent.putExtra("resultCode", ResultCode.SIGNIN);
        intent.putExtra("activityName", ActivityNames.COMMONSIGNINACTIVITY);
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
    }

    @SuppressLint("ParcelCreator")
    public class SignInResultReceiver extends ResultReceiver {

        public SignInResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == ResultCode.SIGNIN) {
                String response = resultData.getString("response");
                System.out.println("response: " + response);
                /**
                 * Message format:
                 * SIGNINOK:username:password:userType:email:gender:carId
                 * or
                 * SIGNINDENIED
                 * */
                String[] elements = response.split(":");
                if (elements[0].equals(MessageReply.SIGNINOK)) {
                    username = elements[1];
                    password = elements[2];
                    userType = elements[3];
                    email = elements[4];
                    gender = elements[5];
                    carId = elements[6];
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
                            pop(
                                    "Username and password not match",
                                    "Please input your correct username and password or sign up",
                                    "Back"
                            );
                        }
                    });
                    return;
                }
            }
        }
    }
}
