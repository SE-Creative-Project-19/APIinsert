package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.VolunteerDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolunteerDAO {
    private final SqlSessionFactory sqlSessionFactory;
    public VolunteerDAO( SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<VolunteerDTO> getVolunteer() {
        List<VolunteerDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList("mapper.VolunteerMapper.getVolunteer");
        } finally {
            session.close();
        }
        return list;
    }   // 신청 내역 조회

    public List<VolunteerDTO> getVolunteerFilter(VolunteerDTO volunteerDTO, String processingResult) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("processingResult", processingResult);
            parameterMap.put("VolunteerDTO", volunteerDTO);

            List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteer", parameterMap);
            return volunteerDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<VolunteerDTO> getVolunteerApplicant(String facility) { //TODO 기관이름으 바탕으로 해당하는 servieInfoPk를 조인한 후 volunteer와 비교해서 가져옵니다.
        try (SqlSession session = sqlSessionFactory.openSession()) {

            List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteerApplicant", facility);
            return volunteerDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void updateVolunteer(VolunteerDTO volunteerDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("mapper.VolunteerMapper.updateVolunteer", volunteerDTO);
            session.commit();
        } finally {
            session.close();
        }
    }

    public List<VolunteerDTO> getVolunteerDone(String facility) {
        try (SqlSession session = sqlSessionFactory.openSession()) {

            List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteerDone", facility);
            return volunteerDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

