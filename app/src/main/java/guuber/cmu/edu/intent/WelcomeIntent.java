package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.common.ChooseSignUpSignInActivity;
import guuber.cmu.edu.activities.common.SignInActivity;
import guuber.cmu.edu.activities.common.SignUpActivity;
import guuber.cmu.edu.activities.common.WelcomeActivity;

/**
 * Created by Yanning on 4/9/16.
 */
public class WelcomeIntent {
    private Intent intent;
    private final WelcomeActivity welcomeIntentActivity;
    private final Class<ChooseSignUpSignInActivity> chooseSignUpSignInActivityClass;

    public WelcomeIntent(WelcomeActivity welcomeIntentActivity,Class<ChooseSignUpSignInActivity> chooseSignUpSignInActivityClass) {
        this.welcomeIntentActivity = welcomeIntentActivity;
        this.chooseSignUpSignInActivityClass = chooseSignUpSignInActivityClass;

        intent = new Intent(welcomeIntentActivity, chooseSignUpSignInActivityClass);
    }

    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
