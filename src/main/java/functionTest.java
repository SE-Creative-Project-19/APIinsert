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
            List<ServiceInfoDTO> serviceInfoDTOS = serviceInfoDAO.selectByMnnstNm("사단법인 나눔세상 휴먼플러스"); //해당 기관의 현재 serviceInfoDTO list를 가져옴
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

            serviceInfoDTO.setSidoCd(6110000);

//            serviceInfoDTO.setProgrmBgnde(Date.valueOf("2023-05-01"));
//            serviceInfoDTO.setProgrmEndde(Date.valueOf("2023-06-30"));
            serviceInfoDTOS = dao.getServiceInfoByFilter(serviceInfoDTO,2);
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