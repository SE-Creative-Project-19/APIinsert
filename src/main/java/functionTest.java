import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import persistence.dao.ServiceInfoDAO;
import persistence.dao.UserDAO;
import persistence.dao.VolunteerDAO;
import persistence.dto.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class functionTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ServiceInfoDTO serviceInfoDTO = null;
        Map<String, Integer> population = new HashMap<>();
            population.put("서울특별시", 6110000);
            population.put("부산광역시", 6260000);
            population.put("대구광역시", 6270000);
            population.put("인천광역시", 6280000);
            population.put("광주광역시", 6290000);
            population.put("대전광역시", 6300000);
            population.put("울산광역시", 6310000);
            population.put("세종특별자치시", 5690000);
            population.put("경기도", 6410000);
            population.put("강원도", 6420000);
            population.put("충청북도", 6430000);
            population.put("충청남도", 6440000);
            population.put("전라북도", 6450000);
            population.put("전라남도", 6460000);
            population.put("경상북도", 6470000);
            population.put("경상남도", 6480000);
            population.put("제주특별자치도", 6500000);
        try {
            String resource = "config/config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();

            ServiceInfoDAO dao = new ServiceInfoDAO(sqlSessionFactory);
            VolunteerDAO volunteerDAO = new VolunteerDAO(sqlSessionFactory);
            UserDAO userDao = new UserDAO(sqlSessionFactory);
//            ServiceInfoService service = new ServiceInfoService(dao,volunteerDAO);
//            UserView userView = new UserView(new UserService(userDao));
//            ServiceInfoView view = new ServiceInfoView(service);
            ServiceInfoDAO serviceInfoDAO = new ServiceInfoDAO(sqlSessionFactory);

            //view.displayAllServiceInfo();
            //페이지 처리를 테스트

           // System.out.println("필터링을 하지 않았을 때를 기준으로 출력합니다.");
           //view.displayServiceInfo(serviceInfoDTO);d



//
            //service.updateServiceInfoByTime();
            // 담당자의 봉사활동 조회 부터 승인, 별점 미등록자 조회, 별점 등록 까지
            List<ServiceInfoDTO> serviceInfoDTOS = serviceInfoDAO.selectByMnnstNm("홍성군자원봉사센터"); //해당 기관의 현재 serviceInfoDTO list를 가져옴
            // 봉사활동 리스트에서 하나 선택했다면 volunteer리스트에서 해당 serviceInfo를 가진 애들만 출력한 후 user의 정보를 가지고온다.
            System.out.println(serviceInfoDTOS.size());
            List<VolunteerDTO> list = volunteerDAO.getVolunteerApplicant(serviceInfoDTOS.get(0).getServiceInfoPK()); //해당 serviceInfoDTO 신청자 명단을 가져옴
            List<UserDTO> userDTOS = userDao.getUsersByPk(list); //봉사 신청자의 개인정보 리턴
            System.out.println("굿");
            for(int i = 0 ; i< list.size(); i++){
                System.out.println(list.get(i).toString());
                System.out.println(userDTOS.get(i).toString());
                list.get(i).setProcessingResult("별점 미등록");
                volunteerDAO.updateVolunteer(list.get(i)); //업데이트
                serviceInfoDAO.updateApptotal(list.get(i).getServiceInfo_ServiceInfoPK()); //신청인원수 증가 및 모집인원 만족 시 모집 상태 마감으로 변경
                System.out.println();
            }
            list = volunteerDAO.getVolunteerDone(serviceInfoDTOS.get(0).getServiceInfoPK()); //특정 serviceInfo의 별점 미등록 상태인 volunteer list return

            userDTOS = userDao.getUsersByPk(list); // 특정 봉사활동에 신청한 별점 미등록 상태의 신청자의 userDTO list return

            System.out.println("별점 등록 시작합니다");

            for(int i = 0 ; i< list.size(); i++){
                System.out.println(list.get(i).toString());
                System.out.println(userDTOS.get(i).toString());
                userDTOS.get(i).setMannerTemperature(userDTOS.get(i).getMannerTemperature()+4); //현재 매너온도에 부여할 점수를 더함
                userDao.updateUser(userDTOS.get(i)); //유저 매너온도 수정


                list.get(i).setProcessingResult("봉사 완료"); //해당 봉사의 봉사상태 변경
                volunteerDAO.updateVolunteer(list.get(i)); // 업데이트
                System.out.println();
            }

            serviceInfoDTO = new ServiceInfoDTO();
            System.out.println("필터링 결과 출력");
            int sidoCd = population.get("서울특별시");//유저의 주소

            serviceInfoDTO.setSidoCd(sidoCd);

//            serviceInfoDTO.setProgrmBgnde(Date.valueOf("2023-05-01"));
//            serviceInfoDTO.setProgrmEndde(Date.valueOf("2023-06-30"));
            serviceInfoDTOS = dao.getServiceInfoByFilter(serviceInfoDTO);
            for(ServiceInfoDTO serviceInfoDTO1 : serviceInfoDTOS){

                System.out.println(serviceInfoDTO1.toString());
                System.out.println();
            }



            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(" 안녕");
    }
}