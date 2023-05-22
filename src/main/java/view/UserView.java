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
}