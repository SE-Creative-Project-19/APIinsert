package Network.Protocol;

public class ProtocolCode {

    //TYPE SIGNUP
    public final static byte REGISTER_INFO = 1;

    //TYPE LOGIN
    public final static byte LOGIN_INFO = 1;

    //TYPE FIND_MY_INFO
    public final static byte FIND_ID = 1;
    public final static byte FIND_PW = 2;
    public final static byte UPDATE_PW = 3;

    //TYPE MYPAGE
    public final static byte SHOW_MY_INFO = 1;
    public final static byte UPDATE_MY_INFO = 2;
    public final static byte SET_NEW_PW = 3;

    //TYPE INQUIRY, VOLUNTEER
    public final static byte SERVICE_LIST_INQUIRY = 1;
    public final static byte SERVICE_CONTENT_INQUIRY = 2;
    public final static byte MY_PARTICIPATE_IN_LIST = 3;

    //TYPE INQUIRY, MANAGER
    public final static byte PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST = 1;
    public final static byte MY_ORGANIZATION_ACTIVITY_LIST = 2;

    //TYPE ACCEPTANCE, MANAGER
    public final static byte ACCEPT = 1;
    public final static byte REJECT = 2;

    //TYPE REGISTER, VOLUNTEER
    public final static byte REGISTER_SERVICE_ACTIVITY = 1;
    //TYPE REGISTER, MANAGER
    public final static byte MANNER_TEMPERATURE = 1;

    //TYPE FILTERING
    public final static byte FIELD_SERVICE_SELECT = 1;
    public final static byte PERIOD_SERVICE_SELECT = 2;
    public final static byte SERVICE_AREA_SELECT = 3;
    public final static byte SERVICE_NAME_SELECT = 4;

    //TYPE CHATTING

}
