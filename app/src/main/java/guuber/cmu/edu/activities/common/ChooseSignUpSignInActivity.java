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

    private ChooseSignInSignUpIntent intentWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_choose_signup_signin);

        Button signInButton =
                (Button) findViewById(R.id.common_signInButton);
        signInButton.setOnClickListener(signInButtonClicked);

        Button signUpButton =
                (Button) findViewById(R.id.common_signUpButton);
        signUpButton.setOnClickListener(signUpButtonClicked);

    }


    View.OnClickListener signInButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intentWrapper = new ChooseSignInSignUpIntent(ChooseSignUpSignInActivity.this, SignInActivity.class);
            startActivity(intentWrapper.getIntent());
        }
    };

    View.OnClickListener signUpButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intentWrapper = new ChooseSignInSignUpIntent(ChooseSignUpSignInActivity.this, SignUpActivity.class);
            startActivity(intentWrapper.getIntent());
        }
    };
}
