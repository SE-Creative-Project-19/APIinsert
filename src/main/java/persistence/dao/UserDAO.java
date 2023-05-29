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
    public int insertUser(UserDTO userDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int duplicateIdCount = session.selectOne("mapper.UserMapper.checkDuplicateId", userDTO.getID());
            if (duplicateIdCount > 0) {
                return 1;
            }

            int duplicatePhoneNumberCount = session.selectOne("mapper.UserMapper.checkDuplicatePhoneNumber", userDTO.getPhoneNumber());
            if (duplicatePhoneNumberCount > 0) {
                return 2;
            }
            session.insert("mapper.UserMapper.insertUser", userDTO);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int insertManager(UserDTO userDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int duplicateIdCount = session.selectOne("mapper.UserMapper.checkDuplicateId", userDTO.getID());
            if (duplicateIdCount > 0) {
                return 1;
            }

            int duplicatePhoneNumberCount = session.selectOne("mapper.UserMapper.checkDuplicatePhoneNumber", userDTO.getPhoneNumber());
            if (duplicatePhoneNumberCount > 0) {
                return 2;
            }
            session.insert("mapper.UserMapper.insertManager", userDTO);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateUser(UserDTO userDTO) { 
        //TODO 상철 대상의 정보를 수정해야하는데 대상 지정이 안됨
        // 그래서 오류 없이는 되는데 실제 DB에서 정보가 안바뀜 -> ID는 키로 두기 때문에 바꾸지 않도록
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
    }   // ID 찾기

    public boolean findUserPassword(String name, String id, String phoneNumber) {
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
        return true; //TODO 정상철 수정해라~
    }   // 비밀번호 재설정

    public List<UserDTO> getUsersByPk(List<VolunteerDTO> volunteerDTOS, int pageNo) { //TODO 봉사자 리스트를 바탕으로 해당 유저DTO 리스트를  return
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