package view;

import persistence.dto.MessageHeaderDTO;
import service.MessageHeaderService;


import java.util.List;
import java.util.Scanner;

public class MessageHeaderView {
    private MessageHeaderService service;

    public MessageHeaderView(MessageHeaderService service) {
        this.service = service;
    }
}