package guuber.cmu.edu.ws.local;

import android.content.Context;

import java.util.List;

import guuber.cmu.edu.dbLayout.MessageDBController;
import guuber.cmu.edu.entities.Message;

/**
 * Created by lunwenh on 4/13/2016.
 */

/*
 * This class provides the insert and select operation for message
 * */
public class MessageHandler {
    private MessageDBController messageDBController;

    public MessageHandler(Context context) {
        this.messageDBController = new MessageDBController(context);
    }

    /**
     * insert one message into database
     * @param message
     *        the message to be inserted into database
     * */
    public void insertMessage(Message message) {
        this.messageDBController.insertMessage(message);
    }
    /**
     * get all messages in database
     * @return the list of messages in database
     * */
    public List<Message> selectMessage() {
        return this.messageDBController.selectMessages();
    }
}
