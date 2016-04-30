package guuber.cmu.edu.server;

import java.io.IOException;

import guuber.cmu.edu.db.DBOperation;
import guuber.cmu.edu.db.User;
import guuber.cmu.edu.message.MessageKind;
import guuber.cmu.edu.message.MessageReply;

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
				/**
				 * Message format:
				 * SIGNUP:username:password:userType:email:gender:carId
				 * */
				case MessageKind.SIGNUP: 
					if(this.dbOperation.selectByUsername(elements[1]) == null) {
						this.connection.getBufferedWriter().write(MessageReply.SIGNUPOK + "\n");
						this.connection.getBufferedWriter().flush();
						this.connection.setUsername(elements[1]);
						this.connection.setUserType(elements[3]);
						if(elements[3].equals("Driver")) {
							Connections.addDriverConnection(elements[1], connection);
							this.dbOperation.insertUser(
									new User(
											elements[1],
											elements[2],
											elements[3],
											elements[4],
											elements[5],
											elements[6]
											)
									);
						} else {
							Connections.addPassengerConnection(elements[1], connection);
							this.dbOperation.insertUser(
									new User(
											elements[1],
											elements[2],
											elements[3],
											elements[4],
											elements[5],
											""
											)
									);
						}
					} else{
						this.connection.getBufferedWriter().write(MessageReply.SIGNUPDENIED + "\n");
						this.connection.getBufferedWriter().flush();
					}
					break;
				/**
				 * Message format:
				 * SIGNIN:username:password
				 * */
				case MessageKind.SIGNIN:
					User user = this.dbOperation.selectByUsernameAndPassword(elements[1], elements[2]);
					if(user == null) {
						System.out.println("Sign in denied");
						this.connection.getBufferedWriter().write(MessageReply.SIGNINDENIED + "\n");
						this.connection.getBufferedWriter().flush();
					} else {
						System.out.println("Sign in ok");
						String response = MessageReply.SIGNINOK + ":" + user.toMessage() + "\n";
						System.out.println(response);
						this.connection.getBufferedWriter().write(response);
						this.connection.getBufferedWriter().flush();
					}
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbOperation.closeResources();
	}
}
