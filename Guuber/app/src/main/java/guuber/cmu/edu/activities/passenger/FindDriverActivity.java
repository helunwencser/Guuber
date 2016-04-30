package guuber.cmu.edu.activities.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;

public class FindDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_find_driver);

        Button findButton =
                (Button) findViewById(R.id.passenger_findButton);
        findButton.setOnClickListener(findButtonClicked);

    }

    View.OnClickListener findButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FindDriverActivity.this, StartServiceActivity.class);
            startActivity(intent);
        }
    };
    public void findDriver(View view) {

    }
}
