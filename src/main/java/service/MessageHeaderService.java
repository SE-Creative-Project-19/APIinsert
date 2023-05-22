package service;
import persistence.dao.MessageHeaderDAO;
import persistence.dto.MessageHeaderDTO;
import java.util.List;

public class MessageHeaderService {
    private MessageHeaderDAO dao;

    public MessageHeaderService(MessageHeaderDAO dao) {this.dao = dao;}

}
