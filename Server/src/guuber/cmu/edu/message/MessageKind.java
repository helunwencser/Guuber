package guuber.cmu.edu.message;

/**
 * This class defines all kinds of messages
 * */
public class MessageKind {
    /* sign up message */
	public static final String SIGNUP = "SIGNUP";
	
    /* sign in message */
	public static final String SIGNIN = "SIGNIN";
	
    /* driver location update message */
    public static final String DRIVERLOC = "DRIVERLOC";

    /* passenger location update message */
    public static final String PASSENGERLOC = "PASSENGERLOC";

    /* driver location should be deleted */
    public static final String DRIVEREXIT = "DRIVEREXIT";
    
    /* passenger location should be deleted */
    public static final String PASSENGEREXIT = "PASSENGEREXIT";
    
    /* driver start the ride */
    public static final String STARTRIDE = "STARTRIDE";
}
