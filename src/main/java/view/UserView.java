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
            System.out.println("사용자 ID : " + user.getID());
            System.out.println("사용자 이름 : " + user.getName());
            System.out.println("사용자 전화번호 : " + user.getPhoneNumber());
            System.out.println("사용자 주소 : " + user.getAddress());
            System.out.println("매너 온도 : " + user.getMannerTemperature());
            System.out.println();
        }
    }

}