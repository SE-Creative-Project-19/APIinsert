package service;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;
import java.util.List;

public class UserService{
    private UserDAO dao;

    public UserService(UserDAO dao) {this.dao = dao;}

    public void insertUser(UserDTO userDTO) {dao.insertUser(userDTO);}
    public void updateUser(UserDTO userDTO) {dao.updateUser(userDTO);}
    public List<UserDTO> getUser() {return dao.getUser();}
    public String findUserId(String phoneNumber) {return dao.findUserId(phoneNumber);}
    public UserDTO loginUser(String id, String pw) {return dao.loginUser(id, pw);}

}
