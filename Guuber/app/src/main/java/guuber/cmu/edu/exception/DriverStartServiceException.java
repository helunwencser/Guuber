package guuber.cmu.edu.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by Yanning on 5/1/16.
 */
public class DriverStartServiceException extends Exception {
    private int errorNumber;
    private String errorMessage;

    public DriverStartServiceException(int errorNumber) {
        this.errorNumber = errorNumber;
        switch(errorNumber) {
            case 1:
                this.errorMessage = "No passenger selected";
                break;
            case 2:
                this.errorMessage = "Passenger has not selected a destination";
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
        builder.setTitle("Exception of Starting");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("Back", null);
        builder.show();
    }
}