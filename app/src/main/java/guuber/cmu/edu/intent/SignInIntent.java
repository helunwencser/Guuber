package guuber.cmu.edu.intent;

import android.content.Intent;

import guuber.cmu.edu.activities.common.ChooseSignUpSignInActivity;
import guuber.cmu.edu.activities.common.SignInActivity;
import guuber.cmu.edu.activities.common.SignUpActivity;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;

/**
 * Created by wangziming on 4/9/16.
 */
public class SignInIntent {

    private Intent intent;
    private final SignInActivity signInActivity;
    private final Class<FindDriverActivity> findDriverActivityClass;
    private final Class<FindPassengerActivity> findPassengerActivityClass;




    public SignInIntent(int switches,SignInActivity signInActivity,
                        Class<FindDriverActivity> findDriverActivityClass,
                        Class<FindPassengerActivity> findPassengerActivityClass,
                        String userName,
                        String password) {
        this.signInActivity = signInActivity;
        this.findDriverActivityClass = findDriverActivityClass;
        this.findPassengerActivityClass = findPassengerActivityClass;
        /*if(from instanceof guuber.cmu.edu.activities.common.ChooseSignUpSignInActivity && to instanceof guuber.cmu.edu.activities.common.SignInActivity){
            intent = new Intent(from,to);
        }*/
        if(switches == 0){
             intent = new Intent(signInActivity,findDriverActivityClass);
        }
        if(switches == 1){
             intent = new Intent(signInActivity,findPassengerActivityClass);
        }
        intent.putExtra("userName", userName);
        intent.putExtra("password", password);

    }



    public Intent getIntent() {

        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
