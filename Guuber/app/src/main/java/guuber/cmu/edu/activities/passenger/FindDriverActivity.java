package guuber.cmu.edu.activities.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.common.CommonSignInActivity;
import guuber.cmu.edu.activities.common.CommonSignInSignUpActivity;
import guuber.cmu.edu.activities.common.CommonSignUpActivity;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.service.GuuberService;

public class FindDriverActivity extends AppCompatActivity  {

    Spinner spin;
    String username;
    String password;
    String userType;
    String email;
    String gender;
    String carId;
    private ArrayAdapter<String> adapter;
    private final String[] categories={"UserProfile","Transactions","Log out"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_find_driver);


        if(CommonSignInActivity.userinfo.getUsername() != null){
            username = CommonSignInActivity.userinfo.getUsername();
            password = CommonSignInActivity.userinfo.getPassword();
            userType = CommonSignInActivity.userinfo.getUserType();
            email = CommonSignInActivity.userinfo.getEmail();
            gender = CommonSignInActivity.userinfo.getGender();
            carId = CommonSignInActivity.userinfo.getCarId();
        }else{
            username = CommonSignUpActivity.userinfo.getUsername();
            password = CommonSignUpActivity.userinfo.getPassword();
            userType = CommonSignUpActivity.userinfo.getUserType();
            email = CommonSignUpActivity.userinfo.getEmail();
            gender = CommonSignUpActivity.userinfo.getGender();
            carId = CommonSignUpActivity.userinfo.getCarId();

        }


        Log.d("UserInfoP",username+"\t"+password+"\t"+userType+"\t"+email+"\t"+gender+"\t"+carId);

        View.OnClickListener findButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDriverActivity.this, StartServiceActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        };
        Button findButton =
                (Button) findViewById(R.id.passenger_findButton);
        findButton.setOnClickListener(findButtonClicked);


        spin = (Spinner)this.findViewById(R.id.passenger_find_drop_page);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_profile_page, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin.setAdapter(adapter);
        spin.setSelection(0, false);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    if (arg2 == 1) {
                        Intent intent = new Intent(FindDriverActivity.this, UpdateProfileActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("userType", userType);
                        intent.putExtra("password", password);
                        intent.putExtra("email", email);
                        intent.putExtra("gender", gender);
                        intent.putExtra("carId", carId);
                        Log.d("UserInfoPENTER", username + "\t" + password + "\t" + userType + "\t" + email + "\t" + gender + "\t" + carId);
                        startActivity(intent);

                    } else if (arg2 == 2) {
                        Intent intent = new Intent(FindDriverActivity.this, ViewHistoryActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else if(arg2 == 3){
                        /* end the background service */
                        Intent endServiceIntent = new Intent(FindDriverActivity.this, GuuberService.class);
                        endServiceIntent.putExtra("operation", Operation.ENDBACKGROUNDSERVICE);
                        startService(endServiceIntent);
                        Intent stopServiceIntent = new Intent(FindDriverActivity.this, GuuberService.class);
                        stopService(stopServiceIntent);
                        Intent intent = new Intent(FindDriverActivity.this, CommonSignInSignUpActivity.class);
                        startActivity(intent);
                    }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    public void findDriver(View view) {

    }




}
