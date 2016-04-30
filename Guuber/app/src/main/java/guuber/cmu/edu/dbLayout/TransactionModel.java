package guuber.cmu.edu.dbLayout;

import android.provider.BaseColumns;

/**
 * Created by lunwenh on 4/8/2016.
 */

/**
 * This class defines the model of transaction
 * */
public class TransactionModel implements BaseColumns{
    public static String TABLE_NAME = "transaction_table";
    public static String _ID = "_id";
    public static String DRIVER = "driver";
    public static String PASSENGER = "passenger";
    public static String START_TIME = "startTime";
    public static String END_TIME = "endTime";
    public static String START_LOCATION = "startLocation";
    public static String END_LOCATION = "endLocation";
    public static String COST = "cost";
}
