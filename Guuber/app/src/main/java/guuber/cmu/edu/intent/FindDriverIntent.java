package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.common.WelcomeActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.activities.passenger.UpdateProfileActivity;
import guuber.cmu.edu.activities.passenger.ViewHistoryActivity;

/**
 * Created by Yanning on 4/10/16.
 */
public class FindDriverIntent {
    private Intent intent;
    private final FindDriverActivity findDriverActivity;

    public FindDriverIntent(FindDriverActivity findDriverActivity, int position) {
        this.findDriverActivity = findDriverActivity;
        switch (position) {
            case 0: // UserProfile
                intent = new Intent(findDriverActivity, UpdateProfileActivity.class);
                break;
            case 1: // Transactions
                intent = new Intent(findDriverActivity, ViewHistoryActivity.class);
                break;
            case 2: // Log out
                intent = new Intent(findDriverActivity, WelcomeActivity.class);
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
