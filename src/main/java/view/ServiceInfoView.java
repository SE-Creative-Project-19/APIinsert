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
        for (ServiceInfoDTO serviceInfo : serviceInfoList) {
            System.out.println(serviceInfo);
        }
        System.out.println("끝");
    }
    public void displayAllServiceInfo() {
        List<ServiceInfoDTO> serviceInfoList = service.getAllServiceInfo();
        for (ServiceInfoDTO serviceInfo : serviceInfoList) {
            System.out.println(serviceInfo); // Assumes the ServiceInfoDTO class has overridden the toString() method
        }
    }
}
