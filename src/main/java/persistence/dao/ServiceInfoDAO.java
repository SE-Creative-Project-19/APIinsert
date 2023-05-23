package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.ServiceInfoDTO;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceInfoDAO {
    private SqlSession sqlSession;
    private final SqlSessionFactory sqlSessionFactory;
    public ServiceInfoDAO(SqlSession sqlSession, SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = sqlSession;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    //수정 필요
    public List<ServiceInfoDTO> getServiceInfoList(int pageNo) {
        int pageSize = 10;
        int offset = (pageNo - 1) * pageSize;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("pageSize", pageSize);
            parameterMap.put("offset", offset);

            List<ServiceInfoDTO> serviceInfoList = session.selectList("mapper.ServiceInfoMapper.getServiceInfoList", parameterMap);
            return serviceInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<ServiceInfoDTO> getServiceInfoByFilter(ServiceInfoDTO serviceInfoDTO){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<ServiceInfoDTO> serviceInfoList = session.selectList("mapper.ServiceInfoMapper.getServiceInfoByFilter", serviceInfoDTO);
            return serviceInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertServiceInfo(ServiceInfoDTO serviceInfoDTO){
        SqlSession session = sqlSessionFactory.openSession();
        try{
            session.insert("mapper.ServiceInfoMapper.insertServiceInfo",serviceInfoDTO);
            session.commit();
        } finally {
            session.close();
        }
    }
    public List<ServiceInfoDTO> getAllServiceInfo() {
        List<ServiceInfoDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            list = session.selectList("mapper.ServiceInfoMapper.getAllServiceInfo");
        } finally {
            session.close();
        }
        return list;
    }
}
