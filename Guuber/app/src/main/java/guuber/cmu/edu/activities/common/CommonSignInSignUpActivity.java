package guuber.cmu.edu.activities.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.activities.passenger.ViewHistoryActivity;

public class CommonSignInSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_sign_in_sign_up);
        Intent intent = new Intent(this,ViewHistoryActivity.class);
        intent.putExtra("userName","Bob");
        startActivity(intent);
    }

    public void chooseSignInSignUp(View view) {
        if(view == this.findViewById(R.id.common_signInButton)) {
            System.out.println("Sign in");
        } else {
            System.out.println("Sign up");
        }
    }
}
