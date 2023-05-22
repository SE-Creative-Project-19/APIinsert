package persistence.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class MessageDTO implements Serializable {
    private int messagePK; //PK
    private String messageContent; //대화 내용
    private Date time; //작성 시간
    private int messageHeaderPK; //메세지 헤더 PK(해당 메세지 헤더에 관한 특정 시간에 대화내용을 나타냄)

    public MessageDTO() {
    }

    public MessageDTO(int messagePK, String messageContent, Date time, int messageHeaderPK) {
        this.messagePK = messagePK;
        this.messageContent = messageContent;
        this.time = time;
        this.messageHeaderPK = messageHeaderPK;
    }
}