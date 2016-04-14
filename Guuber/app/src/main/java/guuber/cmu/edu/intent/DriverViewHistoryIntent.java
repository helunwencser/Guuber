package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.driver.ViewHistoryActivity;

/**
 * Created by Yanning on 4/10/16.
 */
public class DriverViewHistoryIntent {
    private Intent intent;

    private final ViewHistoryActivity viewHistoryActivity;

    public DriverViewHistoryIntent(ViewHistoryActivity viewHistoryActivity, Class viewClass) {
        this.viewHistoryActivity = viewHistoryActivity;
        intent = new Intent(viewHistoryActivity, viewClass);
    }

    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
