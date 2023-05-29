package Network.Server;

import Network.Protocol.ProtocolCode;
import Network.Protocol.ProtocolHeader;
import Network.Protocol.ProtocolKind;
import Network.Protocol.ProtocolType;
import persistence.MyBatisConnectionFactory;
import persistence.dao.ServiceInfoDAO;
import persistence.dao.UserDAO;
import persistence.dto.ServiceInfoDTO;
import persistence.dto.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
                        if(result == 1) {
                            resultStr = "중복된 아이디 입니다.";
                        }else if(result == 2) {
                            resultStr = "중복된 전화번호 입니다.";
                        }else {
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

                        int result = userDAO.insertManager(userDTO);
                        String resultStr = "";
                        if(result == 1) {
                            resultStr = "중복된 아이디 입니다.";
                        }else if(result == 2) {
                            resultStr = "중복된 전화번호 입니다.";
                        }else {
                            resultStr = "회원가입 완료";
                        }
                        oos.writeObject(resultStr);
                        oos.flush();
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            }else if (code == ProtocolCode.CHECKID) {
                if(kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        boolean result = userDAO.isIdDuplicate(userDTO.getID());
                        String resultStr = "";
                        if(result) {
                            resultStr = "중복된 아이디가 있습니다.";
                            oos.writeObject(resultStr);
                            oos.flush();
                        }else {
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

                if (kind == ProtocolKind.VOLUNTEER) {// 봉사자
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
                } else if (kind == ProtocolKind.MANAGER) { //담당자
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
                        String result = userDAO.findUserId(userDTO.getName(),userDTO.getPhoneNumber());

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
                        boolean result = userDAO.findUserPassword(userDTO.getName(),userDTO.getID(), userDTO.getPhoneNumber());

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
                    //DAO 수정 필요한듯
                }

            } else if (code == ProtocolCode.UPDATE_MY_INFO) {// 회원 정보 수정

                if (kind == ProtocolKind.COMMON) {
                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        userDAO.updateUser(userDTO);
                        String result = "회원정보수정";
                        oos.writeObject(result);
                        oos.flush();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                }

            } else if (code == ProtocolCode.SET_NEW_PW) {// 새로운 비밀번호로 재설정

                if (kind == ProtocolKind.COMMON) {

                    try {
                        UserDTO userDTO = (UserDTO) objectInput.readObject();
                        UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
                        userDAO.updateUser(userDTO);

                        String result = "비밀번호 재설정";
                        oos.writeObject(result);
                        oos.flush();

                    } catch (ClassNotFoundException e) {
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

                } else if (kind == ProtocolKind.MANAGER) { //자신이 속한 기관의 봉사 활동  리스트 출력

                }
            } else if (code == ProtocolCode.SERVICE_CONTENT_INQUIRY) {// 봉사활동 선택 시 세부 내용

                if (kind == ProtocolKind.VOLUNTEER) {

                }

            } else if (code == ProtocolCode.MY_PARTICIPATE_IN_LIST) {// 내가 참여한 봉사활동

                if (kind == ProtocolKind.VOLUNTEER) {

                }

            } else if (code == ProtocolCode.PARTICIPATE_IN_SERVICE_VOLUNTEER_LIST) {// 봉사활동 참여한 봉사자 리스트

                if (kind == ProtocolKind.MANAGER) {

                }

            } else if (code == ProtocolCode.MY_ORGANIZATION_ACTIVITY_LIST) {// 본인 소속 기관의 봉사활동 리스트
                if (kind == ProtocolKind.MANAGER) {

                }
            }
        } else if (type == ProtocolType.ACCEPTANCE) {// TODO 승인 여부
            if (code == ProtocolCode.ACCEPT) { // 봉사활동 참여에 승인

                if (kind == ProtocolKind.MANAGER) {

                }

            } else if (code == ProtocolCode.REJECT) {// 봉사활동 참여에 거절

                if (kind == ProtocolKind.MANAGER) {

                }

            }
        } else if (type == ProtocolType.REGISTER) {// TODO 등록
            if (code == ProtocolCode.REGISTER_SERVICE_ACTIVITY) {

                if (kind == ProtocolKind.VOLUNTEER) {

                }
            } else if (code == ProtocolCode.MANNER_TEMPERATURE) {

                if (kind == ProtocolKind.MANAGER) {

                }

            }
        } else if (type == ProtocolType.FILTERING) {// TODO 필터링
            if (code == ProtocolCode.FIELD_SERVICE_SELECT) {// 분야별 필터링

                if (kind == ProtocolKind.VOLUNTEER) {

                }
            } else if (code == ProtocolCode.PERIOD_SERVICE_SELECT) {// 기간별 필터링

                if (kind == ProtocolKind.VOLUNTEER) {

                }
            } else if (code == ProtocolCode.SERVICE_AREA_SELECT) {// 지역별 필터링

                if (kind == ProtocolKind.VOLUNTEER) {

                }
            } else if (code == ProtocolCode.SERVICE_NAME_SELECT) {// 분야명 필터링

                if (kind == ProtocolKind.VOLUNTEER) {

                }
            }
        } else if (type == ProtocolType.CHATTING) {// TODO 채팅

        } else {
            System.out.println("매칭되는 프로토콜 type이 없습니다.");
        }
    }
}
