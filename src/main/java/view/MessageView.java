package view;

import persistence.dto.MessageDTO;
import service.MessageService;


import java.util.List;
import java.util.Scanner;

public class MessageView {
    private MessageService service;

    public MessageView(MessageService service) {
        this.service = service;
    }
}