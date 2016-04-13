package guuber.cmu.edu.activities.passenger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.intent.FindDriverIntent;

public class FindDriverActivity extends AppCompatActivity {

    private FindDriverIntent intentWrapper;
    private Spinner userSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_find_driver);

        userSpinner = (Spinner) findViewById(R.id.passenger_find_drop_page);

        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intentWrapper = new FindDriverIntent(FindDriverActivity.this, position);
                FindDriverActivity.this.startActivity(intentWrapper.getIntent());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
