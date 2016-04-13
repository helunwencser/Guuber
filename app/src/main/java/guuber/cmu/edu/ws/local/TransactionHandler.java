package guuber.cmu.edu.ws.local;

import android.content.Context;

import java.util.List;

import guuber.cmu.edu.dbLayout.TransactionDBController;
import guuber.cmu.edu.entities.Transaction;

/**
 * Created by lunwenh on 4/13/2016.
 */

/*
 * This class provides the insert and select operation for transaction
 * */
public class TransactionHandler {
    private TransactionDBController transactionDBController;

    public TransactionHandler(Context context) {
        this.transactionDBController = new TransactionDBController(context);
    }

    /**
     * insert one transaction into database
     * @param transaction
     *        the transaction to be inserted
     * */
    public void insertTransaction(Transaction transaction) {
        this.transactionDBController.insertTransaction(transaction);
    }


    /**
     * get the list of transactions whose driver name is driver
     * @param driver
     *        the name of driver
     *
     * @return the list of transaction
     * */
    public List<Transaction> selectTransactionsByDriver(String driver) {
        return this.transactionDBController.selectTransactionsByDriver(driver);
    }

    /**
     * get the list of transactions whose passenger name is passenger
     * @param passenger
     *        the name of passenger
     *
     * @return the list of transaction
     * */
    public List<Transaction> selectTransactionsByPassenger(String passenger) {
        return this.selectTransactionsByPassenger(passenger);
    }
}
