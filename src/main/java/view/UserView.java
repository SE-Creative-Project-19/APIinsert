package view;

import persistence.dto.UserDTO;
import service.UserService;


import java.util.List;
import java.util.Scanner;

public class UserView {
    private UserService userService;

    public UserView() {
        userService = null;
    }
    public UserView(UserService userService) {
        this.userService = userService;
    }

    public void signUpUser(UserDTO user) {
        // UserService를 이용하여 회원가입 로직을 처리하는 코드
        userService.insertUser(user);
        System.out.println("회원가입이 완료되었습니다.");
    }

    public void signUpManager(UserDTO user, String facility) {
        // UserService를 이용하여 회원가입 로직을 처리하는 코드
        userService.insertManager(user, facility);
        System.out.println("회원가입이 완료되었습니다.");
    }

    public void getUserInfo(String id) {
        // UserService를 이용하여 회원정보조회 로직을 처리하는 코드
        List<UserDTO> userList = userService.getUser(id);
        if (userList != null && !userList.isEmpty()) {
            for (UserDTO user : userList) {
                System.out.println("사용자 ID : " + user.getID());
                System.out.println("사용자 이름 : " + user.getName());
                System.out.println("사용자 전화번호 : " + user.getPhoneNumber());
                System.out.println("사용자 주소 : " + user.getAddress());
                System.out.println("매너 온도 : " + user.getMannerTemperature());
                System.out.println();
            }
        } else {
            System.out.println("사용자를 찾을 수 없습니다.");
        }
    }

//    public void findUserID(String name, String phoneNumber) {
//        // UserService를 이용하여 ID 찾기 로직을 처리하는 코드
//        String userID = userService.findUserId(name, phoneNumber);
//        if (userID != null) {
//            System.out.println("찾은 사용자 ID: " + userID);
//        } else {
//            System.out.println("해당 전화번호로 등록된 사용자가 없습니다.");
//        }
//    }

    public void resetPassword(String id, String phoneNumber, String newPassword) {
        // 사용자의 존재 여부 확인
        UserDTO user = userService.loginUser(id, phoneNumber);
        if (user != null) {
            // 비밀번호 재설정
            user.setPW(newPassword);
            userService.updateUser(user);
            System.out.println("비밀번호가 성공적으로 재설정되었습니다.");
        } else {
            System.out.println("입력한 정보와 일치하는 사용자가 없습니다.");
        }
    }

    public void login(String id, String pw) {
        // UserService를 이용하여 로그인 로직을 처리하는 코드
        UserDTO user = userService.loginUser(id, pw);
        if (user != null) {
            System.out.println("로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }
    }

    public void showInfoUser(UserDTO userDTO) {
        System.out.printf("이름 : %s \n", userDTO.getName() );
        System.out.printf("주소 : %s \n", userDTO.getAddress() );
        System.out.printf("전화번호 : %s \n", userDTO.getPhoneNumber() );
        System.out.printf("매너 온도 : %d \n\n",userDTO.getMannerTemperature());
    }
}