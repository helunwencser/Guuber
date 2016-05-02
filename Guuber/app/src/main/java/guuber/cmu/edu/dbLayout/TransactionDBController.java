package guuber.cmu.edu.dbLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import guuber.cmu.edu.entities.Transaction;
import android.util.Log;

/**
 * Created by lunwenh on 4/8/2016.
 */

/**
 * This class defines the CRUD operations on transaction table
 * */
public class TransactionDBController {
    private TransactionDBHelper transactionDBHelper = null;

    /* singleton design pattern */
    public TransactionDBController(Context context) {
        if(transactionDBHelper == null) {
            transactionDBHelper = new TransactionDBHelper(context);
        }
    }

    /**
     * insert one transaction into database
     * @param transaction
     *        the transaction to be inserted
     * */
    public void insertTransaction(Transaction transaction) {
        SQLiteDatabase sqLiteDatabase = this.transactionDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TransactionModel.DRIVER, transaction.getDriver());
        contentValues.put(TransactionModel.PASSENGER, transaction.getPassenger());
        contentValues.put(TransactionModel.START_TIME, transaction.getStartTime());
        contentValues.put(TransactionModel.END_TIME, transaction.getEndTime());
        contentValues.put(TransactionModel.START_LOCATION, transaction.getStartLocation());
        contentValues.put(TransactionModel.END_LOCATION, transaction.getEndLocation());
        contentValues.put(TransactionModel.COST, transaction.getCost());
        sqLiteDatabase.insert(TransactionModel.TABLE_NAME, null, contentValues);
    }

    /**
     * get the list of transactions whose driver name is driver
     * @param driver
     *        the name of driver
     *
     * @return the list of transaction
     * */
    public List<Transaction> selectTransactionsByDriver(String driver) {
        List<Transaction> res = new ArrayList<Transaction>();

        SQLiteDatabase sqLiteDatabase = this.transactionDBHelper.getReadableDatabase();
        Log.d("tranD","0");
        String[] projection = {
                TransactionModel._ID,
                TransactionModel.DRIVER,
                TransactionModel.PASSENGER,
                TransactionModel.START_TIME,
                TransactionModel.END_TIME,
                TransactionModel.START_LOCATION,
                TransactionModel.END_LOCATION,
                TransactionModel.COST
        };
        Log.d("tranD","1D");
        String sortOrder = TransactionModel._ID + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                TransactionModel.TABLE_NAME,
                projection,
                TransactionModel.DRIVER + "=?",
                new String[] {driver},
                null,
                null,
                sortOrder
        );
        Log.d("tranD","2D");
        if(cursor.moveToFirst()) {
            Log.d("tranD","3D");
            do {
                res.add(
                        new Transaction(
                                //cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7)
                        )
                );
            } while(cursor.moveToNext());
        }
        Log.d("tranD","4");
        return res;
    }

    /**
     * get the list of transactions whose passenger name is passenger
     * @param passenger
     *        the name of passenger
     *
     * @return the list of transaction
     * */
    public List<Transaction> selectTransactionsByPassenger(String passenger) {
        List<Transaction> res = new ArrayList<Transaction>();

        SQLiteDatabase sqLiteDatabase = this.transactionDBHelper.getReadableDatabase();
        Log.d("tran","0");
        String[] projection = {
                TransactionModel._ID,
                TransactionModel.DRIVER,
                TransactionModel.PASSENGER,
                TransactionModel.START_TIME,
                TransactionModel.END_TIME,
                TransactionModel.START_LOCATION,
                TransactionModel.END_LOCATION,
                TransactionModel.COST
        };
        Log.d("tran","1");
        String sortOrder = TransactionModel._ID + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                TransactionModel.TABLE_NAME,
                projection,
                TransactionModel.PASSENGER + "=?",
                new String[] {passenger},
                null,
                null,
                sortOrder
        );
        Log.d("tran","2");
        if(cursor.moveToFirst()) {
            Log.d("tran","3");
            do {
                res.add(
                        new Transaction(
                               // cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7)
                        )
                );
            } while(cursor.moveToNext());
        }
        Log.d("tran","4");
        return res;
    }


    public List<Transaction> selectTransactionsByTransactionID(int TransactionID) {
        List<Transaction> res = new ArrayList<Transaction>();

        SQLiteDatabase sqLiteDatabase = this.transactionDBHelper.getReadableDatabase();

        String[] projection = {
                TransactionModel._ID,
                TransactionModel.DRIVER,
                TransactionModel.PASSENGER,
                TransactionModel.START_TIME,
                TransactionModel.END_TIME,
                TransactionModel.START_LOCATION,
                TransactionModel.END_LOCATION,
                TransactionModel.COST
        };

        String sortOrder = TransactionModel._ID + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                TransactionModel.TABLE_NAME,
                projection,
                TransactionModel._ID + "=?",
                new String[]{String.valueOf(TransactionID)},
                null,
                null,
                sortOrder
        );
        if(cursor.moveToFirst()) {
            do {
                res.add(
                        new Transaction(
                                //cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7)
                        )
                );
            } while(cursor.moveToNext());
        }
        return res;
    }
}
//cursor.getInt(cursor.getColumnIndex("_ID")),