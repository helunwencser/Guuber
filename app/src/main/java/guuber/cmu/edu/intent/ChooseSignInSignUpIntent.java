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
    private final Class<SignUpActivity> signUpActivityClass;
    private final Class<SignInActivity> signInActivityClass;




    public ChooseSignInSignUpIntent(int switches,ChooseSignUpSignInActivity chooseSignUpSignInActivity, Class<SignUpActivity> signUpActivityClass,Class<SignInActivity> signInActivityClass) {
        this.chooseSignUpSignInActivity = chooseSignUpSignInActivity;
        this.signUpActivityClass = signUpActivityClass;
        this.signInActivityClass = signInActivityClass;
        /*if(from instanceof guuber.cmu.edu.activities.common.ChooseSignUpSignInActivity && to instanceof guuber.cmu.edu.activities.common.SignInActivity){
            intent = new Intent(from,to);
        }*/
        if(switches == 0){
            Intent intent = new Intent(chooseSignUpSignInActivity,signUpActivityClass);
        }
        if(switches == 1){
            Intent intent = new Intent(chooseSignUpSignInActivity,signInActivityClass);
        }

    }



    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }


}
