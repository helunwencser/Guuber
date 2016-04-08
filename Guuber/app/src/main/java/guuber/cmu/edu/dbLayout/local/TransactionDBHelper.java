package guuber.cmu.edu.dbLayout.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import guuber.cmu.edu.dbLayout.DBConfig;
import guuber.cmu.edu.entities.Transaction;

/**
 * Created by lunwenh on 4/8/2016.
 */
public class TransactionDBHelper extends SQLiteOpenHelper {

    private static final String INT_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    /* create table */
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TransactionModel.TABLE_NAME + " (" +
            TransactionModel._ID + INT_TYPE + " AUTO_INCREMENT" + COMMA_SEP +
            TransactionModel.DRIVER + TEXT_TYPE + COMMA_SEP +
            TransactionModel.PASSENGER + TEXT_TYPE + COMMA_SEP +
            TransactionModel.START_TIME + TEXT_TYPE + COMMA_SEP +
            TransactionModel.END_TIME + TEXT_TYPE + COMMA_SEP +
            TransactionModel.START_LOCATION + TEXT_TYPE + COMMA_SEP +
            TransactionModel.END_LOCATION + TEXT_TYPE + COMMA_SEP +
            TransactionModel.COST + INT_TYPE + COMMA_SEP +
            "PRIMARY KEY (" + TransactionModel._ID + ")" +
            " )";

    public TransactionDBHelper(Context context) {
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
