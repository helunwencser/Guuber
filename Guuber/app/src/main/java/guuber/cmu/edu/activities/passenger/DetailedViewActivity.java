package guuber.cmu.edu.activities.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;
import java.util.List;


/**
 * Created by wangziming on 4/9/16.
 */
public class DetailedViewActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button returnButton;
    TextView driverName;
    TextView startTime;
    TextView endTime;
    TextView cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_detailed_view);

        returnButton = (Button)findViewById(R.id.passenger_detailed_returnButton);
        driverName = (TextView)findViewById(R.id.transactionPassenger);
        startTime  = (TextView)findViewById(R.id.transactionStartTime);
        endTime = (TextView)findViewById(R.id.transactionEndTime);
        cost = (TextView)findViewById(R.id.transactionCost);
        Intent intent = getIntent();
        int transactionID = Integer.parseInt(intent.getStringExtra("transactionID"));

        TransactionDBController controller = new TransactionDBController(this);
        List<Transaction> result = controller.selectTransactionsByTransactionID(transactionID);

        driverName.setText(result.get(0).getDriver());
        startTime.setText(result.get(0).getStartTime());
        endTime.setText(result.get(0).getEndTime());
        cost.setText(result.get(0).getCost());

    }



    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.passenger_detailed_returnButton)){
            Intent intent = new Intent(this,FindDriverActivity.class);
            startActivity(intent);
        }

    }
}
