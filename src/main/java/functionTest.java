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
import view.UserView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            VolunteerDAO volunteerDAO = new VolunteerDAO(sqlSessionFactory);
            UserDAO userDao = new UserDAO(sqlSessionFactory);
            ServiceInfoService service = new ServiceInfoService(dao,volunteerDAO);
            UserView userView = new UserView(new UserService(userDao));
            ServiceInfoView view = new ServiceInfoView(service);

            //view.displayAllServiceInfo();
            //페이지 처리를 테스트

           // System.out.println("필터링을 하지 않았을 때를 기준으로 출력합니다.");
           //view.displayServiceInfo(serviceInfoDTO);
            System.out.println("필터링 했을 때 출력합니다.");
             serviceInfoDTO = new ServiceInfoDTO();
//            serviceInfoDTO.setProgrmEndde(Date.valueOf("2023-07-30"));
//
            service.updateServiceInfoByTime();
//           List<VolunteerDTO> list = volunteerDAO.getVolunteerApplicant("홍성군자원봉사센터");
////            System.out.println(list.size()==0);
////            for(VolunteerDTO volunteerDTO : list){
////                System.out.println(volunteerDTO.toString());
////            }
////
////            System.out.println("다음단계");
////            List<UserDTO> userDTOS = userDao.getUsersByPk(list,1);
////            for(UserDTO userDTO : userDTOS){
////                userView.showInfoUser(userDTO);
////                System.out.println(userDTO.toString());
////                userDTO.setMannerTemperature(userDTO.getMannerTemperature()+4);
////                userDao.updateUser(userDTO);
////            }
////            list.get(0).setProcessingResult("봉사 완료");
////            System.out.println(list.get(0).toString());
////            volunteerDAO.updateVolunteer(list.get(0));
            Map<String, Integer> newPopulation = new HashMap<>();
            for (Map.Entry<Integer, String> entry :view.getPopulation().entrySet()) {
                newPopulation.put(entry.getValue(), entry.getKey());
            }
//           String sidoName = "서울특별시";
//            int sidoCd = newPopulation.get(sidoName);
//            System.out.println("sidocd입니다");
//            System.out.println(sidoCd);
//            if(sidoCd > 1 ) System.out.println("이건 숫자가 맞다");
//            System.out.println(sidoCd);
//
//            serviceInfoDTO.setSidoCd(sidoCd);
//            serviceInfoDTO.setProgrmBgnde(Date.valueOf("2023-05-21"));
            System.out.println(serviceInfoDTO.toString());
//
            view.displayServiceInfo(serviceInfoDTO);
 //            service.updateServiceInfoByTime();
//
//
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" 안녕");
    }
}