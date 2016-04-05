package edu.cmu.guuber.view.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;


public class ViewHistory extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengertransactionview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new ListViewAdapter());

        listView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                viewDetails(position);
            }
        });
    }

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoFindDriver =  new Intent(ViewHistory.this, FindDriver.class);
            startActivity(gotoFindDriver);
        }
    };

    public void viewDetails(int transactionID) {
        Intent details = new Intent(ViewHistory.this, DetailedView.class);
        details.putExtra("transaction_id", transactionID);
        startActivity(details);
    }




    class ListViewAdapter extends BaseAdapter{

    }
}
