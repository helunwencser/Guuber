package guuber.cmu.edu.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class defines all connections between clients and server
 * */
public class Connections {
	
	/* all connections between clients and server */
	private static Set<Connection> connections = new HashSet<Connection>();
	
	/**
	 * all active connections between passenger client and server
	 * active means that the passenger has logged in or signed up
	 * the key of the map is passenger's username which is unique
	 * */
	private static Map<String, Connection> passengerConnections = new HashMap<String, Connection>();
	
	/**
	 * all active connections between driver client and server
	 * active means the passenger has logged in or signed up
	 * the key of the map is the driver's username which is unique
	 * */
	private static Map<String, Connection> driverConnections = new HashMap<String, Connection>();

	public static Set<Connection> getConnections() {
		return connections;
	}
	
	/**
	 * add new connection to connections
	 * @param	connection	the new connection
	 * */
	public static void addConnection(Connection connection) {
		connections.add(connection);
	}
	
	/**
	 * add passenger connection to passengerConnections
	 * @param	passengerName	the username of passenger
	 * 
	 * @param	connection	the new connection to be added
	 * */
	public static void addPassengerConnection(String passengerName, Connection connection) {
		passengerConnections.put(passengerName, connection);
	}
	
	/**
	 * get passenger connection by passenger's username
	 * @param	passengerName	the name of passenger
	 * 
	 * @return	the connection belongs to passenger
	 * */
	public  static Connection getPassengerConnection(String passengerName) {
		if(passengerConnections.containsKey(passengerName)) {
			return passengerConnections.get(passengerName);
		}
		return null;
	}
	
	/**
	 * add driver connection to driverConnections
	 * @param	driverName	the username of driver
	 * 
	 * @param	connection	the new connection to be added
	 * */
	public static void addDriverConnection(String driverName, Connection connection) {
		driverConnections.put(driverName, connection);
	}
	
	/**
	 * get driver connection by driver's username
	 * @param	passengerName	the name of driver
	 * 
	 * @return	the connection belongs to driver
	 * */
	public static Connection getDriverConnection(String driverName) {
		if(driverConnections.containsKey(driverName)) {
			return driverConnections.get(driverName);
		}
		return null;
	}

	public static Map<String, Connection> getPassengerConnections() {
		return passengerConnections;
	}

	public static Map<String, Connection> getDriverConnections() {
		return driverConnections;
	}
}
