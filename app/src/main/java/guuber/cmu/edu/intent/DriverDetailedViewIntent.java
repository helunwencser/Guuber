package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.driver.DetailedViewActivity;
import guuber.cmu.edu.activities.driver.ViewHistoryActivity;

/**
 * Created by Yanning on 4/10/16.
 */
public class DriverDetailedViewIntent {
    private Intent intent;

    private final DetailedViewActivity detailedViewActivity;

    public DriverDetailedViewIntent(DetailedViewActivity detailedViewActivity, Class<ViewHistoryActivity> viewClass) {
        this.detailedViewActivity = detailedViewActivity;
        intent = new Intent(detailedViewActivity, viewClass);
    }

    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
