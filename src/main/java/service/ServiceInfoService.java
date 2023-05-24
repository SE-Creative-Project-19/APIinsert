package service;

import persistence.dao.ServiceInfoDAO;
import persistence.dto.ServiceInfoDTO;

import java.util.List;

public class ServiceInfoService {
    private ServiceInfoDAO dao;

    public ServiceInfoService(ServiceInfoDAO dao) {
        this.dao = dao;
    }

    public List<ServiceInfoDTO> getServiceInfoList(int pageNo) {
        return dao.getServiceInfoList(pageNo);
    }
    public List<ServiceInfoDTO> getAllServiceInfo() {
        return dao.getAllServiceInfo();
    }

    public List<ServiceInfoDTO> getServiceInfoByFilter(ServiceInfoDTO serviceInfoDTO, int pageNo){return dao.getServiceInfoByFilter(serviceInfoDTO,pageNo);}

    public void insertServiceInfo(ServiceInfoDTO serviceInfoDTO) {
        dao.insertServiceInfo(serviceInfoDTO);
    }
}
