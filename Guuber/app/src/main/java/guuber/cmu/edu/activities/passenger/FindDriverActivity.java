package guuber.cmu.edu.activities.passenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.cmu.guuber.guuber.R;

public class FindDriverActivity extends AppCompatActivity {

    private Spinner userSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_find_driver);

    }
}
