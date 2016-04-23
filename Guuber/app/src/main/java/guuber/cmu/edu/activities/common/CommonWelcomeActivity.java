package guuber.cmu.edu.activities.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.cmu.guuber.guuber.R;


public class CommonWelcomeActivity extends AppCompatActivity {

    /* these are the background pictures for welcome page */
    int[] backgroundPictures = {
            R.drawable.start_background1,
            R.drawable.start_background2,
            R.drawable.start_background3
    };

    String[] welcomeMessages = {
            "Easier Travel",
            "Cheaper Travel",
            "Happier Travel"
    };

    int index = -1;

    private static final int UPDATE_IMAGE = 6000;

    LinearLayout linearLayout = null;

    TextView textView = null;

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == UPDATE_IMAGE) {
                if (index >= backgroundPictures.length - 1) {
                    index = -1;
                }
                index++;
                linearLayout.setBackgroundResource(backgroundPictures[index]);
                textView.setText(welcomeMessages[index]);
                reloadCarImage();
            }
        }
    };

    private void reloadCarImage() {
        Message message = new Message();
        message.what = UPDATE_IMAGE;
        handler.sendMessageDelayed(message, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.linearLayout = (LinearLayout)this.findViewById(R.id.welcome_root_linearLayout);
        this.textView = (TextView)this.findViewById(R.id.common_welcome_textview);
        reloadCarImage();

        final Context context = this;

        /* start sign in and sign up page after 3 seconds */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(context, CommonSignInSignUpActivity.class);
                context.startActivity(intent);
            }
        }, 8000);
    }

}
