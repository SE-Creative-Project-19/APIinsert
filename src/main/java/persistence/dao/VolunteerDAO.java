package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.VolunteerDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolunteerDAO {
    private SqlSession sqlSession;
    private final SqlSessionFactory sqlSessionFactory;

    public VolunteerDAO(SqlSession sqlSession, SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = sqlSession;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<VolunteerDTO> getVolunteer() {
        List<VolunteerDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            list = session.selectList("mapper.VolunteerMapper.getVolunteer");
        } finally {
            session.close();
        }
        return list;
    }   // 신청 내역 조회

    public List<VolunteerDTO> getVolunteerFilter(VolunteerDTO volunteerDTO, String processingResult) {
    try(SqlSession session = sqlSessionFactory.openSession()) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("processingResult", processingResult);
        parameterMap.put("VolunteerDTO", volunteerDTO);

        List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteer", parameterMap);
        return volunteerDTOList;
    } catch (Exception e){
        e.printStackTrace();
        return null;
    }
    }   // 이전 봉사내역 조회
}

