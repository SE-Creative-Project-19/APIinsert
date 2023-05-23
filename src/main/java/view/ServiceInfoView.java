package view;

import persistence.dto.ServiceInfoDTO;
import service.ServiceInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ServiceInfoView {
    private ServiceInfoService service;
    private Map<String, Integer> population = new HashMap<>();



    public ServiceInfoView(ServiceInfoService service) {
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
        this.service = service;
    }
    public void serviceInfoByFilter(ServiceInfoDTO serviceInfoDTO){
        List<ServiceInfoDTO> serviceInfoList = service.getServiceInfoByFilter(serviceInfoDTO);
        for (ServiceInfoDTO serviceInfo : serviceInfoList) {
            System.out.println(serviceInfo.toString()); // Assumes the ServiceInfoDTO class has overridden the toString() method
        }
        System.out.println("실행 종료");
    }
    public void displayServiceInfo(ServiceInfoDTO serviceInfoDTO) {
        Scanner scanner = new Scanner(System.in);
        List<ServiceInfoDTO> serviceInfoList = null;
        System.out.print("페이지 번호를 입력하세요: ");
        int pageNo = scanner.nextInt();

        if (serviceInfoDTO == null){
            serviceInfoList = service.getServiceInfoList(pageNo);
        }
        else{
            serviceInfoList = service.getServiceInfoByFilter(serviceInfoDTO,pageNo);
        }

        System.out.println("실행");
        // 데이터를 출력하는 로직을 구현
        printMainServiceInfo(serviceInfoList);
        System.out.println("끝");
    }
    //메인 봉사화면에서 나오는 정보를 출력합니다.
    public void printMainServiceInfo(List<ServiceInfoDTO> list){
        for (ServiceInfoDTO serviceInfo : list) {
            System.out.printf("모집 중 (%s,%s) \n", serviceInfo.getSrvcCLCode(),serviceInfo.getSrvcCSCode());
            System.out.println(serviceInfo.getProgrmSj());
            System.out.printf("[모집기관] %s [모집기간] %s ~ %s [봉사기간] %s ~ %s \n", serviceInfo.getMnnstNm(),serviceInfo.getNoticeBgnde(),serviceInfo.getNoticeEndde(),serviceInfo.getProgrmBgnde(), serviceInfo.getProgrmEndde());
            System.out.println(serviceInfo.getProgrmCn());
        }
    }
    public void displayAllServiceInfo() {
        List<ServiceInfoDTO> serviceInfoList = service.getAllServiceInfo();
        for (ServiceInfoDTO serviceInfo : serviceInfoList) {
            System.out.println(serviceInfo.toString()); // Assumes the ServiceInfoDTO class has overridden the toString() method
        }
    }
}
