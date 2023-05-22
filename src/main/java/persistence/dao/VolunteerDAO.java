package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import persistence.dto.VolunteerDTO;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolunteerDAO {
    private SqlSession sqlSession;
    private final SqlSessionFactory sqlSessionFactory;

    public VolunteerDAO(SqlSession sqlSession, SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = sqlSession;
        this.sqlSessionFactory = sqlSessionFactory;
    }
}