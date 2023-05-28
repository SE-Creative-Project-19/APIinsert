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
    private int serviceInfoPK;//봉사프로그램 등록번호PK
    private int userPK;//사용자PK

    public VolunteerDTO() {
    }

    public VolunteerDTO(int volunteerPK, String processingResult, int serviceInfoPK, int userPK) {
        this.volunteerPK = volunteerPK;
        this.processingResult = processingResult;
        this.serviceInfoPK = serviceInfoPK;
        this.userPK = userPK;
    }
}
