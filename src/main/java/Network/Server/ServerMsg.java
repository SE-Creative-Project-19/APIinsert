package Network.Server;

import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolHeader;
import Network.Protocol.ProtocolKind;
import Network.Protocol.ProtocolType;
import persistence.MyBatisConnectionFactory;
import persistence.dao.ServiceInfoDAO;
import persistence.dao.UserDAO;
import persistence.dao.VolunteerDAO;
import persistence.dto.ServiceInfoDTO;
import persistence.dto.UserDTO;
import persistence.dto.VolunteerDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerMsg { //client to server
    private byte type, code, kind;
    private ObjectInputStream objectInput;

    private ProtocolHeader protocolHeader = null;

    public ServerMsg(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        protocolHeader = (ProtocolHeader) ois.readObject(); //헤더 객체만큼 빼오고
        type = protocolHeader.getType();
        code = protocolHeader.getCode();
        kind = protocolHeader.getKind();

        objectInput = ois; //바디 내용 객체는 객체 스트림에 저장
    }

    public void run(ObjectOutputStream oos) throws IOException {
        if (type == ProtocolType.SIGNUP) {// TODO 회원가입
            if (code == ProtocolCode.REGISTER_INFO) {// 회원 가입 정보 등록

                if (kind == ProtocolKind.VOLUNTEER) {// 봉사자
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());

                        int result = userDAO.insertUser(userDTO);
                        String resultStr = "";
                        if (result == 1) {
                            resultStr = "중복된 아이디 입니다.";
                        } else if (result == 2) {
                            resultStr = "중복된 전화번호 입니다.";
                        } else {
                            resultStr = "회원가입 완료";
                        }
                        oos.writeObject(resultStr);
                        oos.flush();
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                } else if (kind == ProtocolKind.MANAGER) {// 담당자
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());

                        int result = userDAO.insertManager(userDTO, userDTO.getFacility());
                        String resultStr = "";
                        if (result == 1) {
                            resultStr = "중복된 아이디 입니다.";
                        } else if (result == 2) {
                            resultStr = "중복된 전화번호 입니다.";
                        } else {
                            resultStr = "회원가입 완료";
                        }
                        oos.writeObject(resultStr);
                        oos.flush();
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            } else if (code == ProtocolCode.CHECKID) {
                if (kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        boolean result = userDAO.isIdDuplicate(userDTO.getID());
                        String resultStr = "";
                        if (result) {
                            resultStr = "중복된 아이디가 있습니다.";
                            oos.writeObject(resultStr);
                            oos.flush();
                        } else {
                            resultStr = "사용 가능한 아이디 입니다.";
                            oos.writeObject(resultStr);
                            oos.flush();
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }
            }

        } else if (type == ProtocolType.LOGIN) {// TODO 로그인
            if (code == ProtocolCode.LOGIN_INFO) {// 회원 로그인 정보

                if (kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        userDTO = userDAO.loginUser(userDTO.getID(), userDTO.getPW());

                        oos.writeObject(userDTO);
                        oos.flush();

                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }
            }
        } else if (type == ProtocolType.FIND_MY_INFO) {// ID,PW 찾기
            if (code == ProtocolCode.FIND_ID) { // 아이디 찾기

                if (kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        String result = userDAO.findUserId(userDTO.getName(), userDTO.getPhoneNumber());

                        oos.writeObject(result);
                        oos.flush();

                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            } else if (code == ProtocolCode.FIND_PW) { // 패스워드 찾기

                if (kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        boolean result = userDAO.updatePassword(userDTO);

                        oos.writeObject(result);
                        oos.flush();

                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            }
        } else if (type == ProtocolType.MYPAGE) {// TODO 마이페이지
            if (code == ProtocolCode.SHOW_MY_INFO) {// 회원 정보 보여주기

                if (kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        List<UserDTO> list = new ArrayList<>();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        list = userDAO.getUser(userDTO.getID());
                        oos.writeObject(list);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            } else if (code == ProtocolCode.UPDATE_MY_INFO) {// 회원 정보 수정

                if (kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        userDAO.updateUser(userDTO);
                        String result = "회원정보수정 완료";
                        oos.writeObject(result);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            }
        } else if (type == ProtocolType.INQUIRY) {// TODO 조회
            if (code == ProtocolCode.SERVICE_LIST_INQUIRY) {// 봉사활동 목록 조회

                if (kind == ProtocolKind.VOLUNTEER) {// 신청할 수 있는 봉사활동 리스트 출력
                    try {
                        int page = (int) objectInput.readObject();
                        ServiceInfoDAO serviceInfoDAO = new ServiceInfoDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        List<ServiceInfoDTO> list = new ArrayList<>();
                        list = serviceInfoDAO.getServiceInfoList(page);
                        oos.writeObject(list);
                        oos.flush();

                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }

                }

            } else if (code == ProtocolCode.MY_PARTICIPATE_IN_LIST) {// 내가 참여한 봉사활동

                if (kind == ProtocolKind.VOLUNTEER) {
                    List<Map<String, Object>> first = null;
                    List<Map<String, Object>> second = null;
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        VolunteerDAO volunteerDAO = new VolunteerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        first = volunteerDAO.getVolunteerService(userDTO.getFacility());
                        second = volunteerDAO.getVolunteerServiceHistory(userDTO.getFacility());

                        oos.writeObject(first);
                        oos.writeObject(second);
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            } else if (code == ProtocolCode.PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST) {// 봉사활동 참여한 봉사자 리스트[신청]

                if (kind == ProtocolKind.MANAGER) {
                    List<VolunteerDTO> list = null;
                    List<UserDTO> userDTOS = null;
                    try {
                        ServiceInfoDTO serviceInfoDTO = (ServiceInfoDTO) objectInput.readObject();
                        VolunteerDAO volunteerDAO = new VolunteerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        list = volunteerDAO.getVolunteerApplicant(serviceInfoDTO.getServiceInfoPK());
                        userDTOS = userDAO.getUsersByPk(list);

                        oos.writeObject(list);
                        oos.writeObject(userDTOS);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            } else if (code == ProtocolCode.PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST_Result) {// 봉사활동 참여한 봉사자 리스트[결과]

                if (kind == ProtocolKind.MANAGER) {
                    List<VolunteerDTO> list = null;
                    List<UserDTO> userDTOS = null;
                    try {
                        ServiceInfoDTO serviceInfoDTO = (ServiceInfoDTO) objectInput.readObject();
                        VolunteerDAO volunteerDAO = new VolunteerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        list = volunteerDAO.getVolunteerDone(serviceInfoDTO.getServiceInfoPK());
                        userDTOS = userDAO.getUsersByPk(list);

                        oos.writeObject(list);
                        oos.writeObject(userDTOS);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }
            } else if (code == ProtocolCode.MY_ORGANIZATION_ACTIVITY_LIST) {// 본인 소속 기관의 봉사활동 리스트
                if (kind == ProtocolKind.MANAGER) {

                }
            }
        } else if (type == ProtocolType.ACCEPTANCE) {// TODO 승인 여부
            if (code == ProtocolCode.ACCEPT) { // 봉사활동 참여에 승인

                if (kind == ProtocolKind.MANAGER) {
                    try {
                        VolunteerDTO volunteerDTO = (VolunteerDTO) objectInput.readObject();
                        volunteerDTO.setProcessingResult("별점 미등록");
                        VolunteerDAO volunteerDAO = new VolunteerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        volunteerDAO.updateVolunteer(volunteerDTO);
                        String result = "승인 완료";

                        oos.writeObject(result);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            } else if (code == ProtocolCode.REJECT) {// 봉사활동 참여에 거절

                if (kind == ProtocolKind.MANAGER) {
                    try {
                        VolunteerDTO volunteerDTO = (VolunteerDTO) objectInput.readObject();
                        volunteerDTO.setProcessingResult("거절");
                        VolunteerDAO volunteerDAO = new VolunteerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        volunteerDAO.updateVolunteer(volunteerDTO);
                        String result = "승인 거절";

                        oos.writeObject(result);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            }
        } else if (type == ProtocolType.REGISTER) {// TODO 등록
            if (code == ProtocolCode.REGISTER_SERVICE_ACTIVITY) {

                if (kind == ProtocolKind.VOLUNTEER) {
                    try {
                        VolunteerDTO volunteerDTO = (VolunteerDTO) objectInput.readObject();
                        VolunteerDAO volunteerDAO = new VolunteerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        volunteerDAO.insertVolunteer(volunteerDTO);

                        String result = "등록 완료";
                        oos.writeObject(result);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }
            } else if (code == ProtocolCode.MANNER_TEMPERATURE) {

                if (kind == ProtocolKind.MANAGER) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        userDAO.updateUser(userDTO);

                        String result = "매너 온도 업데이트 완료";
                        oos.writeObject(result);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            }
        } else if (type == ProtocolType.FILTERING) {// TODO 필터링
            if (code == ProtocolCode.FILTER) {//필터링

                if (kind == ProtocolKind.VOLUNTEER) {
                    List<ServiceInfoDTO> list = null;
                    try {
                        ServiceInfoDTO serviceInfoDTO = (ServiceInfoDTO) objectInput.readObject();
                        ServiceInfoDAO serviceInfoDAO = new ServiceInfoDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        list = serviceInfoDAO.getServiceInfoByFilter(serviceInfoDTO, 1);

                        oos.writeObject(list);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("매칭되는 프로토콜 type이 없습니다.");
        }
    }
}

