package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.ServiceInfoDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.*;

public class ServiceInfoDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public ServiceInfoDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<ServiceInfoDTO> getServiceInfoList(int pageNo) { // 전체 리스트 중에서 원하는 페이지의 리스트를 10개씩 출력
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
    public List<ServiceInfoDTO> getServiceInfoByFilter(ServiceInfoDTO serviceInfoDTO, int pageNo) { // dto를 기준으로 필터링
        int pageSize = 10;
        int offset = (pageNo - 1) * pageSize;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("pageSize", pageSize);
            parameterMap.put("offset", offset);
            parameterMap.put("serviceInfoDTO", serviceInfoDTO);
            List<ServiceInfoDTO> serviceInfoList = session.selectList("mapper.ServiceInfoMapper.getServiceInfoByFilter", parameterMap);
            return serviceInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertServiceInfo(ServiceInfoDTO serviceInfoDTO){//  api에서 받아온 활동 정보를 insert할 때 사용합니다
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

    public void updateServiceInfoByTime() {
        SqlSession session = sqlSessionFactory.openSession();
        Date currentDate = new Date();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("currentDate", currentDate);
        try {
            session.update("mapper.ServiceInfoMapper.updateServiceInfoByTime",parameterMap);
            session.commit();
        } finally {
            session.close();
        }

    }
}