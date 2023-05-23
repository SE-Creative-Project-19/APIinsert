package Network.Protocol;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocolHeader {

    byte type;
    byte code;
    byte kind;

    public ProtocolHeader(byte type, byte code, byte kind) {
        this.type = type;
        this.code = code;
        this.kind = kind;
    }
}
