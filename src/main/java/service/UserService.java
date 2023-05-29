package service;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;
import persistence.dto.VolunteerDTO;

import java.util.List;

public class UserService{
    private UserDAO dao;
    private VolunteerDTO volunteerDTO;
    public UserService(UserDAO dao) {this.dao = dao; volunteerDTO = new VolunteerDTO();}
    public void insertUser(UserDTO userDTO) {dao.insertUser(userDTO);}
    public void insertManager(UserDTO userDTO) {dao.insertManager(userDTO);}
    public void updateUser(UserDTO userDTO) {dao.updateUser(userDTO);}
    public List<UserDTO> getUser(String id) {return dao.getUser(id);}
    public List<UserDTO> getUsersByPK(List<VolunteerDTO> volunteerDTOS, int pageNo){return dao.getUsersByPk(volunteerDTOS,pageNo);};
    public String findUserId(String name, String phoneNumber) {return dao.findUserId(name, phoneNumber);}
    public boolean findUserPassword(String name, String id, String phoneNumber) {return dao.findUserPassword(name, id, phoneNumber);}
    public UserDTO loginUser(String id, String pw) {return dao.loginUser(id, pw);}

}
