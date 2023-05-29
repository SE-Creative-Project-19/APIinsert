package persistence.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString

public class VolunteerDTO implements Serializable {
    private int volunteerPK; //봉사PK
    private String processingResult; //처리 결과(신층 승인여부) 승인 전, (승인,거절), 신청 취소 ,별점 미등록,봉사 완료
    private int ServiceInfo_ServiceInfoPK;//봉사프로그램 등록번호PK
    private int User_UserPK;//사용자PK

    public VolunteerDTO() {
    }

    public VolunteerDTO(int volunteerPK, String processingResult, int ServiceInfo_ServiceInfoPK, int User_UserPK) {
        this.volunteerPK = volunteerPK;
        this.processingResult = processingResult;
        this.ServiceInfo_ServiceInfoPK = ServiceInfo_ServiceInfoPK;
        this.User_UserPK = User_UserPK;
    }
}
