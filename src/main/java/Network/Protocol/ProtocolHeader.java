package Network.Protocol;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtocolHeader {

    byte type;
    byte code;
    byte kind;

    public ProtocolHeader() {
        type = 0;
        code = 0;
        kind = 0;
    }
}
