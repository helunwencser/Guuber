import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.cmu.guuber.guuber.R;


public class viewHistory extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengertransactionview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonClicked);
    }

    View.OnClickListener cancelButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gotoFindDriver =  new Intent(viewHistory.this, findDriver.class);
            startActivity(gotoFindDriver);
        }
    };

    public void viewDetails(int transactionID) {
        Intent details = new Intent(viewHistory.this, detailedView.class);
        details.putExtra("transaction_id", transactionID);
        startActivity(details);
    }
}
