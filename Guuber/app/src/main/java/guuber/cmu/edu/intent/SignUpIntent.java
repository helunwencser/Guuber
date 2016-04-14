package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.common.SignUpActivity;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;

/**
 * Created by Yanning on 4/9/16.
 */
public class SignUpIntent {
    private Intent intent;
    private final SignUpActivity signUpActivity;
    private final Class<FindDriverActivity> findDriverActivityClass;
    private final Class<FindPassengerActivity> findPassengerActivityClass;

    public SignUpIntent(int switches, SignUpActivity signUpActivity,
                        Class<FindDriverActivity> findDriverActivityClass,
                        Class<FindPassengerActivity> findPassengerActivityClass,
                        String userName) {
        this.signUpActivity = signUpActivity;
        this.findDriverActivityClass = findDriverActivityClass;
        this.findPassengerActivityClass = findPassengerActivityClass;
        /*if(from instanceof guuber.cmu.edu.activities.common.ChooseSignUpSignInActivity && to instanceof guuber.cmu.edu.activities.common.SignInActivity){
            intent = new Intent(from,to);
        }*/
        if(switches == 1){
            intent = new Intent(signUpActivity,findDriverActivityClass);
        }
        if(switches == 0){
            intent = new Intent(signUpActivity,findPassengerActivityClass);
        }
        intent.putExtra("userName", userName);
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
