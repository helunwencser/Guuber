package guuber.cmu.edu.activities.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.intent.WelcomeIntent;

/**
 * Created by wangziming on 4/9/16.
 */
public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_welcome);

        WelcomeIntent welcomeIntent = new WelcomeIntent(WelcomeActivity.this, ChooseSignUpSignInActivity.class);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(welcomeIntent.getIntent());
    }
}
