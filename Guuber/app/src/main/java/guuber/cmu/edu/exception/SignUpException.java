package guuber.cmu.edu.exception;

/**
 * Created by Yanning on 4/9/16.
 */
public class SignUpException extends Exception {
    private int errorNumber;
    private String errorMessage;

    public SignUpException(int errorNumber, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorMessage = errorMessage;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
