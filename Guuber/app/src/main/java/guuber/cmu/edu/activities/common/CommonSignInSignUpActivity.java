package guuber.cmu.edu.activities.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.cmu.guuber.guuber.R;

public class CommonSignInSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_sign_in_sign_up);
    }

    public void chooseSignInSignUp(View view) {
        if(view == this.findViewById(R.id.common_signInButton)) {
            System.out.println("Sign in");
        } else {
            System.out.println("Sign up");
        }
    }
}
