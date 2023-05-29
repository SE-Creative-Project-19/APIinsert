package service;

import persistence.dao.ServiceInfoDAO;
import persistence.dao.UserDAO;
import persistence.dao.VolunteerDAO;
import persistence.dto.ServiceInfoDTO;

import java.util.List;

public class ServiceInfoService {
    private ServiceInfoDAO dao;
    private UserDAO userDAO;
    private VolunteerDAO volunteerDAO;

    public ServiceInfoService(ServiceInfoDAO dao, VolunteerDAO volunteerDAO) { // VolunteerDAO를 통해서 시간이 지난 봉사활동들을 완료해야 해서 추가하였습니다.
        this.dao = dao;
        this.volunteerDAO = volunteerDAO;
    }

    public List<ServiceInfoDTO> getServiceInfoList(int pageNo) {
        return dao.getServiceInfoList(pageNo);
    }
    public List<ServiceInfoDTO> getAllServiceInfo() {
        return dao.getAllServiceInfo();
    }
    public List<ServiceInfoDTO> getServiceInfoByFilter(ServiceInfoDTO serviceInfoDTO, int pageNo){return dao.getServiceInfoByFilter(serviceInfoDTO,pageNo);}

    public void updateServiceInfoByTime(){
        dao.updateServiceInfoByTime();// 12시를 기준으로 봉사 모집 기간이 완료된 serviceInfo를 변경합니다.
        volunteerDAO.updateVolunteerByTime();// 12시를 기준으로 봉사 활동 기간이 완료된 volunteer를 별점 미등록 상태로 변경합니다.
    }
    public void insertServiceInfo(ServiceInfoDTO serviceInfoDTO) {
        dao.insertServiceInfo(serviceInfoDTO);
    }
}