package guuber.cmu.edu.dbLayout;

/**
 * Created by lunwenh on 4/8/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import guuber.cmu.edu.entities.Message;

/**
 * This class defines the CRUD operations on message table
 * */
public class MessageDBController {
    private MessageDBHelper messageDBHelper = null;

    /* singleton design pattern */
    public MessageDBController(Context context) {
        if(this.messageDBHelper == null) {
            this.messageDBHelper = new MessageDBHelper(context);
        }
    }

    /**
     * insert one message into database
     * @param message
     *        the message to be inserted into database
     * */
    public void insertMessage(Message message) {
        SQLiteDatabase sqLiteDatabase = this.messageDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageModel.SENDER, message.getSender());
        contentValues.put(MessageModel.RECEIVER, message.getReceiver());
        contentValues.put(MessageModel.CONTENT, message.getContent());
        contentValues.put(MessageModel.TIMESTAMP, message.getTimeStamp());
        sqLiteDatabase.insert(MessageModel.TABLE_NAME, null, contentValues);
    }

    /**
     * get all messages in database
     * @return the list of messages in database
     * */
    public List<Message> selectMessages() {
        List<Message> res = new ArrayList<Message>();
        SQLiteDatabase sqLiteDatabase = this.messageDBHelper.getReadableDatabase();

        String[] projection = {
                MessageModel.SENDER,
                MessageModel.RECEIVER,
                MessageModel.CONTENT,
                MessageModel.TIMESTAMP
        };

        String sortOrder = MessageModel.TIMESTAMP + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                MessageModel.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        if(cursor.moveToFirst()) {
            do {
                res.add(
                        new Message(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3)
                        )
                );
            } while(cursor.moveToNext());
        }
        return res;
    }
}
