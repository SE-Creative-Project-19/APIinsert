package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.ServiceInfoDTO;
import persistence.dto.UserDTO;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.VolunteerDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    private final SqlSessionFactory sqlSessionFactory;

    public UserDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public UserDTO loginUser(String id, String pw) {
        SqlSession session = sqlSessionFactory.openSession();
        UserDTO userDTO = null;
        try {
            Map<String, String> loginInfo = new HashMap<>();
            loginInfo.put("id", id);
            loginInfo.put("pw", pw);
            userDTO = session.selectOne("mapper.UserMapper.loginUser", loginInfo);
        } finally {
            session.close();
        }
        return userDTO;
    }   // 로그인

    public List<UserDTO> getUser(String id) { //TODO id를 입력했느데 왜 DTO 리스트..? DTO 여야 하는 거 아님?
        List<UserDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList("mapper.UserMapper.getUser", id);
        } finally {
            session.close();
        }
        return list;
    }   // 회원정보조회

    public void insertUser(UserDTO userDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            // id 중복 검사
            String existingId = session.selectOne("mapper.UserMapper.checkDuplicateId", userDTO.getID());
            if (existingId != null) {
                throw new IllegalArgumentException("중복된 아이디입니다.");
            }

            // 전화번호 중복 검사
            String existingPhoneNumber = session.selectOne("mapper.UserMapper.checkDuplicatePhoneNumber", userDTO.getPhoneNumber());
            if (existingPhoneNumber != null) {
                throw new IllegalArgumentException("중복된 전화번호입니다.");
            }

            // 회원 정보 삽입
            session.insert("mapper.UserMapper.insertUser", userDTO);
            session.commit();
        } finally {
            session.close();
        }
    }   // 회원가입


    public void updateUser(UserDTO userDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("mapper.UserMapper.updateUser", userDTO);
            session.commit();
        } finally {
            session.close();
        }
    }   // 개인정보수정

    public String findUserId(String phoneNumber) {
        SqlSession session = sqlSessionFactory.openSession();
        String userId = null;
        try {
            userId = session.selectOne("mapper.UserMapper.findUserId", phoneNumber);
        } finally {
            session.close();
        }
        return userId;
    }   // ID 찾기

    public String findUserPassword(String id, String phoneNumber) {
        SqlSession session = sqlSessionFactory.openSession();
        String password = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", id);
            params.put("phoneNumber", phoneNumber);
            password = session.selectOne("mapper.UserMapper.findUserPassword", params);
        } finally {
            session.close();
        }
        return password;
    }   // 비밀번호 재설정


    public UserDTO getUserByPK(int userPK) {//todo UserPK를 가지고 해당하는 user를 return
        SqlSession session = sqlSessionFactory.openSession();
        UserDTO userDTO = null;
        try {
            userDTO = session.selectOne("mapper.UserMapper.getUserByPK", userPK);
        } finally {
            session.close();
        }
        return userDTO;
    }

    public List<UserDTO> getUsersByPk(List<VolunteerDTO> volunteerDTOS, int pageNo) { //TODO 봉사자 리스트를 바탕으로 해당 유저DTO 리스트를  return
        List<UserDTO> list = null;
        int pageSize = 10;
        int offset = (pageNo - 1) * pageSize;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("pageSize", pageSize);
            parameterMap.put("offset", offset);
            parameterMap.put("volunteerDTOS", volunteerDTOS);
            list = session.selectList("mapper.UserMapper.getUsersByPk", parameterMap);
        } finally {
            session.close();
        }
        return list;
    }
}