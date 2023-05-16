package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.ServiceInfoDTO;
import org.apache.ibatis.session.SqlSessionFactory;

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

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("pageSize", pageSize);
        parameterMap.put("offset", offset);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<Map<String, Object>> resultMap = session.selectList("mapper.ServiceInfoMapper.getServiceInfoList", parameterMap);
            List<ServiceInfoDTO> serviceInfoList = new ArrayList<>();

            for (Map<String, Object> result : resultMap) {
                ServiceInfoDTO serviceInfoDTO = new ServiceInfoDTO();
                // Map의 값을 ServiceInfoDTO에 매핑하여 설정
                serviceInfoDTO.setServiceInfoPK((Integer) result.get("serviceInfoPK"));
                serviceInfoDTO.setActWkdy((String) result.get("actWkdy"));
                // 나머지 필드들에 대해서도 매핑 작업 수행

                serviceInfoList.add(serviceInfoDTO);
            }

            return serviceInfoList;
        } catch (Exception e) {
            // 예외 처리
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
