package guuber.cmu.edu.activities.passenger;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;
import java.util.List;

/**
 * Created by wangziming on 4/9/16.
 */
public class ViewHistoryActivity extends ListActivity implements android.view.View.OnClickListener{
    private ListView obj;
    Button cancel;
    //ArrayList transactionList = new ArrayList();
    //TextView transactionId ;

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.passenger_view_cancelButton)){
            Intent intent = new Intent(this,FindDriverActivity.class);
            startActivity(intent);
        }else{

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_view_history);

        ListView listView = (ListView) this.findViewById(android.R.id.list);

        TransactionDBController tranController = new TransactionDBController(this);
        Intent intent = getIntent();
        //String userName = "Bob";
        String userName = intent.getStringExtra("userName");
        List<Transaction> transactionList = tranController.selectTransactionsByPassenger(userName);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for(Transaction transaction : transactionList){
            HashMap<String, Object> item = new HashMap<String, Object>();
            /*item.put("transactionID", transaction.getTransaction_id());
            item.put("driverName", transaction.getDriver());
            item.put("StartTime", transaction.getStartTime());*/
            item.put("transactionID", "0");
            item.put("driverName", "Bob");
            item.put("StartTime", "2016");
            data.add(item);
        }

        if(data.size() != 0){
            ListAdapter adapter = new SimpleAdapter(ViewHistoryActivity.this,data, R.layout.passenger_view_transaction_entry,
                    new String[] { "id","drivername","startTime"},
                    new int[] {R.id.pTransactionID, R.id.driver_name,R.id.pStartTime});
            setListAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView listView = (ListView) parent;
                    HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                    String transactionID = data.get("transactionID").toString();
                    //Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();
                    Intent Indent = new Intent(getApplicationContext(), DetailedViewActivity.class);
                    Indent.putExtra("transactionID", transactionID);
                    startActivity(Indent);
                }
            });
        }else{
            Toast.makeText(this,"No Related Transactions",Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}