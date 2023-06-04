package Network.Protocol;

public class ProtocolCode {

    //TYPE SIGNUP
    public final static byte REGISTER_INFO = 1;
    public final static byte CHECKID = 2;

    //TYPE LOGIN
    public final static byte LOGIN_INFO = 1;

    //TYPE FIND_MY_INFO
    public final static byte FIND_ID = 1;
    public final static byte FIND_PW = 2;

    //TYPE MYPAGE
    public final static byte SHOW_MY_INFO = 1;
    public final static byte UPDATE_MY_INFO = 2;
    public final static byte SET_NEW_PW = 3;

    //TYPE INQUIRY, VOLUNTEER
    public final static byte SERVICE_LIST_INQUIRY = 1;
    public final static byte SERVICE_CONTENT_INQUIRY = 2;
    public final static byte MY_PARTICIPATE_IN_LIST = 3;

    //TYPE INQUIRY, MANAGER
    public final static byte PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST = 4;
    public final static byte PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST_Result = 5;
    public final static byte MY_ORGANIZATION_ACTIVITY_LIST = 6;

    //TYPE ACCEPTANCE, MANAGER
    public final static byte ACCEPT = 1;
    public final static byte REJECT = 2;

    //TYPE REGISTER, VOLUNTEER
    public final static byte REGISTER_SERVICE_ACTIVITY = 1;
    //TYPE REGISTER, MANAGER
    public final static byte MANNER_TEMPERATURE = 2;

    //TYPE FILTERING
    public final static byte FILTER = 1;


    //TYPE CHATTING

}
