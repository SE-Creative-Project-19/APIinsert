package service;
import persistence.dao.VolunteerDAO;
import persistence.dto.UserDTO;
import persistence.dto.VolunteerDTO;
import java.util.List;

public class VolunteerService{
    private VolunteerDAO dao;
    private UserService userService;
    public VolunteerService(VolunteerDAO dao,UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

//    public List<VolunteerDTO> getVolunteer() {return dao.getVolunteer();}

    public List<VolunteerDTO> getVolunteerFilter(VolunteerDTO volunteerDTO, String processingResult){// 봉사 신청자 명단을 바탕으로 해당 유저의 정보들을 리스트로 반환
        return dao.getVolunteerFilter(volunteerDTO, processingResult);
    }

    public List<UserDTO> getVolunteerListByServicePK(List<VolunteerDTO> volunteerDTOS){ //TODO 봉사 신청 리스트에서  신청자에 대한  userDTO를 리스트  return
       List<UserDTO> userDTOS = userService.getUsersByPK(volunteerDTOS);
       return userDTOS;
    }
    public List<VolunteerDTO> getVolunteerApplicant(int serviceInfoPK) {//TODO 담당자가 해당 기관의 승인이 필요한 봉사 신청 리스트를 return
        return dao.getVolunteerApplicant(serviceInfoPK);

    }

    public void updateVolunteer(VolunteerDTO volunteerDTO) {// 봉사처리 상태를 수정하는 용도로 사용합니다.
         dao.updateVolunteer(volunteerDTO);
    }


    public List<VolunteerDTO> getVolunteerDone(int serviceInfoPK) { //TODO 봉사기간이 지나서 봉사는 완료되었으나 별점을 등록하지 않은 봉사 신청 리스트를  return
        return dao.getVolunteerDone(serviceInfoPK);

    }
}
