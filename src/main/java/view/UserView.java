package view;

import persistence.dto.UserDTO;
import service.UserService;


import java.util.List;
import java.util.Scanner;

public class UserView {
    private UserService service;

    public UserView(UserService service) {
        this.service = service;
    }

    public void printUser(List<UserDTO> list) {
        for (UserDTO user : list) {
            System.out.println(user.getID());
            System.out.println(user.getName());
            System.out.println(user.getPhoneNumber());
            System.out.println(user.getAddress());
            System.out.println(user.getMannerTemperature());
            System.out.println();
        }
    }

}