package persistence.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserDTO implements Serializable {
    private int userPK; //사용자PK
    private String ID;
    private String PW;
    private String phoneNumber;
    private String address;
    private int mannerTemperature; //매너온도
    private int type; //사용자 유형
    private String name;
    private int serviceInfoPK;
    private String facility;

    public UserDTO() {
    }

    public UserDTO(int userPK, String ID, String PW, String phoneNumber, String address, int mannerTemperature, int type, String name, String facility, int serviceInfoPK) {
        this.userPK = userPK;
        this.ID = ID;
        this.PW = PW;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.mannerTemperature = mannerTemperature;
        this.type = type;
        this.name = name;
        this.facility = facility;
        this.serviceInfoPK = serviceInfoPK;
    }
}