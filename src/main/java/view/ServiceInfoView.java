package view;

import persistence.dto.ServiceInfoDTO;
import service.ServiceInfoService;
import java.util.List;
import java.util.Scanner;

public class ServiceInfoView {
    private ServiceInfoService service;

    public ServiceInfoView(ServiceInfoService service) {
        this.service = service;
    }

    public void displayServiceInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("페이지 번호를 입력하세요: ");
        int pageNo = scanner.nextInt();

        List<ServiceInfoDTO> serviceInfoList = service.getServiceInfoList(pageNo);
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
            System.out.println(serviceInfo); // Assumes the ServiceInfoDTO class has overridden the toString() method
        }
    }
}
