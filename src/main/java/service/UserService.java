package service;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;
import java.util.List;

public class UserService{
    private UserDAO dao;

    public UserService(UserDAO dao) {this.dao = dao;}

    public void insertUserInfo(UserDTO userDTO) {dao.insertUserInfo(userDTO);}

}
