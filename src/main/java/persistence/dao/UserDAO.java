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
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userDTO;
    }   // 로그인

    public List<UserDTO> getUser(String id) {
        List<UserDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList("mapper.UserMapper.getUser", id);
        } finally {
            session.close();
        }
        return list;
    }   // 회원정보조회

    public boolean isIdDuplicate(String id) {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            int count = session.selectOne("mapper.UserMapper.checkDuplicateId", id);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isPhoneNumberDuplicate(String phoneNumber) {
        SqlSession session = sqlSessionFactory.openSession();
        try  {
            int count = session.selectOne("mapper.UserMapper.checkDuplicatePhoneNumber", phoneNumber);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public void insertUser(UserDTO userDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int duplicateIdCount = session.selectOne("mapper.UserMapper.checkDuplicateId", userDTO.getID());
            if (duplicateIdCount > 0) {
                System.out.println("중복된 아이디 입니다.");
                return;
            }

            int duplicatePhoneNumberCount = session.selectOne("mapper.UserMapper.checkDuplicatePhoneNumber", userDTO.getPhoneNumber());
            if (duplicatePhoneNumberCount > 0) {
                System.out.println("중복된 전화번호 입니다.");
                return;
            }
            session.insert("mapper.UserMapper.insertUser", userDTO);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertManager(UserDTO userDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int duplicateIdCount = session.selectOne("mapper.UserMapper.checkDuplicateId", userDTO.getID());
            if (duplicateIdCount > 0) {
                // 아이디 중복 처리
                return;
            }

            int duplicatePhoneNumberCount = session.selectOne("mapper.UserMapper.checkDuplicatePhoneNumber", userDTO.getPhoneNumber());
            if (duplicatePhoneNumberCount > 0) {
                // 전화번호 중복 처리
                return;
            }
            session.insert("mapper.UserMapper.insertManager", userDTO);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(UserDTO userDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("mapper.UserMapper.updateUser", userDTO);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }   // 개인정보수정

    public String findUserId(String name, String phoneNumber) {
        SqlSession session = sqlSessionFactory.openSession();
        String userId = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("phoneNumber", phoneNumber);
            userId = session.selectOne("mapper.UserMapper.getId", params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userId;
    }   // ID 찾기 테스트 해봐야함

    public String findUserPassword(String name, String id, String phoneNumber) {
        SqlSession session = sqlSessionFactory.openSession();
        String password = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", name);
            params.put("id", id);
            params.put("phoneNumber", phoneNumber);
            password = session.selectOne("mapper.UserMapper.findUserPassword", params);
        } finally {
            session.close();
        }
        return password;
    }   // 비밀번호 재설정 테스트 해봐야함

    public List<UserDTO> getUsersByPk(List<VolunteerDTO> volunteerDTOS, int pageNo) {
        List<UserDTO> list = null;
        if(volunteerDTOS.size() == 0) return null;
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