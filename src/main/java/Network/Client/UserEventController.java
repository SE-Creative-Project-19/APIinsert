package Network.Client;

import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolHeader;
import Network.Protocol.ProtocolKind;
import Network.Protocol.ProtocolType;
import persistence.dao.UserDAO;
import persistence.dto.ServiceInfoDTO;
import persistence.dto.UserDTO;
import service.ServiceInfoService;
import view.ServiceInfoView;
import view.UserView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserEventController {
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ProtocolHeader protocolHeader = null;

    UserDTO userDTO;
    Scanner sc = new Scanner(System.in);

    public UserEventController(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
    }

    public void signUp() {
        //TODO 회원가입 정보 set 해줘야함.
        userDTO.setID("test");
        userDTO.setPW("1234");
        userDTO.setName("chanjin");
        userDTO.setPhoneNumber("010-1111-2222");
        userDTO.setAddress("경상북도 구미시 대학로 61 오름관 26호");
        userDTO.setType(2);

        if (userDTO.getType() == 2) { // Volunteer
            protocolHeader = new ProtocolHeader(ProtocolType.SIGNUP, ProtocolCode.REGISTER_INFO, ProtocolKind.VOLUNTEER);
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            } catch (IOException e) {
                System.out.print("Error: Send Register Info");
                e.printStackTrace();
            }
        } else if (userDTO.getType() == 3) { // Manager
            protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.REGISTER_INFO, ProtocolKind.MANAGER);
            //userDTO.set(기관)
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            } catch (IOException e) {
                System.out.print("Error: Send Register Info");
                e.printStackTrace();
            }
        }
        String result = "";
        try {
            result = (String) ois.readObject();
            System.out.print(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.print("Error: Receive Register Info");
            e.printStackTrace();
        }

    }

    public void login() {
        userDTO.setID("test");
        userDTO.setPW("1234");
        userDTO.setType(2); // TODO 로그인 시 봉사자이면 2, 담당자이면 3

        if (userDTO.getType() == 2) { // Volunteer
            protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolKind.VOLUNTEER);
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            } catch (IOException e) {
                System.out.println("Error: Send Login Info");
                e.printStackTrace();
            }
        } else if (userDTO.getType() == 3) { // Manager
            protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolKind.MANAGER);
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            } catch (IOException e) {
                System.out.println("Error: Send Login Info");
                e.printStackTrace();
            }
        }
        //TODO 로그인 완료 시 해당 DTO를 서버에서 받아온 정보로 가득 채우는 거는 어떤가?
        // 서버에서 return DTO로 된 후 받아온 다음 userDTO = 받아온 DTO => 이러면 회원인 상태에서의 유저의 모든 정보 받아올 수 있음.
        String result = "";
        try {
            result = (String) ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Receive Login Info");
            e.printStackTrace();
        }

    }

    public void findMyID() {
        userDTO.setPhoneNumber("010-1111-2222");//setPhone
        protocolHeader = new ProtocolHeader(ProtocolType.FIND_MY_INFO, ProtocolCode.FIND_ID, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send findMyID");
        }
        try {
            userDTO = (UserDTO) ois.readObject();
            System.out.println(userDTO.getID());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Receive findMyID");
            e.printStackTrace();
        }
    }

    public void findMyPW() {
        userDTO.setID("ID");//setID
        userDTO.setPhoneNumber("010");//setPhone
        userDTO.setName("PCJ");//setName
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
            result = (String) ois.readObject(); //비밀번호 재설정 해주세요.
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive findMyPW");
        }
    }

    public void setUpdatePW() {
        //새로운 비번 set -> 비밀번호 찾기와 회원 정보 수정에서의 비밀번호 재설정 기능이 같아서 하나로 묶음.
        userDTO.setPW("PW");
        try {
            protocolHeader = new ProtocolHeader(ProtocolType.MYPAGE, ProtocolCode.SET_NEW_PW, ProtocolKind.COMMON);
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send UpdatePW");
        }
        String result = "";
        try {
            result = (String) ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive UpdatePW");
        }
    }

    public void showMyInfo() {
        userDTO.setID("test");
        try {
            protocolHeader = new ProtocolHeader(ProtocolType.MYPAGE, ProtocolCode.SHOW_MY_INFO, ProtocolKind.COMMON);
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send showMyInfo");
            e.printStackTrace();
        }

        UserView userView = new UserView();
        try {
            userDTO = (UserDTO) ois.readObject();
            userView.showInfoUser(userDTO);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive MyInfo");
            e.printStackTrace();
        }
    }

    public void updateMyInfo() {
        //수정 정보 입력 But 여기서 Password는 바꾸면 안되는디? userDTO 하나로 사용하는 것이 맞을까?
        userDTO.setID("updateTest");
        userDTO.setName("PCJ");
        userDTO.setAddress("경상북도 구미시 옥계북로 33 105동 1402호");
        userDTO.setPhoneNumber("010-3333-4444");
        if (userDTO.getType() == 3) { //담당자의 경우
            userDTO.setFacility("기관");
        }

        try {
            protocolHeader = new ProtocolHeader(ProtocolType.MYPAGE, ProtocolCode.UPDATE_MY_INFO, ProtocolKind.COMMON);
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send UpdateMyInfo");
            e.printStackTrace();
        }

        String result = "";
        try {
            result = (String) ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive updateMyInfoResult");
        }
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
        List<ServiceInfoDTO> list = null;
        try {
            list = (List<ServiceInfoDTO>) ois.readObject();
            serviceInfoView.printMainServiceInfo(list);
        } catch (IOException | ClassNotFoundException e) {
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
