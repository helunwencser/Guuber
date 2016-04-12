package guuber.cmu.edu.activities.passenger;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.cmu.guuber.guuber.R;
import guuber.cmu.edu.dbLayout.local.TransactionModel;
import guuber.cmu.edu.intent.PassengerViewHistoryIntent;
import guuber.cmu.edu.ws.local.TransactionDBController;

/**
 * Created by wangziming on 4/9/16.
 */
public class ViewHistoryActivity extends AppCompatActivity {

    private PassengerViewHistoryIntent intentWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passenger_activity_view_history);

        TransactionDBController db = new TransactionDBController(this);

        ListView historyList = (ListView) findViewById(R.id.passenger_view_listView);

        String passenger = "";

        MyAdapter adapter = new MyAdapter(this, db.selectTransactionsByPassenger(passenger));

        historyList.setAdapter(adapter);

        Button cancelButton =
                (Button) findViewById(R.id.passenger_view_cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);
    }

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toHome();
        }
    };

    private void toHome() {
        intentWrapper = new PassengerViewHistoryIntent(ViewHistoryActivity.this, FindDriverActivity.class);
        this.startActivity(intentWrapper.getIntent());
    }

    private class MyAdapter extends CursorAdapter {
        public MyAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.passenger_view_history_listview, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView id = (TextView) view.findViewById(R.id.passenger_view_history_id);
            TextView start = (TextView) view.findViewById(R.id.passenger_view_history_id);
            TextView end = (TextView) view.findViewById(R.id.passenger_view_history_endlocation);

            String passenger = cursor.getString(cursor.getColumnIndexOrThrow(TransactionModel._ID));
            String startLoc = cursor.getString(cursor.getColumnIndexOrThrow(TransactionModel.START_LOCATION));
            String endLoc = cursor.getString(cursor.getColumnIndexOrThrow(TransactionModel.END_LOCATION));

            id.setText(passenger + "   ");
            start.setText(startLoc + "   ");
            end.setText(endLoc);
        }
    }
}