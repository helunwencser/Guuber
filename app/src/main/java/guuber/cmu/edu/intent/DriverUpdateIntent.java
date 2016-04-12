package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.driver.UpdateProfileActivity;

/**
 * Created by Yanning on 4/10/16.
 */
public class DriverUpdateIntent {

    private Intent intent;

    private final UpdateProfileActivity updateProfileActivity;

    public DriverUpdateIntent(UpdateProfileActivity updateProfileActivity, Class findPassengerClass) {
        this.updateProfileActivity = updateProfileActivity;
        intent = new Intent(updateProfileActivity, findPassengerClass);
    }

    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
