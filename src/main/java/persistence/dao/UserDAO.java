package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.UserDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private SqlSession sqlSession;
    private final SqlSessionFactory sqlSessionFactory;

    public UserDAO(SqlSession sqlSession, SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = sqlSession;
        this.sqlSessionFactory = sqlSessionFactory;
    }
}