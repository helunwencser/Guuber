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
public class DetailedViewActivity extends AppCompatActivity{

    Button returnButton;
    TextView driverName;
    TextView startTime;
    TextView endTime;
    TextView cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_detailed_view);

        View.OnClickListener onClickListener = new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(DetailedViewActivity.this,ViewHistoryActivity.class);
                intent.putExtra("userName","Bob");
                startActivity(intent);
            }
        };
        returnButton = (Button)findViewById(R.id.passenger_detailed_returnButton);
        returnButton.setOnClickListener(onClickListener);

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
        cost.setText(String.valueOf(result.get(0).getCost()));

    }


}
