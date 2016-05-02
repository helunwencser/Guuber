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
import guuber.cmu.edu.activities.common.CommonSignInActivity;
import guuber.cmu.edu.activities.common.CommonSignUpActivity;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;
import java.util.List;
import  android.widget.ArrayAdapter;

/**
 * Created by wangziming on 4/9/16.
 */
public class ViewHistoryActivity extends AppCompatActivity {
    private ListView listView;
    String username;
    Button cancel;


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
        username = intent.getStringExtra("username");
        if(!CommonSignInActivity.userinfo.getUsername().equals("")){
            username = CommonSignInActivity.userinfo.getUsername();

        }else{
            username = CommonSignUpActivity.userinfo.getUsername();
        }
        final List<Transaction> transactionList = tranController.selectTransactionsByPassenger(username);
        int transactionSize = transactionList.size();
        if(transactionSize != 0){
            String[] res = new String[transactionSize];
            for(int i = 0; i< transactionSize; i++){
                StringBuilder sam = new StringBuilder();
                sam.append(transactionList.get(i).getDriver()+"\t");
                sam.append(transactionList.get(i).getStartTime());
                res[i] = sam.toString();
            }


            listView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, res));


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                        long arg3) {
                    Intent Indent = new Intent(getApplicationContext(), DetailedViewActivity.class);
                    Indent.putExtra("username", transactionList.get(pos).getPassenger());
                    Indent.putExtra("driver", transactionList.get(pos).getDriver());
                    Indent.putExtra("startTime", transactionList.get(pos).getStartTime());
                    Indent.putExtra("endTime", transactionList.get(pos).getEndTime());
                    Indent.putExtra("startLocation", transactionList.get(pos).getStartLocation());
                    Indent.putExtra("endLocation", transactionList.get(pos).getEndLocation());
                    Indent.putExtra("cost", transactionList.get(pos).getCost());
                    startActivity(Indent);
                }
            });

        }else{
            Toast.makeText(this,"No Related Transactions",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
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
        username = intent.getStringExtra("username");
        if(!CommonSignInActivity.userinfo.getUsername().equals("")){
            username = CommonSignInActivity.userinfo.getUsername();

        }else{
            username = CommonSignUpActivity.userinfo.getUsername();
        }
        final List<Transaction> transactionList = tranController.selectTransactionsByPassenger(username);
        int transactionSize = transactionList.size();
        if(transactionSize != 0){
            String[] res = new String[transactionSize];
            for(int i = 0; i< transactionSize; i++){
                StringBuilder sam = new StringBuilder();
                sam.append(transactionList.get(i).getDriver()+"\t");
                sam.append(transactionList.get(i).getStartTime());
                res[i] = sam.toString();
            }


            listView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, res));


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                        long arg3) {
                    Intent Indent = new Intent(getApplicationContext(), DetailedViewActivity.class);
                    Indent.putExtra("username", transactionList.get(pos).getPassenger());
                    Indent.putExtra("driver", transactionList.get(pos).getDriver());
                    Indent.putExtra("startTime", transactionList.get(pos).getStartTime());
                    Indent.putExtra("endTime", transactionList.get(pos).getEndTime());
                    Indent.putExtra("startLocation", transactionList.get(pos).getStartLocation());
                    Indent.putExtra("endLocation", transactionList.get(pos).getEndLocation());
                    Indent.putExtra("cost", transactionList.get(pos).getCost());
                    startActivity(Indent);
                }
            });

        }else{
            Toast.makeText(this,"No Related Transactions",Toast.LENGTH_SHORT).show();
        }
    }

}