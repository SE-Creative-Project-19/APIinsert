
package Network.Client;

import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolHeader;
import Network.Protocol.ProtocolKind;
import Network.Protocol.ProtocolType;
import persistence.dto.ServiceInfoDTO;
import persistence.dto.UserDTO;
import persistence.dto.VolunteerDTO;
import view.ServiceInfoView;
import view.UserView;

import java.io.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;
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
                System.out.println("로그인 성공");
            } else {
                System.out.println("로그인 실패");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Receive Login Info");
            e.printStackTrace();
        }

    }

    public void findMyID() {
        userDTO.setName("chanjin");
        userDTO.setPhoneNumber("010-1111-2222");
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
        userDTO.setName("chanjin");
        userDTO.setID("test");
        userDTO.setPhoneNumber("010-1111-2222");
        userDTO.setPW("PW");
        protocolHeader = new ProtocolHeader(ProtocolType.FIND_MY_INFO, ProtocolCode.FIND_PW, ProtocolKind.COMMON);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error: send findMyPW");
        }
        boolean result = false;
        try {
            result = (boolean) ois.readObject(); //비밀번호 재설정 해주세요. true 이면
            System.out.println(result);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive findMyPW");
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
        List<UserDTO> list;
        UserView userView = new UserView();
        try {
            list = (List<UserDTO>) ois.readObject();
            for (int i = 0; i < list.size(); i++) {
                userView.showInfoUser(list.get(i));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: receive MyInfo");
            e.printStackTrace();
        }
    }

    public void updateMyInfo() {
        userDTO.setID("test");
        userDTO.setName("LSH");
        userDTO.setAddress("경상북도 구미시 옥계북로 33 105동 1402호");
        userDTO.setPhoneNumber("010-2222-3333");
        userDTO.setPW("1234");
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

    public void inquiryServiceList() { //TODO CLI 환경에서 기능 테스트 완료 밑에서부터는 GUI 연결하면서 해야함.
        int page = 1;
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
        //TODO 봉사활동 세부 정보는 각 리스트의 클릭 이벤트로 객체 반환 후 정보 빼오기
    }
    public void myParticipateInList() { //내가 참여한 봉사활동 리스트
        protocolHeader = new ProtocolHeader(ProtocolType.INQUIRY, ProtocolCode.MY_PARTICIPATE_IN_LIST, ProtocolKind.VOLUNTEER);
        userDTO.setID("test");
        userDTO.setType(2);
        List<Map<String, Object>> first = null;
        List<Map<String, Object>> second = null;
        //이미 로그인 하면 여기에 저장되는 userDTO는 유저이게 된다.
        if (userDTO.getType() == 2) {
            try {
                oos.writeObject(protocolHeader);
                oos.writeObject(userDTO);
                oos.flush();
            } catch (IOException e) {
                System.out.println("Error: myParticipateInList");
                e.printStackTrace();
            }
            try {
                first = (List<Map<String, Object>>) ois.readObject();
                second = (List<Map<String, Object>>) ois.readObject();
                System.out.println(first.toString());
                System.out.println(second.toString());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: receive Error");
                e.printStackTrace();
            }
        }
    }

    public void participateInServiceListManagement(ServiceInfoDTO eventDTO) { // 봉사활동에 참여한 봉사자 리스트 -> 신청 관리
        ServiceInfoDTO serviceInfoDTO = eventDTO; //TODO 클릭 시 객체와 대입해야함
        protocolHeader = new ProtocolHeader(ProtocolType.INQUIRY, ProtocolCode.PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST, ProtocolKind.MANAGER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(serviceInfoDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        List<UserDTO> list = null;
        List<VolunteerDTO> volunteerList = null;
        try {
            volunteerList = (List<VolunteerDTO>) ois.readObject();
            list = (List<UserDTO>) ois.readObject();
            System.out.println(list.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("receive Error");
            e.printStackTrace();
        }

    }

    public void participateInServiceListResult(ServiceInfoDTO eventDTO) { // 봉사활동에 참여한 봉사자 리스트 -> 결과 관리
        ServiceInfoDTO serviceInfoDTO = eventDTO; //TODO 클릭 시 객체와 대입해야함
        protocolHeader = new ProtocolHeader(ProtocolType.INQUIRY, ProtocolCode.PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST_RESULT, ProtocolKind.MANAGER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(serviceInfoDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        List<UserDTO> list = null;
        List<VolunteerDTO> volunteerList = null;
        try {
            volunteerList = (List<VolunteerDTO>) ois.readObject();
            list = (List<UserDTO>) ois.readObject();
            System.out.println(list.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("receive Error");
            e.printStackTrace();
        }
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
            List<ServiceInfoDTO> list = null;
            try {
                list = (List<ServiceInfoDTO>) ois.readObject();
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i).toString());
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: receive VolunteerDTO List");
                e.printStackTrace();
            }
        } else {
            System.out.println("권한이 없습니다.");
        }

    }

    public void activityAccept(VolunteerDTO volunteerDTO) { //봉사 승인
        VolunteerDTO volunteer = volunteerDTO;
        protocolHeader = new ProtocolHeader(ProtocolType.ACCEPTANCE, ProtocolCode.ACCEPT, ProtocolKind.MANAGER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(volunteer);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        String result = "";
        try {
            result = (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void activityReject(VolunteerDTO volunteerDTO) { // 봉사 거절
        VolunteerDTO volunteer = volunteerDTO;
        protocolHeader = new ProtocolHeader(ProtocolType.ACCEPTANCE, ProtocolCode.REJECT, ProtocolKind.MANAGER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(volunteer);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        String result = "";
        try {
            result = (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void registerServiceActivity(ServiceInfoDTO serviceInfoDTO) { //봉사 활동 신청
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUser_UserPK(userDTO.getUserPK());
        volunteerDTO.setProcessingResult("승인 전");
        volunteerDTO.setServiceInfo_ServiceInfoPK(serviceInfoDTO.getServiceInfoPK());
        protocolHeader = new ProtocolHeader(ProtocolType.REGISTER, ProtocolCode.REGISTER_SERVICE_ACTIVITY, ProtocolKind.VOLUNTEER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(volunteerDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        String result = "";
        try {
            result = (String) ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void mannerTemperature(UserDTO userDTO, int manner) {// 매너온도 입력 -> updateUser
        //클릭 시 해당 유저 정보를 가져옴
        userDTO.setMannerTemperature(manner);
        protocolHeader = new ProtocolHeader(ProtocolType.REGISTER, ProtocolCode.MANNER_TEMPERATURE, ProtocolKind.MANAGER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(userDTO);
            oos.flush();
        }catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        String result = "";
        try {
            result = (String) ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void fieldServiceSelect(ServiceInfoDTO serviceInfoDTO) {
        ServiceInfoDTO service = serviceInfoDTO;
        String srvcCLCode = serviceInfoDTO.getSrvcCLCode(), srvcCSCode = serviceInfoDTO.getSrvcCSCode(),
                progrmSj = serviceInfoDTO.getProgrmSj(), MnnstNm = serviceInfoDTO.getMnnstNm();

        int sidocd = serviceInfoDTO.getSidoCd();

        Date progrmBgnde = serviceInfoDTO.getProgrmBgnde(), progrmEndde = serviceInfoDTO.getProgrmEndde();

        //각각의 변수에 매핑을 필터링란에 있는 것들과 해야할 듯.
        if (srvcCLCode != null) {
            serviceInfoDTO.setSrvcCLCode(srvcCLCode);
        }
        if (srvcCSCode != null) {
            serviceInfoDTO.setSrvcCSCode(srvcCSCode);
        }
        if (progrmSj != null) {
            serviceInfoDTO.setProgrmSj(progrmSj);
        }
        if (MnnstNm != null) {
            serviceInfoDTO.setMnnstNm(MnnstNm);
        }
        if (sidocd != 0) {
            serviceInfoDTO.setSidoCd(sidocd);
        }
        if (progrmBgnde != null) {
            serviceInfoDTO.setProgrmBgnde(progrmBgnde);
        }
        if (progrmEndde != null) {
            serviceInfoDTO.setProgrmEndde(progrmEndde);
        }
        protocolHeader = new ProtocolHeader(ProtocolType.FILTERING, ProtocolCode.FILTER, ProtocolKind.VOLUNTEER);
        try {
            oos.writeObject(protocolHeader);
            oos.writeObject(serviceInfoDTO);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        List<ServiceInfoDTO> list = null;

        try {
            list = (List<ServiceInfoDTO>) ois.readObject();
            for(int i=0; i<list.size(); i++) {
                System.out.println(list.get(i).toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

}

