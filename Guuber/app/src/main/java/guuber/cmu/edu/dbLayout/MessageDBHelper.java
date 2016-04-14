package guuber.cmu.edu.dbLayout;

/**
 * Created by lunwenh on 4/8/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class defines the table of message
 * */
public class MessageDBHelper extends SQLiteOpenHelper {

    private static final String INT_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + MessageModel.TABLE_NAME + " (" +
            MessageModel._ID + INT_TYPE + " AUTO_INCREMENT" + COMMA_SEP +
            MessageModel.SENDER + TEXT_TYPE + COMMA_SEP +
            MessageModel.RECEIVER + TEXT_TYPE + COMMA_SEP +
            MessageModel.CONTENT + TEXT_TYPE + COMMA_SEP +
            MessageModel.TIMESTAMP + TEXT_TYPE + COMMA_SEP +
            "PRIMARY KEY (" + MessageModel._ID + ")" +
            " )";

    public MessageDBHelper(Context context) {
        super(context, DBConfig.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
