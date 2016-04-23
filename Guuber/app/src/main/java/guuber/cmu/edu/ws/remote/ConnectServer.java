package guuber.cmu.edu.ws.remote;

/**
 * Created by lunwenh on 4/13/2016.
 */

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * This class defines the interactions with remove server
 * */
public class ConnectServer {
    private String SERVERADDR = "localhost";

    private int PORT = 8888;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private BlockingDeque<Object> receivedMessage;

    public ConnectServer() {
        try{
            Socket socket = new Socket(SERVERADDR, PORT);
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.receivedMessage = new LinkedBlockingDeque<Object>();
    }

    /**
     * Send message to server
     * @param object - the object to be sent
     * */
    public void sendMessage(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive message from server
     * */
    public void receiveMessage() {
        try{
            while(true) {
                Object object = this.objectInputStream.readObject();
                this.receivedMessage.put(object);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get message received from server.
     * @return If there are messages,
     * return the message; otherwise, return null
     * */
    public Object getMessage() {
        if(this.receivedMessage.size() > 0) {
            return this.receivedMessage.poll();
        }
        return null;
    }
}
