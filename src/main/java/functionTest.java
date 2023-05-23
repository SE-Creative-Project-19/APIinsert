import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import persistence.dao.ServiceInfoDAO;
import service.ServiceInfoService;
import view.ServiceInfoView;
import persistence.dto.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalTime;

public class functionTest {
    public static void main(String[] args) {
        LocalTime today = LocalTime.parse("09"+":00");
        System.out.println(today);
        try {
            String resource = "config/config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            ServiceInfoDAO dao = new ServiceInfoDAO(sqlSession, sqlSessionFactory);
            ServiceInfoService service = new ServiceInfoService(dao);
            ServiceInfoView view = new ServiceInfoView(service);

            //view.displayAllServiceInfo();
            //페이지 처리를 테스트
           // view.displayServiceInfo();
            ServiceInfoDTO serviceInfoDTO = new ServiceInfoDTO();

            serviceInfoDTO.setProgrmEndde(Date.valueOf("2023-07-30"));
            System.out.println(serviceInfoDTO.toString());

            view.serviceInfoByFilter(serviceInfoDTO);
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" 안녕");
    }
}
