package view;

import persistence.dto.VolunteerDTO;
import service.VolunteerService;


import java.util.List;
import java.util.Scanner;

public class VolunteerView {
    private VolunteerService service;

    public VolunteerView(VolunteerService service) {
        this.service = service;
    }
}