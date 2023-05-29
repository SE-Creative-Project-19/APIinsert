package Network.Client;

import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolHeader;
import Network.Protocol.ProtocolKind;
import Network.Protocol.ProtocolType;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import persistence.dto.ServiceInfoDTO;
import persistence.dto.UserDTO;
import persistence.dto.VolunteerDTO;
import service.UserService;
import view.ServiceInfoView;
import view.UserView;
import view.VolunteerView;

import java.io.*;
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
        userDTO = new UserDTO();
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
            userDTO.setFacility("어린이집");
            protocolHeader = new ProtocolHeader(ProtocolType.SIGNUP, ProtocolCode.REGISTER_INFO, ProtocolKind.MANAGER);
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

    public void checkID() {
        userDTO.setID("test");

        protocolHeader = new ProtocolHeader(ProtocolType.SIGNUP, ProtocolCode.CHECKID, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send Check ID");
            e.printStackTrace();
        }
        String result = "";
        try {
            result = (String) ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {

        }
    }

    public void login() {
        userDTO.setID("test");
        userDTO.setPW("1234");
        protocolHeader = new ProtocolHeader(ProtocolType.LOGIN, ProtocolCode.LOGIN_INFO, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: Send Login Info");
            e.printStackTrace();
        }
        try {
            userDTO = (UserDTO) ois.readObject();
            if (userDTO != null) {
                System.out.printf("로그인 성공");
            } else {
                System.out.println("로그인 실패");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Receive Login Info");
            e.printStackTrace();
        }

    }

    public void findMyID() {
        userDTO.setName("admin");
        userDTO.setPhoneNumber("010-3333-4444");
        protocolHeader = new ProtocolHeader(ProtocolType.FIND_MY_INFO, ProtocolCode.FIND_ID, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send findMyID");
        }
        String result = "";
        try {
            result = (String) ois.readObject();
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("아이디를 찾지 못했습니다.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Receive findMyID");
            e.printStackTrace();
        }
    }

    public void findMyPW() {
        userDTO.setID("test");//setID
        userDTO.setPhoneNumber("010-1111-2222");//setPhone
        userDTO.setName("chanjin");//setName
        protocolHeader = new ProtocolHeader(ProtocolType.FIND_MY_INFO, ProtocolCode.FIND_PW, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send findMyPW");
        }
        boolean result = true;
        try {
            result = (boolean) ois.readObject(); //비밀번호 재설정 해주세요. true 이면
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

    public void showMyInfo() {//정상 작동
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
        List<UserDTO> list;
        UserView userView = new UserView();
        try {
            list = (List<UserDTO>) ois.readObject();
            for(int i=0; i<list.size(); i++) {
                userView.showInfoUser(list.get(i));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive MyInfo");
            e.printStackTrace();
        }
    }

    public void updateMyInfo() { //TODO 상철: 값이 안 바뀜
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

    public void inquiryServiceList() {
        System.out.print("페이지 번호를 입력해주세요: ");
        int page = sc.nextInt();
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
        //TODO 범석이 세부 정보 봉사 활동 조회하는 부분 없는 거 같음..
    }

    public void myParticipateInList() { //내가 참여한 봉사활동 리스트
        //TODO 상철이 getVolunteer부분 파라미터 수정 및 내용 수정 바람.
        //TODO id가 파라미터로 받아야 내 아이디에 맞는 것들만 나오는 게 아닌가?
    }

    public void participateInServiceList() { // 봉사활동에 참여한 봉사자 리스트
        //TODO 이거는 해당 되는 부분이 어디 있는 지 모르겠음 있으면 주석 지우고 있는 부분 작성 좀
    }

    public void myOrganizationActivityList() { // 본인 소속 기관의 봉사활동 리스트
        if (userDTO.getType() == 3) { //담당자 일 때만 가능
            userDTO.setFacility("어린이집"); //테스트 용

            try {
                protocolHeader = new ProtocolHeader(ProtocolType.INQUIRY, ProtocolCode.MY_ORGANIZATION_ACTIVITY_LIST, ProtocolKind.MANAGER);
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            } catch (IOException e) {
                System.out.println("Error: send UpdateMyInfo");
                e.printStackTrace();
            }

            try {
                //TODO 범석: UserDTO로 입력받은 기관으로 API에 접근하여 입력받은 기관에 속한 봉사활동들을 뽑은 리스트 출력에 관해서 어떻게 하는 지 모르겠음.
                ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: receive VolunteerDTO List");
                e.printStackTrace();
            }
            //매너 온도를 입력한다면. 아래 작업 만들기.
        } else {
            System.out.println("권한이 없습니다.");
        }

    }

    public void activityAccept() { //봉사 승인

    }

    public void activityReject() { // 봉사 거절

    }

    public void registerServiceActivity() { //봉사 활동 등록 [봉사 승인 이후]

    }

    public void mannerTemperature() {// 매너온도 입력

    }

    public void fieldServiceSelect() {//필터링

    }

    public void periodServiceSelect() {//필터링

    }

    public void serviceAreaSelect() {// 필터링

    }

    public void serviceNameSelect() { //필터링

    }

}
