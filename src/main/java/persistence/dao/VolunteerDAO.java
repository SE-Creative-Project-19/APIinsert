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

    public List<Map<String, Object>> getVolunteerService(String id) {
        List<Map<String, Object>> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList("mapper.VolunteerMapper.getVolunteerService", id);
        } finally {
            session.close();
        }
        return list;
    } // 봉사 신청 내역 조회

    public List<Map<String, Object>> getVolunteerServiceHistory(String id) {
        List<Map<String, Object>> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            list = session.selectList("mapper.VolunteerMapper.getVolunteerServiceHistory", id);
        } finally {
            session.close();
        }
        return list;
    } // 봉사 활동 내역 조회

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

    public List<VolunteerDTO> getVolunteerApplicant(int serviceInfoPK) {//TODO serviceinfo_pk를 가지고 해당하는 volunteerDTO list를 출력합니다.

        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteerApplicant", serviceInfoPK);
            return volunteerDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void updateVolunteer(VolunteerDTO volunteerDTO) {// 봉사 처리 결과를 수정합니다.  다른용도로도 가능
        SqlSession session = sqlSessionFactory.openSession();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("volunteerPK", volunteerDTO.getVolunteerPK());
        parameterMap.put("processingResult", volunteerDTO.getProcessingResult());
        parameterMap.put("User_UserPK", volunteerDTO.getUser_UserPK());
        parameterMap.put("ServiceInfo_ServiceInfoPK", volunteerDTO.getServiceInfo_ServiceInfoPK());

        try {
            session.update("mapper.VolunteerMapper.updateVolunteer", volunteerDTO);
            session.commit();
        } finally {
            session.close();
        }
    }



    public List<VolunteerDTO> getVolunteerDone(int serviceInfoPK) { //TODO 봉사 완료 후 별점을 등록하지 못한 봉사 신청 리스트를 return

        try(SqlSession session = sqlSessionFactory.openSession()) {

            List<VolunteerDTO> volunteerDTOList = session.selectList("mapper.VolunteerMapper.getVolunteerDone", serviceInfoPK);
            return volunteerDTOList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void updateVolunteerByTime() {// 12시 업데이트 했을 때 시간이 지나면서 봉사 활동 기간이 끝난 활동에 대해서 별점 미등록 상태로 수정합니다.
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
    public void insertVolunteer(VolunteerDTO volunteerDTO) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int count = session.selectOne("mapper.VolunteerMapper.countVolunteerByUserPKAndServiceInfoPK", volunteerDTO);
            if (count > 0) {
                // 중복 처리 로직
                return;
            }

            session.insert("mapper.VolunteerMapper.insertVolunteer", volunteerDTO);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    } // 봉사 신청

}

