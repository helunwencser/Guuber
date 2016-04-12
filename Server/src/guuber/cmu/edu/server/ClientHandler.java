package guuber.cmu.edu.server;

import java.io.IOException;

import guuber.cmu.edu.db.DBOperation;

/**
 * This class defines a new thread for listening
 * message from client and send back response
 * */
public class ClientHandler implements Runnable {
	
	private Connection connection;
	
	private DBOperation dbOperation;
	
	public ClientHandler(Connection connection) {
		this.connection = connection;
		this.dbOperation = new DBOperation();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Object message = null;
		try {
			while((message = this.connection.getObjectInputStream().readObject()) != null) {
				//TODO: add logic for different kinds of message
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
