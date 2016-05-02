package guuber.cmu.edu.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by Yanning on 4/9/16.
 */
public class SignInException extends Exception {
    private int errorNumber;
    private String errorMessage;

    public SignInException(int errorNumber) {
        this.errorNumber = errorNumber;
        switch(errorNumber) {
            case 1:
                this.errorMessage = "Invalid user name";
                break;
            case 2:
                this.errorMessage = "Invalid password";
                break;
            case 3:
                this.errorMessage = "Username and password not match";
                break;
        }
        Log.d("Exception", errorMessage);

    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void alert(Context context) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle(errorMessage);
        switch(errorNumber) {
            case 1:
                builder.setMessage("User name must have at least 6 characters");
                break;
            case 2:
                builder.setMessage("password must contain 8 to 20 characters," +
                        "it must contain at least one uppercase, one lowercase, one digit," +
                        "one special character (@#$%!)");
                break;
            case 3:
                builder.setMessage("Please input your correct username and password or sign up");
                break;
        }
        builder.setPositiveButton("Back", null);
        builder.show();
    }
}
