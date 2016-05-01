package guuber.cmu.edu.activities.passenger;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import  android.widget.ArrayAdapter;

/**
 * Created by wangziming on 4/9/16.
 */
public class ViewHistoryActivity extends AppCompatActivity {
    private ListView listView;
    Button cancel;
    //ArrayList transactionList = new ArrayList();
    //TextView transactionId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_view_history);

        listView = (ListView) this.findViewById(R.id.passenger_view_list);

        View.OnClickListener onClickListener = new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(ViewHistoryActivity.this,FindDriverActivity.class);
                startActivity(intent);
            }
        };
        cancel = (Button)this.findViewById(R.id.passenger_view_cancelButton);
        cancel.setOnClickListener(onClickListener);

        TransactionDBController tranController = new TransactionDBController(this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");
        Log.d("username", userName);
        final List<Transaction> transactionList = tranController.selectTransactionsByDriver(userName);
        Log.d("transaction", transactionList.get(0).toString());


        int transactionSize = transactionList.size();
        if(transactionSize != 0){
            String[] res = new String[transactionSize];
            for(int i = 0; i< transactionSize; i++){

                StringBuilder sam = new StringBuilder();
                sam.append(String.valueOf(transactionList.get(i).getTransaction_id())+"\t");
                sam.append(transactionList.get(i).getDriver()+"\t");
                sam.append(transactionList.get(i).getStartTime());
                res[i] = sam.toString();
                Log.d("res[i]",res[i]);
            }


            listView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, res));


            /*ListAdapter adapter = new SimpleAdapter(ViewHistoryActivity.this,data, R.layout.passenger_view_transaction_entry,
                    new String[] { "id","drivername","startTime"},
                    new int[] {R.id.pTransactionID, R.id.driver_name,R.id.pStartTime});
            setListAdapter(adapter);*/

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                        long arg3) {
                    String transactionID = String.valueOf(transactionList.get(pos).getTransaction_id());
                    Intent Indent = new Intent(getApplicationContext(), DetailedViewActivity.class);
                    Indent.putExtra("transactionID", transactionID);
                    startActivity(Indent);
                }
            });

        }else{
            Toast.makeText(this,"No Related Transactions",Toast.LENGTH_SHORT).show();
        }


    }


}