package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.VolunteerDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.*;

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

    }

    public List<VolunteerDTO> getVolunteerApplicant(String facility) {//TODO 기관이름으 바탕으로 해당하는 servieInfoPk를 조인한 후 volunteer와 비교해서 가져옵니다.
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("facility", facility);

            List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteerApplicant", parameterMap);
            return volunteerDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void updateVolunteer(VolunteerDTO volunteerDTO) {//TODO 봉사 처리 결과를 수정합니다.
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("mapper.VolunteerMapper.updateVolunteer", volunteerDTO);
            session.commit();
        } finally {
            session.close();
        }
    }


    public List<VolunteerDTO> getVolunteerDone(String facility) { //TODO 봉사 완료 후 별점을 등록하지 못한 봉사 신청 리스트를 return
        try(SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("facility", facility);
            List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteerDone", facility);
            return volunteerDTOList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void updateVolunteerByTime() {//TODO 12시 업데이트 했을 때 시간이 지나면서 봉사 활동 기간이 끝난 활동에 대해서 별점 미등록 상태로 수정합니다.
        SqlSession session = sqlSessionFactory.openSession();
        Date currentDate = new Date();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("currentDate", currentDate);
        try {
            session.update("mapper.VolunteerMapper.updateVolunteerByTime",parameterMap);
            session.commit();
        } finally {
            session.close();
        }
    }
}

