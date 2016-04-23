package guuber.cmu.edu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the main function for server
 * */
public class Server {
	
	/* server socket */
	private static ServerSocket serverSocket; 
	
	/* set stores all connections */
	private static Set<Connection> connections = new HashSet<Connection>();
	
	/* broad cast message to all clients */
	public static void broadCast(String message) {
		try {
			for(Connection connection : connections) {
				connection.getObjectOutputStream().writeObject(message);	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			serverSocket = new ServerSocket(8888);
			while(true) {
				Socket socket = serverSocket.accept();
				Connection connection = new Connection(socket.getInputStream(), socket.getOutputStream()); 
				connections.add(connection);
				new Thread(new ClientHandler(connection)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
