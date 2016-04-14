package guuber.cmu.edu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import guuber.cmu.edu.activities.common.ChooseSignUpSignInActivity;
import guuber.cmu.edu.activities.common.SignInActivity;
import guuber.cmu.edu.activities.common.SignUpActivity;

/**
 * Created by wangziming on 4/9/16.
 */
public class ChooseSignInSignUpIntent  {

    private Intent intent;
    private final ChooseSignUpSignInActivity chooseSignUpSignInActivity;

    public ChooseSignInSignUpIntent(ChooseSignUpSignInActivity chooseSignUpSignInActivity, Class signClass) {
        this.chooseSignUpSignInActivity = chooseSignUpSignInActivity;

        intent = new Intent(chooseSignUpSignInActivity,signClass);

    }

    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }


}
