package persistence.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;

@Getter
@Setter
@ToString
public class UserDTO implements Serializable {
    private int userPK; //사용자PK
    private String ID;
    private String PW;
    private String PhoneNumber;
    private String Address;
    private int MannerTemperature; //매너온도
    private int Type; //사용자 유형
    private String Name;
    private String Facility; // serviceInfo 테이블의 mnnstNm를가짐

    public UserDTO() {
    }

    public UserDTO(int userPK, String ID, String PW, String PhoneNumber, String Address, int MannerTemperature, int Type, String Name, String Facility) {
        this.userPK = userPK;
        this.ID = ID;
        this.PW = PW;
        this.PhoneNumber = PhoneNumber;
        this.Address = Address;
        this.MannerTemperature = MannerTemperature;
        this.Type = Type;
        this.Name = Name;
        this.Facility = Facility;
    }
}