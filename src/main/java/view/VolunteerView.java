package view;

import persistence.dto.VolunteerDTO;
import service.VolunteerService;


import java.util.List;
import java.util.Scanner;

public class VolunteerView {
    private VolunteerService service;

    public VolunteerView(VolunteerService service) {
        this.service = service;
    } // test 입니당

    public void printVolunteer(List<VolunteerDTO> list){
        for(VolunteerDTO volunteerDTO : list) {
            System.out.println("");
        }
    }
}