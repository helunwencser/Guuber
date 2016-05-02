package guuber.cmu.edu.activities.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.activities.common.CommonSignInActivity;
import guuber.cmu.edu.activities.common.CommonSignUpActivity;
import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;
import java.util.List;




/**
 * Created by wangziming on 4/9/16.
 */
public class ViewHistoryActivity extends AppCompatActivity {
    private ListView listView;
    Button cancel;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_view_history);

        listView = (ListView) this.findViewById(R.id.driversss_view_list);

        View.OnClickListener onClickListener = new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(ViewHistoryActivity.this,FindPassengerActivity.class);
                startActivity(intent);
            }
        };
        cancel = (Button)this.findViewById(R.id.driver_view_cancelButton);
        cancel.setOnClickListener(onClickListener);

        TransactionDBController tranController = new TransactionDBController(this);
        Intent intent = getIntent();

        username = intent.getStringExtra("username");

        if(!CommonSignInActivity.userinfo.getUsername().equals("")){
            username = CommonSignInActivity.userinfo.getUsername();

        }else{
            username = CommonSignUpActivity.userinfo.getUsername();
        }

        final List<Transaction> transactionList = tranController.selectTransactionsByDriver(username);
        int transactionSize = transactionList.size();

        if(transactionSize != 0){

            String[] res = new String[transactionSize];
            for(int i = 0; i< transactionSize; i++){

                StringBuilder sam = new StringBuilder();
                sam.append(transactionList.get(i).getPassenger()+"\t");
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
                    Indent.putExtra("username", transactionList.get(pos).getDriver());
                    Indent.putExtra("passenger", transactionList.get(pos).getPassenger());
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
    protected void onStart(){
         super.onStart();
        setContentView(R.layout.driver_activity_view_history);

        listView = (ListView) this.findViewById(R.id.driversss_view_list);

        View.OnClickListener onClickListener = new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(ViewHistoryActivity.this,FindPassengerActivity.class);
                startActivity(intent);
            }
        };
        cancel = (Button)this.findViewById(R.id.driver_view_cancelButton);
        cancel.setOnClickListener(onClickListener);

        TransactionDBController tranController = new TransactionDBController(this);
        Intent intent = getIntent();

        username = intent.getStringExtra("username");

        if(!CommonSignInActivity.userinfo.getUsername().equals("")){
            username = CommonSignInActivity.userinfo.getUsername();

        }else{
            username = CommonSignUpActivity.userinfo.getUsername();
        }
        final List<Transaction> transactionList = tranController.selectTransactionsByDriver(username);
        int transactionSize = transactionList.size();
        if(transactionSize != 0){
            String[] res = new String[transactionSize];
            for(int i = 0; i< transactionSize; i++){
                StringBuilder sam = new StringBuilder();
                sam.append(transactionList.get(i).getPassenger()+"\t");
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
                    Indent.putExtra("username", transactionList.get(pos).getDriver());
                    Indent.putExtra("passenger", transactionList.get(pos).getPassenger());
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
