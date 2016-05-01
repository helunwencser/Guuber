package guuber.cmu.edu.activities.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.passenger.FindDriverActivity;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;

/**
 * Created by wangziming on 4/9/16.
 */
public class DetailedViewActivity extends AppCompatActivity {

    Button returnButton;
    TextView driverNameT;
    TextView startTimeT;
    TextView endTimeT;
    TextView costT;
    TextView startLocationT;
    TextView endLocationT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_detailed_view);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String driverName = intent.getStringExtra("driver");
        final String startTime = intent.getStringExtra("startTime");
        final String endtime = intent.getStringExtra("endTime");
        final String cost = intent.getStringExtra("cost");
        final String startLocation = intent.getStringExtra("startLocation");
        final String endLocation = intent.getStringExtra("endLocation");


        View.OnClickListener onClickListener = new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(DetailedViewActivity.this,ViewHistoryActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        };
        returnButton = (Button)findViewById(R.id.passenger_detailed_returnButton);
        returnButton.setOnClickListener(onClickListener);

        driverNameT = (TextView)findViewById(R.id.transactionPassenger);
        startTimeT  = (TextView)findViewById(R.id.transactionStartTime);
        endTimeT = (TextView)findViewById(R.id.transactionEndTime);
        costT = (TextView)findViewById(R.id.transactionCost);
        startLocationT = (TextView)findViewById(R.id.startLocation);
        endLocationT =   (TextView)findViewById(R.id.endLocation);

        driverNameT.setText(driverName);
        startTimeT.setText(startTime);
        endTimeT.setText(endtime);
        costT.setText(cost);
        startLocationT.setText(startLocation);
        endLocationT.setText(endLocation);

    }

}