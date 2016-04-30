package guuber.cmu.edu.activities.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.driver.FindPassengerActivity;
import guuber.cmu.edu.activities.driver.UpdateProfileActivity;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.activities.passenger.ViewHistoryActivity;

public class CommonSignInSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_sign_in_sign_up);

    }

    public void chooseSignInSignUp(View view) {
        Intent intent;
        if(view == this.findViewById(R.id.common_signInButton)) {
            intent = new Intent(this, FindDriverActivity.class);
        } else {
            intent = new Intent(this, FindPassengerActivity.class);
        }
        this.startActivity(intent);
    }
}
