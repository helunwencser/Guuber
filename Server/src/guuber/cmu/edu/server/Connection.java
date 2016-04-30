package guuber.cmu.edu.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * This class defines the connection to one client.
 * It includes inputStream and outputStream.
 * */
public class Connection {
	
	/* the username of this client */
	private String username;
	
	/* the user type of this client */
	private String userType;
	
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
	
	public Connection(InputStream inputStream, OutputStream outputStream) {
		this.bufferedReader = new BufferedReader(new BufferedReader(new InputStreamReader(inputStream)));
		this.bufferedWriter = new BufferedWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
		this.username = "";
		this.userType = "";
	}

	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
