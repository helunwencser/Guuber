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

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.dbLayout.MessageDBHelper;
import guuber.cmu.edu.exception.SignInException;
import guuber.cmu.edu.messageConst.ActivityNames;
import guuber.cmu.edu.messageConst.ClientMessageKind;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.messageConst.ServerMessageKind;
import guuber.cmu.edu.ws.remote.GuuberService;
import guuber.cmu.edu.dbLayout.TransactionDBHelper;
import guuber.cmu.edu.entities.User;

public class CommonSignInActivity extends AppCompatActivity {

    public static User userinfo = new User();


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
        TransactionDBHelper transactionDBHelper =  new TransactionDBHelper(this);
        SQLiteDatabase sqLiteDatabase = transactionDBHelper.getWritableDatabase();
        transactionDBHelper.onCreate(sqLiteDatabase);
        MessageDBHelper messageDBHelper = new MessageDBHelper(this);
        messageDBHelper.onCreate(sqLiteDatabase);
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
        try {
            if (username == null || username.length() < 6) {
                throw new SignInException(1);
            }
            EditText passwordEditText = (EditText) this.findViewById(R.id.sign_in_password_editText);
            password = passwordEditText.getText().toString();
            if (password == null || password.length() <= 0 || !password.matches(PASSWORD_RESTRICT)) {
                throw new SignInException(2);
            }
        } catch (SignInException e) {
            e.alert(this);
            return;
        }
        ResultReceiver resultReceiver = new SignInResultReceiver(null);
        Intent intent = new Intent(this, GuuberService.class);
        intent.putExtra("operation", Operation.SENDMESSAGE);
        intent.putExtra("message", ServerMessageKind.SIGNIN + ":" + username + ":" + password);
        intent.putExtra("receiver", resultReceiver);
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
        userinfo.setUsername(username);
        userinfo.setPassword(password);
        userinfo.setUserType(userType);
        userinfo.setEmail(email);
        userinfo.setGender(gender);
        userinfo.setCarId(carId);
    }

    @SuppressLint("ParcelCreator")
    public class SignInResultReceiver extends ResultReceiver {

        public SignInResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String response = resultData.getString("response");
            System.out.println("response: " + response);
            /**
             * Message format:
             * SIGNINOK:username:password:userType:email:gender:carId
             * or
             * SIGNINDENIED
             * */
            String[] elements = response.split(":");
            if (elements[0].equals(ClientMessageKind.SIGNINOK)) {
                username = elements[1];
                password = elements[2];
                userType = elements[3];
                email = elements[4];
                gender = elements[5];
                if(userType.equals("Driver")) {
                    carId = elements[6];
                } else {
                    carId = "";
                }

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
                            throw new SignInException(3);
                        } catch (SignInException e) {
                            e.alert(CommonSignInActivity.this);
                        }
                    }
                });
                return;
            }
        }
    }
}
