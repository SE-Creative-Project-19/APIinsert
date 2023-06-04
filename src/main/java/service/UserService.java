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
    public void updateUser(UserDTO userDTO) {dao.updateUser(userDTO);}
    public List<UserDTO> getUser(String id) {return dao.getUser(id);}
    public List<UserDTO> getUsersByPK(List<VolunteerDTO> volunteerDTOS){return dao.getUsersByPk(volunteerDTOS);};
    public String findUserId(String phoneNumber) {return dao.findUserId(phoneNumber);}
    public String findUserPassword(String id, String phoneNumber) {return dao.findUserPassword(id, phoneNumber);}
    public UserDTO loginUser(String id, String pw) {return dao.loginUser(id, pw);}

    public UserDTO getUserByPK(int userPK) { //TODO userPK를 통해서 userDTO를 return 합니다
        return dao.getUserByPK(userPK);
    }
}
