package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.MessageDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MessageDAO(SqlSessionFactory sqlSessionFactory) { //TODO 채팅
        this.sqlSessionFactory = sqlSessionFactory;
    }
}