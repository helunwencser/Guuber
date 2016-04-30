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
import guuber.cmu.edu.activities.passenger.*;
import guuber.cmu.edu.activities.driver.StartServiceActivity;
import guuber.cmu.edu.activities.passenger.UpdateProfileActivity;
import guuber.cmu.edu.activities.passenger.ViewHistoryActivity;

public class FindPassengerActivity extends AppCompatActivity {

    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_find_passenger);

        View.OnClickListener findButtonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPassengerActivity.this, StartServiceActivity.class);
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
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg2 == 0) {
                    Log.d("sdsd0", "sdsd0");
                    Intent intent = new Intent(FindPassengerActivity.this, UpdateProfileActivity.class);
                    intent.putExtra("userName", "Bob");
                    startActivity(intent);

                } else if (arg2 == 1) {
                    Log.d("sdsd1", "sdsd1");
                    Intent intent = new Intent(FindPassengerActivity.this, ViewHistoryActivity.class);
                    intent.putExtra("userName", "Bob");
                    startActivity(intent);
                } else if(arg2 == 2) {
                    Log.d("sdsd2", "sdsd2");
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
