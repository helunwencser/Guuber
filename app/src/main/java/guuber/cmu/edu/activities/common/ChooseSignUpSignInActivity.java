package guuber.cmu.edu.activities.common;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.intent.ChooseSignInSignUpIntent;

/**
 * Created by wangziming on 4/9/16.
 */
public class ChooseSignUpSignInActivity extends AppCompatActivity {

    public void chooseSignupSignin(View view) {
        Intent intent = null;
        ChooseSignInSignUpIntent chooseSignInSignUpIntent = null;
        if(view == findViewById(R.id.signInButton)) {
             chooseSignInSignUpIntent = new ChooseSignInSignUpIntent(0,this, SignUpActivity.class,SignInActivity.class);

        } else {
             chooseSignInSignUpIntent = new ChooseSignInSignUpIntent(1,this, SignUpActivity.class,SignInActivity.class);
        }
        intent = chooseSignInSignUpIntent.getIntent();
        this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_choose_signup_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }



}
