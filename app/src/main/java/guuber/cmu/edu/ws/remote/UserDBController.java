package guuber.cmu.edu.ws.remote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import guuber.cmu.edu.dbLayout.server.UserDBHelper;
import guuber.cmu.edu.dbLayout.server.UserModel;
import guuber.cmu.edu.entities.User;

/**
 * Created by Yanning on 4/8/16.
 */
public class UserDBController {
    private SQLiteDatabase database; // database object
    private UserDBHelper databaseOpenHelper; // database helper

    // public constructor for UserDBController
    public UserDBController(Context context)
    {
        // create a new UserDBHelper
        databaseOpenHelper =
                new UserDBHelper(context);
    } // end UserDBController constructor

    public void open() throws SQLException
    {
        // create or open a database for reading/writing
        database = databaseOpenHelper.getWritableDatabase();
    } // end method open

    // close the database connection
    public void close()
    {
        if (database != null)
            database.close(); // close the database connection
    } // end method close

    public boolean verifySignUp(String userName) {
        String query = "SELECT * FROM " + UserModel.TABLE_NAME + " WHERE " + UserModel.USERNAME
                + " = '" + userName + "'";
        open();
        Cursor cursor = database.rawQuery(query, null);
        close();
        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean verifySignIn(String userName, String password) {
        String query = "SELECT " + UserModel.PASSWORD +" FROM " + UserModel.TABLE_NAME + " WHERE " + UserModel.USERNAME
                + " = '" + userName + "'";
        open();
        Cursor cursor = database.rawQuery(query, null);
        close();
        if (cursor.getCount() > 0) {
            if (password.equals(cursor.getString(0))) {
                return true;
            }
        }
        return false;
    }

    public boolean insertUser(String userName, String password, String userType,
                              String gender, String email, String carID)
    {
        if (!verifySignUp(userName)) {
            return false;
        }
        ContentValues newtEx = new ContentValues();
        newtEx.put(UserModel.USERNAME, userName);
        newtEx.put(UserModel.PASSWORD, password);
        newtEx.put(UserModel.USERTYPE, userType);
        newtEx.put(UserModel.GENDER, gender);
        newtEx.put(UserModel.EMAIL, email);
        newtEx.put(UserModel.CARID, carID);
        open();
        database.insert(UserModel.TABLE_NAME, null, newtEx);
        close();
        return true;
    } // end method insertUser

    public boolean updateUser(Integer id, String userName, String password, String userType,
                              String gender, String email, String carID) {
        if (!verifySignUp(userName)) {
            return false;
        }
        String updateQuery = "UPDATE " + UserModel.TABLE_NAME + " SET "
                + UserModel.USERNAME + " = '" + userName + "',"
                + UserModel.PASSWORD + " = '" + password + "',"
                + UserModel.USERTYPE + " = '" + userType + "',"
                + UserModel.GENDER + " = '" + gender + "',"
                + UserModel.EMAIL + " = '" + email + "',"
                + UserModel.CARID + " = '" + carID + "'"
                + " WHERE " + UserModel.ID + " = " + Integer.toString(id);
        open();
        database.execSQL(updateQuery);
        close();
        return true;
    }

    public User getInfo(String userName) throws Exception {
        String query = "SELECT * FROM " + UserModel.TABLE_NAME + " WHERE " + UserModel.USERNAME
                + " = '" + userName + "'";
        open();
        Cursor cursor = database.rawQuery(query, null);
        close();
        User user = null;
        if (cursor.getCount() > 0) {
            Integer id = cursor.getInt(0);
            String password = cursor.getString(2);
            String userType = cursor.getString(3);
            String gender = cursor.getString(4);
            String email = cursor.getString(5);
            String carID = cursor.getString(6);

            user = new User(id, userName, password, userType, gender, email, carID);

        }
        return user;
    }
}
