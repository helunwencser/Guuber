package guuber.cmu.edu.activities.driver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.cmu.guuber.guuber.R;

public class FindPassengerActivity extends AppCompatActivity {

    private FindPassengerIntent intentWrapper;
    private Spinner userSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_find_passenger);

        userSpinner = (Spinner) findViewById(R.id.driver_find_drop_page);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intentWrapper = new FindPassengerIntent(FindPassengerActivity.this, position);
                FindPassengerActivity.this.startActivity(intentWrapper.getIntent());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
