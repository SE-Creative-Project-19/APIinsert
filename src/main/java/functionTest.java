import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import persistence.dao.ServiceInfoDAO;
import persistence.dao.UserDAO;
import persistence.dao.VolunteerDAO;
import service.ServiceInfoService;
import service.UserService;
import view.ServiceInfoView;
import persistence.dto.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalTime;
import java.util.Scanner;

public class functionTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServiceInfoDTO serviceInfoDTO = null;
        try {
            String resource = "config/config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            ServiceInfoDAO dao = new ServiceInfoDAO(sqlSessionFactory);
            VolunteerDAO volunteerDAO = new VolunteerDAO(sqlSession,sqlSessionFactory);
            ServiceInfoService service = new ServiceInfoService(dao,volunteerDAO);

            ServiceInfoView view = new ServiceInfoView(service);

            //view.displayAllServiceInfo();
            //페이지 처리를 테스트
            System.out.println("필터링을 하지 않았을 때를 기준으로 출력합니다.");
            view.displayServiceInfo(serviceInfoDTO);
            System.out.println("필터링 했을 때 출력합니다.");
            serviceInfoDTO = new ServiceInfoDTO();
            serviceInfoDTO.setProgrmEndde(Date.valueOf("2023-07-30"));
            view.displayServiceInfo(serviceInfoDTO);

            service.updateServiceInfoByTime();

            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" 안녕");
    }
}