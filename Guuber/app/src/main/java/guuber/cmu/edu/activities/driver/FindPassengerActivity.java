package guuber.cmu.edu.activities.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.common.CommonSignInSignUpActivity;
import guuber.cmu.edu.messageConst.Operation;
import guuber.cmu.edu.service.GuuberService;


public class FindPassengerActivity extends AppCompatActivity {

    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_find_passenger);

        Intent intent = getIntent();


        //userprofile
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        final String userType = intent.getStringExtra("userType");
        final String email = intent.getStringExtra("email");
        final String gender = intent.getStringExtra("gender");
        final String carId = intent.getStringExtra("carId");

        Log.d("UserInfoD",username+"\t"+password+"\t"+userType+"\t"+email+"\t"+gender+"\t"+carId);

        View.OnClickListener findButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPassengerActivity.this, StartServiceActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        };
        Button findButton =
                (Button) findViewById(R.id.driver_findButton);
        findButton.setOnClickListener(findButtonClicked);


        spin = (Spinner)this.findViewById(R.id.driver_find_drop_page);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_profile_page, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spin.setAdapter(adapter);
        spin.setSelection(0, false);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg2 == 1) {
                    Intent intent = new Intent(FindPassengerActivity.this, UpdateProfileActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("userType", userType);
                    intent.putExtra("password", password);
                    intent.putExtra("email", email);
                    intent.putExtra("gender", gender);
                    intent.putExtra("carId", carId);
                    Log.d("UserInfoDENTER", username + "\t" + password + "\t" + userType + "\t" + email + "\t" + gender + "\t" + carId);
                    startActivity(intent);

                } else if (arg2 == 2) {
                    Intent intent = new Intent(FindPassengerActivity.this, ViewHistoryActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);

                } else if(arg2 == 3) {
                    /* end the background service */
                    Intent endServiceIntent = new Intent(FindPassengerActivity.this, GuuberService.class);
                    endServiceIntent.putExtra("operation", Operation.ENDBACKGROUNDSERVICE);
                    startService(endServiceIntent);
                    Intent stopServiceIntent = new Intent(FindPassengerActivity.this, GuuberService.class);
                    stopService(stopServiceIntent);
                    Intent intent = new Intent(FindPassengerActivity.this, CommonSignInSignUpActivity.class);
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
