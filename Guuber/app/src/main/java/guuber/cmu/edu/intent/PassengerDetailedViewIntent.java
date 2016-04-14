package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.passenger.DetailedViewActivity;
import guuber.cmu.edu.activities.passenger.ViewHistoryActivity;

/**
 * Created by Yanning on 4/10/16.
 */
public class PassengerDetailedViewIntent {
    private Intent intent;

    private final DetailedViewActivity detailedViewActivity;

    public PassengerDetailedViewIntent(DetailedViewActivity detailedViewActivity, Class<ViewHistoryActivity> viewClass) {
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
