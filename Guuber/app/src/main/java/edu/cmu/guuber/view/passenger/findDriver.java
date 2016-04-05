package edu.cmu.guuber.view.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import edu.cmu.guuber.guuber.R;
import edu.cmu.guuber.view.common.WelcomeActivity;


public class FindDriver extends AppCompatActivity implements  {

    private Spinner dropSpinner;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finddriver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dropSpinner = (Spinner) findViewById(R.id.drop_page);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.drop_page, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropSpinner.setAdapter(adapter);
        dropSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        dropSpinner.setVisibility(View.VISIBLE);


        Button findButton =  (Button) findViewById(R.id.findDriverButton);
        findButton.setOnClickListener(findButtonClicked);
    }

    View.OnClickListener findButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent findDriver = new Intent(FindDriver.this, StartService.class);
            startActivity(findDriver);
        }
    };

    class SpinnerSelectedListener implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
           if(arg2 == 0){

               updateProfile();

           }else if(arg2 == 1){

               viewHistory();

           }else if(arg2 == 2){
               logOut();
           }
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

        public void updateProfile() {
            Intent updateprofile = new Intent(FindDriver.this, UpdateProfile.class);
            startActivity(updateprofile);
        }

        public void viewHistory() {
            Intent viewhistory = new Intent(FindDriver.this, ViewHistory.class);
            startActivity(viewhistory);
        }

        public void logOut() {
            Intent logout = new Intent(FindDriver.this, WelcomeActivity.class);
            startActivity(logout);
        }
    }




}
