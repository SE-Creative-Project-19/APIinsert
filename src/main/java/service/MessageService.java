package service;
import persistence.dao.MessageDAO;
import persistence.dto.MessageDTO;
import java.util.List;

public class MessageService{
    private MessageDAO dao;

    public MessageService(MessageDAO dao) {this.dao = dao;}

}
