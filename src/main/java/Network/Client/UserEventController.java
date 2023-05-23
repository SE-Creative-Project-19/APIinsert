package Network.Client;

import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolHeader;
import Network.Protocol.ProtocolKind;
import Network.Protocol.ProtocolType;
import persistence.dto.UserDTO;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserEventController {
    ObjectInputStream ois;
    ObjectOutputStream oos;

    ProtocolHeader protocolHeader = null;

    int userType = 0;

    public UserEventController(ObjectOutputStream oos, ObjectInputStream ois) {
        this.ois = ois;
        this.oos = oos;
    }
    public void signUp(){
        protocolHeader = new ProtocolHeader(ProtocolType.SIGNUP, ProtocolCode.REGISTER_INFO, ProtocolKind.COMMON);
        UserDTO userDTO = new UserDTO();//TODO 회원가입 정보 set 해줘야함.
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
        }catch (IOException e) {
            System.out.print("Error: Send Sign up Info");
            e.printStackTrace();
        }

        String result = "";
        try {
            result = (String) ois.readObject();//TODO 회원가입 완료 정보 받기
            System.out.print(result);
        }catch (IOException | ClassNotFoundException e ) {
            System.out.print("Error: Receive Sign up Info");
            e.printStackTrace();
        }

    }
    public void login() {
        userType = 0; // TODO 로그인 시 봉사자이면 1, 담당자이면 2
        UserDTO userDTO  = new UserDTO();
        userDTO.setID("ID");
        userDTO.setPW("PW");
        userDTO.setType(userType);

        if(userType == 1) { // Volunteer
            protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolKind.VOLUNTEER);
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
            }catch (IOException e) {
                System.out.print("Error: Send Login Info");
                e.printStackTrace();
            }
        }else if(userType == 2) { // Manager
            protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolKind.MANAGER);
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
            }catch (IOException e) {
                System.out.print("Error: Send Login Info");
                e.printStackTrace();
            }
        }
        String result = "";
        try{
            result = (String)ois.readObject();
        }catch (IOException | ClassNotFoundException e) {
            System.out.print("Error: Receive Login Info");
            e.printStackTrace();
        }

    }
    public void findMyID() {

    }
    public void findMyPW() {

    }
    public void showMyInfo() {

    }
    public void updateMyInfo() {

    }
    public void setNewPW() {

    }
    public void inquiryServiceList() {

    }
    public void inquiryServiceContent() {

    }
    public void myParticipateInList() {

    }
    public void participateInServiceList() {

    }
    public void myOrganizationActivityList() {

    }
    public void activityAccept() {

    }
    public void activityReject() {

    }
    public void registerServiceActivity() {

    }
    public void mannerTemperature() {

    }
    public void fieldServiceSelect() {

    }
    public void periodServiceSelect() {

    }
    public void serviceAreaSelect() {

    }
    public void serviceNameSelect() {

    }
    //채팅
}
