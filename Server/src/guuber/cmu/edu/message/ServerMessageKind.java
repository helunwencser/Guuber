package guuber.cmu.edu.message;

/**
 * Created by helunwen on 4/30/16.
 */
public class ServerMessageKind {
    /* sign up message */
    public static final String SIGNUP = "SIGNUP";

    /* sign in message */
    public static final String SIGNIN = "SIGNIN";

    /* driver location udpate message */
    public static final String DRIVERLOC = "DRIVERLOC";

    /* passenger location update message */
    public static final String PASSENGERLOC = "PASSENGERLOC";

    /* driver location should be deleted */
    public static final String DRIVERCANCEL = "DRIVERCANCEL";
    
    /* passenger location should be deleted */
    public static final String  PASSENGERCANCEL= "PASSENGERCANCEL";

    /* passenger's destination */
    public static final String PASSENGERDEST = "PASSENGERDEST";

    /* driver start the ride */
    public static final String STARTRIDE = "STARTRIDE";

    /* driver end the ride */
    public static final String ENDRIDE = "ENDRIDE";
    
    /* chatting */
    public static final String CHATFROMDRIVER = "CHATFROMDRIVER";
    
    /* chatting */
    public static final String CHATFROMPASSENGER = "CHATFROMPASSENGER";

    /* driver request location */
    public static final String DRIVERREQUESTLOC = "DRIVERREQUESTLOC";
    
    /* passenger request location */
    public static final String PASSENGERREQUESTLOC = "PASSENGERREQUESTLOC";
    
    /*update user profile*/
    public static final String UPDATEDRIVERPROFILE = "UPDATEDRIVERPROFILE";

    public static final String UPDATEPASSENGERPROFILE = "UPDATEPASSENGERPROFILE";
    
    /* exit the application */
    public static final String EXIT = "EXIT";
}
