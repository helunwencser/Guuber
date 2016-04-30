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
		String message = null;
		try {
			while((message = this.connection.getBufferedReader().readLine()) != null) {
				System.out.println("Received message: " + message);
				String[] elements = message.split(":");
				switch(elements[0]) {
				case MessageKind.SIGNUP: 
					this.connection.getBufferedWriter().write("OK" + "\n");
					this.connection.getBufferedWriter().flush();
					break;
				case MessageKind.SIGNIN:
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
