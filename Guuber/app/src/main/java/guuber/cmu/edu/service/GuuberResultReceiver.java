package guuber.cmu.edu.service;

import android.os.ResultReceiver;

/**
 * Created by helunwen on 4/30/16.
 */
public class GuuberResultReceiver {
    private ResultReceiver resultReceiver;
    private int resultCode;

    public GuuberResultReceiver(ResultReceiver resultReceiver, int resultCode) {
        this.resultReceiver = resultReceiver;
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public ResultReceiver getResultReceiver() {
        return resultReceiver;
    }

    public void setResultReceiver(ResultReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }
}
