package guuber.cmu.edu.activities.driver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;

/**
 * Created by wangziming on 4/9/16.
 */
public class DetailedViewActivity extends AppCompatActivity {

    private DriverDetailedViewIntent intentWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_detailed_view);

        Button returnButton =
                (Button) findViewById(R.id.driver_detailed_returnButton);
        returnButton.setOnClickListener(returnButtonClicked);
    }


    View.OnClickListener returnButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toHistory();
        }
    };

    public void toHistory() {
        intentWrapper = new DriverDetailedViewIntent(DetailedViewActivity.this, ViewHistoryActivity.class);
        this.startActivity(intentWrapper.getIntent());
    }
}