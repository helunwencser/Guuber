package guuber.cmu.edu.messageConst;

/**
 * Created by helunwen on 4/29/16.
 */

/**
 * This class defines all kinds of messages will be used in Guuber
 * */
public class MessageKind {
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

    /* driver start the ride */
    public static final String STARTRIDE = "STARTRIDE";

    /* Tell GuuberService to send message */
    public static final String SENDMESSAGE = "SENDMESSAGE";

    /* Tell GuuberService to start service */
    public static final String STARTSERVICE = "STARTSERVICE";
}
