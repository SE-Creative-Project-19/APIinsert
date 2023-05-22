package persistence.dto;


import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class MessageHeaderDTO {
    private int messageHeaderPK; //메세지헤더PK
    private String sender; //송신자 이름
    private String receiver; //수신자 이름
    private String lastMessage; //최근 메세지
    private String lastSender; //최근 송신자
    private Date time; //시간
    private int userPK; //사용자PK

    public MessageHeaderDTO() {
    }

    public MessageHeaderDTO(int messageHeaderPK, String sender, String receiver, String lastMessage, String lastSender, Date time, int userPK) {
        this.messageHeaderPK = messageHeaderPK;
        this.sender = sender;
        this.receiver = receiver;
        this.lastMessage = lastMessage;
        this.lastSender = lastSender;
        this.time = time;
        this.userPK = userPK;
    }
}