package service;
import persistence.dao.VolunteerDAO;
import persistence.dto.VolunteerDTO;
import java.util.List;

public class VolunteerService{
    private VolunteerDAO dao;

    public VolunteerService(VolunteerDAO dao) {this.dao = dao;}

    public List<VolunteerDTO> getVolunteer() {return dao.getVolunteer();}

    public List<VolunteerDTO> getVolunteerFilter(VolunteerDTO volunteerDTO, String processingResult)
    {return dao.getVolunteerFilter(volunteerDTO, processingResult);}


}
