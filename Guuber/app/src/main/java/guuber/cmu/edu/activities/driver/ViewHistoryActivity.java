package guuber.cmu.edu.activities.driver;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.passenger.*;
import guuber.cmu.edu.dbLayout.TransactionModel;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;

/**
 * Created by wangziming on 4/9/16.
 */
public class ViewHistoryActivity extends AppCompatActivity implements android.view.View.OnClickListener{
    private ListView obj;
    Button cancel;
    //ArrayList transactionList = new ArrayList();
    //TextView transactionId ;

    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.driver_view_cancelButton)){
            Intent intent = new Intent(this,FindPassengerActivity.class);
            startActivity(intent);
        }else{

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_view_history);

        ListView listView = (ListView) this.findViewById(R.id.driver_view_listView);

        TransactionDBController tranController = new TransactionDBController(this);
        Intent intent = getIntent();
        //String userName = "Bob";
        String userName = intent.getStringExtra("userName");
        List<Transaction> transactionList = tranController.selectTransactionsByDriver(userName);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        for(Transaction transaction : transactionList){
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("transactionID", transaction.getTransaction_id());
            item.put("passengerName", transaction.getPassenger());
            item.put("StartTime", transaction.getStartTime());
            data.add(item);
        }

        if(data.size() != 0){
            ListAdapter adapter = new SimpleAdapter(ViewHistoryActivity.this,data, R.layout.driver_view_transaction_entry,
                    new String[] { "id","driverName","startTime"},
                    new int[] {R.id.pTransactionID, R.id.passenger_name,R.id.pStartTime});
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView listView = (ListView) parent;
                    HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                    String transactionID = data.get("transactionID").toString();
                    //Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();
                    Intent Indent = new Intent(getApplicationContext(), guuber.cmu.edu.activities.driver.DetailedViewActivity.class);
                    Indent.putExtra("transactionID", transactionID);
                    startActivity(Indent);
                }
            });
        }else{
            Toast.makeText(this, "No Related Transactions", Toast.LENGTH_SHORT).show();
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
