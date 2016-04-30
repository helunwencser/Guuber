package guuber.cmu.edu.activities.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;

public class FindPassengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_find_passenger);

        Button findButton =
                (Button) findViewById(R.id.driver_findButton);
        findButton.setOnClickListener(findButtonClicked);
    }

    public void findPassenger(View view) {

    }

    View.OnClickListener findButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FindPassengerActivity.this, StartServiceActivity.class);
            startActivity(intent);
        }
    };
}
