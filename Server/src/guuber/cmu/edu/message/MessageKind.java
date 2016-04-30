package guuber.cmu.edu.message;

/**
 * This class defines all kinds of messages
 * */
public class MessageKind {
    /* sign up message */
	public static final String SIGNUP = "SIGNUP";
	
    /* sign in message */
	public static final String SIGNIN = "SIGNIN";
	
    /* driver location message */
    public static String DRIVERLOC = "DRIVERLOC";

    /* passenger location message */
    public static String PASSENGERLOC = "PASSENGERLOC";
    
    /* driver location should be deleted */
    public static String DRIVEREXIT = "DRIVEREXIT";
    
    /* passenger location should be deleted */
    public static String PASSENGEREXIT = "PASSENGEREXIT";
}
