package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.security.Timestamp;
import java.time.LocalTime;
import java.sql.Date;
import java.time.LocalDate;
@Getter
@Setter
@ToString
public class ServiceInfoDTO {
    private int serviceInfoPK; //프로그램등록번호
    private String actWkdy; //활동요일
    private int appTotal; //신청인원수
    private String srvcCLCode; //봉사분야
    private String srvcCSCode; //봉사 상세 분야
    private boolean adultPosblAt; //성인가능여부
    private boolean yngbgsPosblAt; //청소년가능여부
    private String mnnstNm; //모집기관(주관기관명)

    private String nanmmbyNm; //등록기관(나눔주체명)

    private String actPlace; //봉사장소
    private String nanmmbyNmAdmn; //담당자명
    private String telno; //전화번호
    private String postAdres; //담당자 주소

    private String email; //이메일
    private String progrmCn; //내용
    private String progrmSj; //봉사제목
    private int progrmSttusSe; //모집상태
    private Date progrmBgnde; //봉사시작일자
    private Date progrmEndde; //봉사종료일자
    private LocalTime actBeginTm; //봉사시작시간
    private LocalTime actEndTm; //봉사종료시간
    private Date noticeBgnde; //모집시작일
    private Date noticeEndde; //모집종료일
    private int rcritNmpr; //모집인원
    private String sidoCd; //시도코드

    public ServiceInfoDTO(int serviceInfoPK, String actWkdy, int appTotal, String srvcCLCode,String srvcCSCode, boolean adultPosblAt,
                          boolean yngbgsPosblAt, boolean grpPosblAt, String mnnstNm, String nanmmbyNm, String actPlace,
                          String nanmmbyNmAdmn, String telno, String postAdres, String email, String progrmCn, String progrmSj,
                          int progrmSttusSe, Date progrmBgnde, Date progrmEndde, LocalTime actBeginTm, LocalTime actEndTm,
                          Date noticeBgnde, Date noticeEndde, int rcritNmpr, String sidoCd) {
        this.serviceInfoPK = serviceInfoPK;
        this.actWkdy = actWkdy;
        this.appTotal = appTotal;
        this.srvcCLCode = srvcCLCode;
        this.srvcCSCode = srvcCSCode;
        this.adultPosblAt = adultPosblAt;
        this.yngbgsPosblAt = yngbgsPosblAt;
        this.mnnstNm = mnnstNm;
        this.nanmmbyNm = nanmmbyNm;
        this.actPlace = actPlace;
        this.nanmmbyNmAdmn = nanmmbyNmAdmn;
        this.telno = telno;
        this.postAdres = postAdres;
        this.email = email;
        this.progrmCn = progrmCn;
        this.progrmSj = progrmSj;
        this.progrmSttusSe = progrmSttusSe;
        this.progrmBgnde = progrmBgnde;
        this.progrmEndde = progrmEndde;
        this.actBeginTm = actBeginTm;
        this.actEndTm = actEndTm;
        this.noticeBgnde = noticeBgnde;
        this.noticeEndde = noticeEndde;
        this.rcritNmpr = rcritNmpr;
        this.sidoCd = sidoCd;
    }
    public ServiceInfoDTO() {
        this.actWkdy = null;
        this.appTotal = 0;
        this.srvcCLCode = null;
        this.adultPosblAt = false;
        this.yngbgsPosblAt = false;
        this.mnnstNm = null;
        this.nanmmbyNm = null;
        this.actPlace = null;
        this.nanmmbyNmAdmn = null;
        this.telno = null;
        this.postAdres = null;
        this.email = null;
        this.progrmCn = null;
        this.progrmSj = null;
        this.progrmSttusSe = 0;
        this.progrmBgnde = null;
        this.progrmEndde = null;
        this.actBeginTm = null;
        this.actEndTm = null;
        this.noticeBgnde = null;
        this.noticeEndde = null;
        this.rcritNmpr = 0;
        this.sidoCd = null;
    }
    // Getter 및 Setter 메서드 생략

}

/* dto to bytes, bytes to dto
 public byte[] getBytes(DataOutputStream dos, int num) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        dos = new DataOutputStream(buf);
        dos.writeUTF(id);
        dos.writeUTF(password);
        if(num == 1){
            dos.writeUTF(name);
            dos.writeInt(age);
            dos.writeUTF(type);
            dos.writeUTF(phone);
            dos.writeUTF(state);
        }

        byte[] res = buf.toByteArray();
        return res;

    }

    public void bytesToAccountDTO(DataInputStream dis, int num, byte[] bytes) throws IOException {

        dis = new DataInputStream(new ByteArrayInputStream(bytes));

        id = dis.readUTF();
        password = dis.readUTF();
        if(num ==1){
            name = dis.readUTF();
            age = dis.readInt();
            type = dis.readUTF();
            phone = dis.readUTF();
        }
    }
 */




