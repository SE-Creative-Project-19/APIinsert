package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.MessageHeaderDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHeaderDAO{
    private SqlSession sqlSession;
    private final SqlSessionFactory sqlSessionFactory;

    public MessageHeaderDAO(SqlSession sqlSession, SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = sqlSession;
        this.sqlSessionFactory = sqlSessionFactory;
    }
}