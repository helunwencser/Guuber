package guuber.cmu.edu.dbLayout.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import guuber.cmu.edu.dbLayout.DBConfig;

/**
 * Created by Yanning on 4/8/16.
 */
public class UserDBHelper extends SQLiteOpenHelper {

    private static final String INT_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + UserModel.TABLE_NAME + " (" +
            UserModel.ID + INT_TYPE + " AUTO_INCREMENT" + COMMA_SEP +
            UserModel.USERNAME + TEXT_TYPE + COMMA_SEP +
            UserModel.PASSWORD + TEXT_TYPE + COMMA_SEP +
            UserModel.USERTYPE + TEXT_TYPE + COMMA_SEP +
            UserModel.GENDER + TEXT_TYPE + COMMA_SEP +
            UserModel.EMAIL + TEXT_TYPE + COMMA_SEP +
            UserModel.CARID + TEXT_TYPE + COMMA_SEP +
            "PRIMARY KEY (" + UserModel.ID + ")" +
            " )";

    public UserDBHelper(Context context) {
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
