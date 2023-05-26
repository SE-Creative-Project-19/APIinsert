package service;
import persistence.dao.VolunteerDAO;
import persistence.dto.UserDTO;
import persistence.dto.VolunteerDTO;
import java.util.List;

public class VolunteerService{
    private VolunteerDAO dao;

    public VolunteerService(VolunteerDAO dao) {this.dao = dao;}

    public List<VolunteerDTO> getVolunteer() {return dao.getVolunteer();}

    public List<VolunteerDTO> getVolunteerFilter(VolunteerDTO volunteerDTO, String processingResult)
    {return dao.getVolunteerFilter(volunteerDTO, processingResult);}

    public List<VolunteerDTO> getVolunteerApplicant(String facility) {
        return dao.getVolunteerApplicant(facility);
    }

    public void updateVolunteer(VolunteerDTO volunteerDTO) {
         dao.updateVolunteer(volunteerDTO);
    }

    public List<VolunteerDTO> getVolunteerDone(String facility) {
        return dao.getVolunteerDone(facility);
    }
}
