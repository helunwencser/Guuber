package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.common.CommonWelcomeActivity;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.driver.UpdateProfileActivity;
import guuber.cmu.edu.activities.driver.ViewHistoryActivity;

/**
 * Created by Yanning on 4/10/16.
 */
public class FindPassengerIntent {
    private Intent intent;

    private final FindPassengerActivity findPassengerActivity;

    public FindPassengerIntent(FindPassengerActivity findPassengerActivity, int position) {
        this.findPassengerActivity = findPassengerActivity;
        switch (position) {
            case 0: // UserProfile
                intent = new Intent(findPassengerActivity, UpdateProfileActivity.class);
                break;
            case 1: // Transactions
                intent = new Intent(findPassengerActivity, ViewHistoryActivity.class);
                break;
            case 2: // Log out
                intent = new Intent(findPassengerActivity, CommonWelcomeActivity.class);
                break;
        }
    }


    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
