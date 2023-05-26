package view;

import org.apache.ibatis.javassist.compiler.Parser;
import persistence.dto.ServiceInfoDTO;
import service.ServiceInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ServiceInfoView {
    private ServiceInfoService service;
    private Map<Integer,String> population = new HashMap<>();

    public ServiceInfoView() {
        population.put(6110000, "서울특별시");
        population.put(6260000, "부산광역시");
        population.put(6270000, "대구광역시");
        population.put(6280000, "인천광역시");
        population.put(6290000, "광주광역시");
        population.put(6300000, "대전광역시");
        population.put(6310000, "울산광역시");
        population.put(5690000, "세종특별자치시");
        population.put(6410000, "경기도");
        population.put(6420000, "강원도");
        population.put(6430000, "충청북도");
        population.put(6440000, "충청남도");
        population.put(6450000, "전라북도");
        population.put(6460000, "전라남도");
        population.put(6470000, "경상북도");
        population.put(6480000, "경상남도");
        population.put(6500000, "제주특별자치도");
    }
  
    public ServiceInfoView(ServiceInfoService service) {
        population.put(6110000, "서울특별시");
        population.put(6260000, "부산광역시");
        population.put(6270000, "대구광역시");
        population.put(6280000, "인천광역시");
        population.put(6290000, "광주광역시");
        population.put(6300000, "대전광역시");
        population.put(6310000, "울산광역시");
        population.put(5690000, "세종특별자치시");
        population.put(6410000, "경기도");
        population.put(6420000, "강원도");
        population.put(6430000, "충청북도");
        population.put(6440000, "충청남도");
        population.put(6450000, "전라북도");
        population.put(6460000, "전라남도");
        population.put(6470000, "경상북도");
        population.put(6480000, "경상남도");
        population.put(6500000, "제주특별자치도");
        this.service = service;
    }

    public void serviceInfoByFilter(ServiceInfoDTO serviceInfoDTO, int pageNo){//TODO dto 정보를 기준으로 필터링하고 pageNo에 해당하는 페이지를 출력합니다.
        List<ServiceInfoDTO> serviceInfoList = service.getServiceInfoByFilter(serviceInfoDTO, pageNo);
        for (ServiceInfoDTO serviceInfo : serviceInfoList) {
            System.out.println(serviceInfo.toString());
        }
        System.out.println("실행 종료");
    }
    public void displayServiceInfo(ServiceInfoDTO serviceInfoDTO) { //TODO 필터링을 적용한 봉사활동 정보를 출력합니다. 만약 dto가 null일경우 처음 전체 리스트를 출력합니다.
        Scanner scanner = new Scanner(System.in);
        List<ServiceInfoDTO> serviceInfoList = null;


        while(true){//haha
            System.out.print("페이지 번호를 입력하세요: (0: 종료)");
            int pageNo = scanner.nextInt();
            if(pageNo == 0) break;
            if (serviceInfoDTO == null){  //TODO DTO에 아무것도 없다는건 필터를 적용할게 없으므로 페이지 수에 맞게 출력한다.
                serviceInfoList = service.getServiceInfoList(pageNo);
                printMainServiceInfo(serviceInfoList);
            }
            else{ //TODO  해당 dto에 있는 정보를 바탕으로 필터링하여 list를 만들고 페이지에 맞게 가져온다.
                serviceInfoList = service.getServiceInfoByFilter(serviceInfoDTO,pageNo);
                printMainServiceInfo(serviceInfoList);
            }
        }
    }

    public void printMainServiceInfo(List<ServiceInfoDTO> list){  //TODO 봉사활동 정보에 대한 리스트를 메인이미지 기준으로 출력합니다

        for (ServiceInfoDTO serviceInfo : list) {
            Integer sidoCd = serviceInfo.getSidoCd();
            String sidoName = population.get(sidoCd);
            System.out.println(sidoName);

            System.out.printf("모집 중 (%s,%s) \n", serviceInfo.getSrvcCLCode(),serviceInfo.getSrvcCSCode());
            System.out.println(serviceInfo.getProgrmSj());
            System.out.printf("[모집기관] %s [모집기간] %s ~ %s [봉사기간] %s ~ %s \n", serviceInfo.getMnnstNm(),serviceInfo.getNoticeBgnde(),serviceInfo.getNoticeEndde(),serviceInfo.getProgrmBgnde(), serviceInfo.getProgrmEndde());
            System.out.println(serviceInfo.getProgrmCn());
            System.out.println();
        }
    }
    public void displayAllServiceInfo() { //TODO 그냥 실험용 출력 검사
        List<ServiceInfoDTO> serviceInfoList = service.getAllServiceInfo();
        for (ServiceInfoDTO serviceInfo : serviceInfoList) {
            System.out.println(serviceInfo.toString());
        }
    }
}