package view;

import persistence.dto.UserDTO;
import persistence.dto.VolunteerDTO;
import service.UserService;
import service.VolunteerService;


import java.util.List;
import java.util.Scanner;

public class VolunteerView {
    private VolunteerService service;
    private UserService userService;
    private UserView userView;
    Scanner sc = new Scanner(System.in);

    public VolunteerView () {

    }
    public VolunteerView(VolunteerService service, UserView userView, UserService userService) {
        this.service = service;
        this.userView = userView;
        this.userService = userService;
    }

    public void printVolunteer(List<VolunteerDTO> list){
        for(VolunteerDTO volunteerDTO : list) {
            System.out.println("");
        }
    }
    public void showVolunteerApplicant(String facility){ //TODO 봉사 담당자는 자신의 봉사 기관에 신청한 사람의 명단을 확인합니다 및 처리 이미지
        List<VolunteerDTO> volunteerDTOS = service.getVolunteerApplicant(facility); //TODO 봉사 담당자는 volunteerService에서 신청자를 얻음, serviceInfo.progrmEndde의 날짜가 지났을 경우 select 제외
        List<UserDTO> userDTOS = userService.getUsersByPK(volunteerDTOS, 1);

        for(int i = 0; i < volunteerDTOS.size(); i++){
            VolunteerDTO volunteerDTO = volunteerDTOS.get(i);
            UserDTO userDTO = userDTOS.get(i);
            userView.showInfoUser(userDTO);//TODO 담당자는 신청자의 이름, 전화번호, 매너온도 등을 봅니다.

            System.out.println("1. 승인 2. 거절");
            int res = sc.nextInt();

            if(res == 1)volunteerDTO.setProcessingResult("승인");
            else  volunteerDTO.setProcessingResult("거절");
            service.updateVolunteer(volunteerDTO);

           service.updateVolunteer(volunteerDTO); //TODO  변경된 ProcessingResult를 업데이트 합니다.
        }
    }
    public void showVolunteerDone(String facility){//TODO 담당자는 활동이 완료되어 매너온도를 등록할 봉사자들을 출력합니다.
        List<VolunteerDTO> volunteerDTOS = service.getVolunteerDone(facility); //TODO 해당 기관에서 봉사 처리 상태가 봉사 완료인 volunteer를 select함.

        List<UserDTO> userDTOS = userService.getUsersByPK(volunteerDTOS, 1);
        for(int i = 0; i < volunteerDTOS.size(); i++){
            VolunteerDTO volunteerDTO = volunteerDTOS.get(i);
            UserDTO userDTO = userDTOS.get(i);

            userView.showInfoUser(userDTO); //TODO 담당자는 신청자의 이름, 전화번호, 매너온도 등을 봅니다.
            System.out.println("1~5점을 입력해주세요");
            int res = sc.nextInt();

            //UserDTO userDTO = userService.getUserByPK(volunteerDTO.getUserPK());//TODO 봉사 테이블의 userPK를 통해 user 테이블에 접근

            userDTO.setMannerTemperature(userDTO.getMannerTemperature()+res);
            userService.updateUser(userDTO);  //TODO 담당자는 해당 봉사 신청자의 매너 온도를 수정합니다.

            volunteerDTO.setProcessingResult("봉사 완료");
            service.updateVolunteer(volunteerDTO); //TODO  변경된 ProcessingResult를 업데이트 합니다.

      
        }
    }

}