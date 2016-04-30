package guuber.cmu.edu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the main function for server
 * */
public class Server {
	
	/* server socket */
	private static ServerSocket serverSocket; 
	
	private static int PORTNUMBER = 55555;
	
	public static void main(String[] args) {
		System.out.println("Starting server...");
		try {
			serverSocket = new ServerSocket(PORTNUMBER);
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("received connection from: " + socket.getRemoteSocketAddress().toString());
				Connection connection = new Connection(socket.getInputStream(), socket.getOutputStream()); 
				Connections.addConnection(connection);
				new Thread(new ClientHandler(connection)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
