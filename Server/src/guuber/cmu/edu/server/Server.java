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
				connection.getBufferedWriter().write(message + "\n");
				connection.getBufferedWriter().flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int PORTNUMBER = 55555;
	
	public static void main(String[] args) {
		System.out.println("Starting server...");
		try {
			serverSocket = new ServerSocket(PORTNUMBER);
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("received connection from: " + socket.getRemoteSocketAddress().toString());
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
