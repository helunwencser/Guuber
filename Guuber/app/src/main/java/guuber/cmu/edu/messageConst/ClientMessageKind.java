package guuber.cmu.edu.messageConst;

/**
 * Created by helunwen on 4/30/16.
 */
public class ClientMessageKind {
    public static final String SIGNUPOK = "SIGNUPOK";

    public static final String SIGNUPDENIED = "SIGNUPDENIED";

    public static final String SIGNINOK = "SIGNINOK";

    public static final String SIGNINDENIED = "SIGNINDENIED";

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

    /*update user profile*/
    public static final String UPDATEPASSENGERPROFILEOKAY = "UPDATEPASSENGERPROFILEOKAY";
    public static final String UPDATEPASSENGERPROFILEDENIED = "UPDATEPASSENGERPROFILEDENIED";

    public static final String UPDATEDRIVERPROFILEOKAY  = "UPDATEDRIVERPROFILEOKAY ";
    public static final String UPDATEDRIVERPROFILEDENIED = "UPDATEDRIVERPROFILEDENIED";

}
