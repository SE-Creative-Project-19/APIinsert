package Network.Protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ProtocolType {

    public final static byte SIGNUP = 1;

    public final static byte LOGIN = 2;

    public final static byte FIND_MY_INFO= 3;

    public final static byte MYPAGE = 4;

    public final static byte INQUIRY = 5;

    public final static byte ACCEPTANCE = 6;

    public final static byte REGISTER = 7;

    public final static byte FILTERING = 8;

    public final static byte CHATTING = 9;

    public static byte[] getHeader(byte type, byte code, byte kind) throws IOException {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bao);

        dos.writeByte(type);
        dos.writeByte(code);
        dos.writeByte(kind);

        return bao.toByteArray();
    }
}
