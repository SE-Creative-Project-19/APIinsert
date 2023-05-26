package Network.Client;

import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolHeader;
import Network.Protocol.ProtocolKind;
import Network.Protocol.ProtocolType;
import persistence.dto.ServiceInfoDTO;
import persistence.dto.UserDTO;
import service.ServiceInfoService;
import view.ServiceInfoView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserEventController {
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ProtocolHeader protocolHeader = null;

    int userType = 0;
    Scanner sc = new Scanner(System.in);

    public UserEventController(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
    }
    public void signUp(){
        UserDTO userDTO = new UserDTO();//TODO 회원가입 정보 set 해줘야함.
        userType = sc.nextInt(); //TODO 임시 테스트용

        if(userType == 1) { // Volunteer
            protocolHeader = new ProtocolHeader(ProtocolType.SIGNUP, ProtocolCode.REGISTER_INFO, ProtocolKind.VOLUNTEER);
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            }catch (IOException e) {
                System.out.print("Error: Send Register Info");
                e.printStackTrace();
            }
        }else if(userType == 2) { // Manager
            protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.REGISTER_INFO, ProtocolKind.MANAGER);
            //userDTO.set(기관)
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            }catch (IOException e) {
                System.out.print("Error: Send Register Info");
                e.printStackTrace();
            }
        }
        String result = "";
        try{
            result = (String)ois.readObject();
            System.out.print(result);
        }catch (IOException | ClassNotFoundException e) {
            System.out.print("Error: Receive Register Info");
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
                oos.flush();
            }catch (IOException e) {
                System.out.println("Error: Send Login Info");
                e.printStackTrace();
            }
        }else if(userType == 2) { // Manager
            protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolKind.MANAGER);
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            }catch (IOException e) {
                System.out.println("Error: Send Login Info");
                e.printStackTrace();
            }
        }
        String result = "";
        try{
            result = (String)ois.readObject();
            System.out.println(result);
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Receive Login Info");
            e.printStackTrace();
        }

    }
    public void findMyID() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("010");//setPhone
        protocolHeader = new ProtocolHeader(ProtocolType.FIND_MY_INFO, ProtocolCode.FIND_ID, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send findMyID");
        }
        try{
            userDTO = (UserDTO)ois.readObject();
            System.out.println(userDTO.getID());
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Receive findMyID");
            e.printStackTrace();
        }
    }
    public void findMyPW() {
        UserDTO userDTO = new UserDTO();
        userDTO.setID("ID");//setID
        userDTO.setPhoneNumber("010");//setPhone
        protocolHeader = new ProtocolHeader(ProtocolType.FIND_MY_INFO, ProtocolCode.FIND_PW, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send findMyPW");
        }
        String result = "";
        try {
            result = (String)ois.readObject(); //비밀번호 재설정 해주세요.
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive findMyPW");
        }
        //새로운 비번 set
        userDTO.setPW("PW");
        try {
            protocolHeader = new ProtocolHeader(ProtocolType.FIND_MY_INFO, ProtocolCode.UPDATE_PW, ProtocolKind.COMMON);
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);

            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send UpdatePW");
        }
        try {
            result = (String)ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive UpdatePW");
        }
    }
    public void showMyInfo() {

    }
    public void updateMyInfo() {

    }
    public void setNewPW() {

    }
    public void inquiryServiceList() throws IOException {
        int page = 3;
        ServiceInfoView serviceInfoView = new ServiceInfoView();
        protocolHeader = new ProtocolHeader(ProtocolType.INQUIRY, ProtocolCode.SERVICE_LIST_INQUIRY, ProtocolKind.VOLUNTEER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(page);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: inquiry Service list");
            e.printStackTrace();
        }
        List<ServiceInfoDTO> list = new ArrayList<>();

        try {
            list = (List<ServiceInfoDTO>) ois.readObject();
            serviceInfoView.printMainServiceInfo(list);
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive Service list");
            e.printStackTrace();
        }
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
