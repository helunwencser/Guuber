package guuber.cmu.edu.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by Yanning on 4/10/16.
 */
public class UpdateException extends Exception  {
    private int errorNumber;
    private String errorMessage;

    public UpdateException(int errorNumber) {
        this.errorNumber = errorNumber;
        switch(errorNumber) {
            case 1:
                this.errorMessage = "Invalid user name";
                break;
            case 2:
                this.errorMessage = "Invalide password";
                break;
            case 3:
                this.errorMessage = "Update failed";
                break;
            case 4:
                this.errorMessage = "Password and retype don't match";
                break;
            case 6:
                this.errorMessage = "Invalid email";
                break;
            case 7:
                this.errorMessage = "Invalid carId";
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
                builder.setMessage("Please Try Again");
                break;
            case 4:
                builder.setMessage("Please Try Again");
                break;
            case 6:
                builder.setMessage("Please input valid email address");
                break;
            case 7:
                builder.setMessage("Please input valid carId");
                break;
        }
        builder.setPositiveButton("Back", null);
        builder.show();
    }
}
