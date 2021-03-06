package guuber.cmu.edu.server;

import java.io.IOException;

import guuber.cmu.edu.db.DBOperation;
import guuber.cmu.edu.db.User;
import guuber.cmu.edu.message.ClientMessageKind;
import guuber.cmu.edu.message.ServerMessageKind;

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
				case ServerMessageKind.SIGNUP: 
					if(this.dbOperation.selectByUsername(elements[1]) == null) {
						this.connection.getBufferedWriter().write(ClientMessageKind.SIGNUPOK + "\n");
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
					} else {
						this.connection.getBufferedWriter().write(ClientMessageKind.SIGNUPDENIED + "\n");
						this.connection.getBufferedWriter().flush();
					}
					break;
				/**
				 * Message format:
				 * SIGNIN:username:password
				 * */
				case ServerMessageKind.SIGNIN:
					User user = this.dbOperation.selectByUsernameAndPassword(elements[1], elements[2]);
					if(user == null) {
						this.connection.getBufferedWriter().write(ClientMessageKind.SIGNINDENIED + "\n");
						this.connection.getBufferedWriter().flush();
					} else {
						String response = ClientMessageKind.SIGNINOK + ":" + user.toMessage() + "\n";
						this.connection.getBufferedWriter().write(response);
						this.connection.getBufferedWriter().flush();
						this.connection.setUsername(user.getUsername());
						this.connection.setUserType(user.getUserType());
						if(user.getUserType().equals("Driver")) {
							Connections.addDriverConnection(user.getUsername(), connection);
						} else {
							Connections.addPassengerConnection(user.getUsername(), connection);
						}
					}
					break;
				/**
				 * Message format:
				 * DRIVERLOC:latitude:longtitude
				 * */
				case ServerMessageKind.DRIVERLOC:
					String driverLocationUpdate = ClientMessageKind.DRIVERLOC + ":" + this.connection.getUsername()
												+ message.substring(message.indexOf(":"));
					Connections.broadcastMessageToPassengers(driverLocationUpdate);
					break;
				/**
				 * Message format:
				 * PASSENGERLOC:latitude:longtitude
				 * */
				case ServerMessageKind.PASSENGERLOC:
					String passengerLocationUpdate = ClientMessageKind.PASSENGERLOC + ":" + this.connection.getUsername()
													+ message.substring(message.indexOf(":"));
					Connections.broadcastMessageToDrivers(passengerLocationUpdate);
					break;
				/**
				 * Message format:
				 * CHATFROMDRIVER:receiver:content
				 * */
				case ServerMessageKind.CHATFROMDRIVER:
					String messageToPassenger = ClientMessageKind.CHATFROMDRIVER + ":" + this.connection.getUsername()
												+ ":" + elements[2];
					Connections.sendMessageToPassenger(elements[1], messageToPassenger);
					break;
				/**
				 * Message format:
				 * CHATFROMPASSENGER:receiver:content
				 * */
				case ServerMessageKind.CHATFROMPASSENGER:
					String messageToDriver = ClientMessageKind.CHATFROMPASSENGER + ":" + this.connection.getUsername()
												+ ":" + elements[2];
					Connections.sendMessageToDriver(elements[1], messageToDriver);
					break;
				/**
				 * Message format:
				 * PASSENGERDEST:latitude:longtitude
				 * */
				case ServerMessageKind.PASSENGERDEST:
					if(this.connection.getUserType().equals("Passenger")) {
						String destinationMessage = ClientMessageKind.PASSENGERDEST + ":" + this.connection.getUsername()
													+ message.substring(message.indexOf(":"));
						Connections.broadcastMessageToDrivers(destinationMessage);
					}
					break;
				/**
				 * Message format:
				 * STARTRIDE:passenger name
				 * */
				case ServerMessageKind.STARTRIDE:
					if(this.connection.getUserType().equals("Driver")) {
						String startRideMessage = ClientMessageKind.STARTRIDE + ":" + this.connection.getUsername();
						Connections.sendMessageToPassenger(elements[1], startRideMessage);
						String deletePassengerMarkMessage = ClientMessageKind.STARTRIDE + ":" + elements[1] + ":" + ClientMessageKind.STARTRIDE;
						Connections.broadcastMessageToDriversExcept(this.connection.getUsername(), deletePassengerMarkMessage);
						String deleteDriverMarkMessage = ClientMessageKind.STARTRIDE + ":" + this.connection.getUsername() + ":" + ClientMessageKind.STARTRIDE;
						Connections.broadcastMessageToPassengerExcept(elements[1], deleteDriverMarkMessage);
					}
					break;
				/**
				 * Message format:
				 * ENDRIDE:passenger name
				 * */
				case ServerMessageKind.ENDRIDE:
					if(this.connection.getUserType().equals("Driver")) {
						String endRideMessage = ClientMessageKind.ENDRIDE;
						Connections.sendMessageToPassenger(elements[1], endRideMessage);
					}
					break;
				/**
				 * Message format:
				 * PASSENGERCANCEL
				 * */
				case ServerMessageKind.PASSENGERCANCEL:
					if(this.connection.getUserType().equals("Passenger")) {
						String passengerCanelMessage = ClientMessageKind.PASSENGERCANCEL + ":" + this.connection.getUsername();
						Connections.broadcastMessageToDrivers(passengerCanelMessage);
					}
					break;
				/**
				 * Message format:
				 * DRIVERCANCEL
				 * */
				case ServerMessageKind.DRIVERCANCEL:
					if(this.connection.getUserType().equals("Driver")) {
						String driverCancelMessage = ClientMessageKind.DRIVERCANCEL + ":" + this.connection.getUsername();
						Connections.broadcastMessageToPassengers(driverCancelMessage);
					}
					break;
				/**
				 * Message format:
				 * UPDATEDRIVERPROFILE:username:password:userType:email:gender:carId
				 * */
				case ServerMessageKind.UPDATEDRIVERPROFILE:
					if(this.connection.getUserType().equals("Driver")) {
						dbOperation.deleteByUsername(elements[1]);
						dbOperation.insertUser(new User(
								elements[1],
								elements[2],
								elements[3],
								elements[4],
								elements[5],
								elements[6]
								));
						String driverUpdateProfileReply = ClientMessageKind.UPDATEDRIVERPROFILEOKAY;
						Connections.sendMessageToDriver(this.connection.getUsername(), driverUpdateProfileReply);
					}
					break;
				/**
				 * Message format:
				 * UPDATEPASSENGERPROFILE:username:password:userType:email:gender
				 * */
				case ServerMessageKind.UPDATEPASSENGERPROFILE:
					if(this.connection.getUserType().equals("Passenger")) {
						dbOperation.deleteByUsername(elements[1]);
						dbOperation.insertUser(new User(
								elements[1],
								elements[2],
								elements[3],
								elements[4],
								elements[5],
								""
								));
						String passengerUpdateProfileReply = ClientMessageKind.UPDATEPASSENGERPROFILEOKAY;
						Connections.sendMessageToPassenger(this.connection.getUsername(), passengerUpdateProfileReply);
					}
					break;
				/**
				 * Message format:
				 * DRIVERREQUESTLOC
				 * */
				case ServerMessageKind.DRIVERREQUESTLOC:
					if(this.connection.getUserType().equals("Driver")) {
						String driverRequestLocationMessage = ClientMessageKind.DRIVERREQUESTLOC;
						Connections.broadcastMessageToPassengers(driverRequestLocationMessage);
					}
					break;
				/**
				 * Message format:
				 * PASSENGERREQUESTLOC
				 * */
				case ServerMessageKind.PASSENGERREQUESTLOC:
					if(this.connection.getUserType().equals("Passenger")) {
						String passengerRequestLocationMessage = ClientMessageKind.PASSENGERREQUESTLOC;
						Connections.broadcastMessageToDrivers(passengerRequestLocationMessage);
					}
					break;
				/**
				 * Message format:
				 * EXIT
				 * */
				case ServerMessageKind.EXIT:
					this.connection.getBufferedReader().close();
					this.connection.getBufferedWriter().close();
					Connections.deleteConnection(this.connection);
					if(this.connection.getUserType().equals("Driver")) {
						Connections.deleteDriverConnection(this.connection.getUsername(), connection);
					} else {
						Connections.deletePassengerConnection(this.connection.getUsername(), connection);
					}
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("User " + this.connection.getUsername() + " is lost or has logout");
		}
		dbOperation.closeResources();
	}
}
