package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.passenger.UpdateProfileActivity;

/**
 * Created by Yanning on 4/10/16.
 */
public class PassengerUpdateIntent {
    private Intent intent;

    private final UpdateProfileActivity updateProfileActivity;

    public PassengerUpdateIntent(UpdateProfileActivity updateProfileActivity, Class findDriverClass) {
        this.updateProfileActivity = updateProfileActivity;
        intent = new Intent(updateProfileActivity, findDriverClass);
    }

    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
