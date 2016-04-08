package guuber.cmu.edu.dbLayout.local;

import android.provider.BaseColumns;

/**
 * Created by lunwenh on 4/8/2016.
 */

/**
 * This class define the model of chat history
 * */
public class MessageModel implements BaseColumns {
    public static String TABLE_NAME = "message_table";
    public static String SENDER = "sender";
    public static String RECEIVER = "receiver";
    public static String CONTENT = "content";
    public static String TIMESTAMP = "timeStamp";
}
