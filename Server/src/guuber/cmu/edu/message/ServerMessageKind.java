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
    public static final String DRIVEREXIT = "DRIVEREXIT";

    /* passenger location should be deleted */
    public static final String PASSENGEREXIT = "PASSENGEREXIT";

    /* passenger's destination */
    public static final String PASSENGERDEST = "PASSENGERDEST";

    /* driver start the ride */
    public static final String STARTRIDE = "STARTRIDE";

    /* chatting */
    public static final String CHAT = "CHAT";
}
