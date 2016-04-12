package guuber.cmu.edu.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * This class defines the connection to one client.
 * It includes inputStream and outputStream.
 * */
public class Connection {
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	public Connection(InputStream inputStream, OutputStream outputStream) {
		try {
			this.objectInputStream = new ObjectInputStream(inputStream);
			this.objectOutputStream = new ObjectOutputStream(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}
}
