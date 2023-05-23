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

    public List<UserDTO> getUser() {
        List<UserDTO> list = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
            list = session.selectList("mapper.UserMapper.getUser");
        } finally {
            session.close();
        }
        return list;
    }

    public void insertUser(UserDTO userDTO){
        SqlSession session = sqlSessionFactory.openSession();
        try{
            session.insert("mapper.UserMapper.insertUser",userDTO);
            session.commit();
        } finally {
            session.close();
        }
    }
}