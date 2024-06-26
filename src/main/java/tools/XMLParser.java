package tools;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.sql.Date;


import persistence.dao.ServiceInfoDAO;
import persistence.dao.VolunteerDAO;
import persistence.dto.ServiceInfoDTO;

public class XMLParser {
	final String SERVICE_KEY = "j2AFfROSPcDKEoCNFRK5BwwauKN5RhguWzqMhZp235enHvWCS0lr4Uh6c6oP1bBpCmnTLCwg%2BigmE65B5FsdkA%3D%3D"; // PERSONAL SERVICE_KEY
	String code = "";
	String info = "";

	public XMLParser() {
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void getInfoByAPI() throws IOException { // Get Information By using API
        String resource = "config/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ServiceInfoDAO serviceInfoDAO = new ServiceInfoDAO(sqlSessionFactory);
		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem"); /*
		 * URL
		 */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("progrmRegistNo", "UTF-8") + "="
				+ URLEncoder.encode(code, "UTF-8")); /* 프로그램등록번호 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		// System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		info = sb.toString(); // Set info String
	}



	public void getInfoTest() throws ParseException, IOException {
		Document doc = Jsoup.parse(info); // Parse information by using JSoup
		ServiceInfoDTO serviceInfoDTO = new ServiceInfoDTO();
		Elements name = doc.getElementsByTag("mnnstNm"); // Get Organization Name
		String resource = "config/config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

		ServiceInfoDAO dao = new ServiceInfoDAO(sqlSessionFactory);
		VolunteerDAO volunteerDAO = new VolunteerDAO(sqlSessionFactory);



		if (name.text().equals("")) {
			System.out.println("null");
		} else {
			//serviceInfoPK
			int serviceInfoPK = Integer.parseInt(code);
			serviceInfoDTO.setServiceInfoPK(serviceInfoPK);
			System.out.println(serviceInfoPK);

			// actWkdy
			Elements actWkdyElements = doc.getElementsByTag("actWkdy");
			if(!actWkdyElements.isEmpty()){
				String actWkdy = actWkdyElements.first().text();
				serviceInfoDTO.setActWkdy(actWkdy);
			}

			// appTotal
			Elements appTotalElements = doc.getElementsByTag("appTotal");
			int appTotal = Integer.parseInt(appTotalElements.first().text());
			serviceInfoDTO.setAppTotal(appTotal);

			// srvcCLCode
			Elements srvcCLCodeElements = doc.getElementsByTag("srvcCLCode");
			String[] parts = srvcCLCodeElements.first().text().split("> ");
			String srvcCLCode = parts[0];
			serviceInfoDTO.setSrvcCLCode(srvcCLCode); //분류

			String srvcCSCode = parts[1];
			serviceInfoDTO.setSrvcCSCode(srvcCSCode); //상세 분류

			// mnnstNm
			Elements mnnstNmElements = doc.getElementsByTag("mnnstNm");
			String mnnstNm = mnnstNmElements.first().text();
			serviceInfoDTO.setMnnstNm(mnnstNm);

			// nanmmbyNm
			Elements nanmmbyNmElements = doc.getElementsByTag("nanmmbyNm");
			String nanmmbyNm = nanmmbyNmElements.first().text();
			serviceInfoDTO.setNanmmbyNm(nanmmbyNm);

			// actPlace
			Elements actPlaceElements = doc.getElementsByTag("actPlace");
			String actPlace = actPlaceElements.first().text();
			serviceInfoDTO.setActPlace(actPlace);

			// nanmmbyNmAdmn
			Elements nanmmbyNmAdmnElements = doc.getElementsByTag("nanmmbyNmAdmn");
			String nanmmbyNmAdmn = nanmmbyNmAdmnElements.first().text();
			serviceInfoDTO.setNanmmbyNmAdmn(nanmmbyNmAdmn);

			// telno
			Elements telnoElements = doc.getElementsByTag("telno");
			String telno = telnoElements.first().text();
			serviceInfoDTO.setTelno(telno);

			// postAdres
			Elements postAdresElements = doc.getElementsByTag("postAdres");
			String postAdres = postAdresElements.first().text();
			serviceInfoDTO.setPostAdres(postAdres);

			// email
			Elements emailElements = doc.getElementsByTag("email");
			String email = emailElements.first() != null ? emailElements.first().text() : "";

			serviceInfoDTO.setEmail(email);

			// progrmCn
			Elements progrmCnElements = doc.getElementsByTag("progrmCn");
			String progrmCn = progrmCnElements.first().text();
			serviceInfoDTO.setProgrmCn(progrmCn);

			// progrmSj
			Elements progrmSjElements = doc.getElementsByTag("progrmSj");
			String progrmSj = progrmSjElements.first().text();
			serviceInfoDTO.setProgrmSj(progrmSj);

			// progrmSttusSe
			Elements progrmSttusSeElements = doc.getElementsByTag("progrmSttusSe");
			int progrmSttusSe = Integer.parseInt(progrmSttusSeElements.first().text());
			serviceInfoDTO.setProgrmSttusSe(progrmSttusSe);

			// progrmBgnde
			Elements progrmBgndeElements = doc.getElementsByTag("progrmBgnde");
			String progrmBgndeString = progrmBgndeElements.first().text();
			progrmBgndeString = progrmBgndeString.substring(0, 4) + "-" + progrmBgndeString.substring(4, 6) + "-" + progrmBgndeString.substring(6);
			Date progrmBgnde = Date.valueOf(progrmBgndeString);
			serviceInfoDTO.setProgrmBgnde(progrmBgnde);

			// progrmEndde
			Elements progrmEnddeElements = doc.getElementsByTag("progrmEndde");
			String progrmEnddeString = progrmEnddeElements.first().text();
			progrmEnddeString = progrmEnddeString.substring(0, 4) + "-" + progrmEnddeString.substring(4, 6) + "-" + progrmEnddeString.substring(6);
			Date progrmEndde = Date.valueOf(progrmEnddeString);
			serviceInfoDTO.setProgrmEndde(progrmEndde);

			// actBeginTm
			Elements actBeginTmElements = doc.getElementsByTag("actBeginTm");
			String actBeginTmString = actBeginTmElements.first().text();
			System.out.println(actBeginTmString.length());
			if(actBeginTmString.length() == 1){
				actBeginTmString = "0"+actBeginTmString+":00";
			}else{
				actBeginTmString+=":00:00";
			}

			LocalTime actBeginTm = LocalTime.parse(actBeginTmString); // parseTime()은 문자열을 시간으로 변환하는 사용자 정의 메서드입니다.
			serviceInfoDTO.setActBeginTm(actBeginTm);

// actEndTm
			Elements actEndTmElements = doc.getElementsByTag("actEndTm");
			String actEndTmString = actEndTmElements.first().text();
			if(actEndTmString.length() == 1){
				actEndTmString = "0"+actEndTmString+":00";
			}else{
				actEndTmString+=":00:00";
			}
			LocalTime actEndTm = LocalTime.parse(actEndTmString);
			serviceInfoDTO.setActEndTm(actEndTm);

// noticeBgnde
			Elements noticeBgndeElements = doc.getElementsByTag("noticeBgnde");
			String noticeBgndeString = noticeBgndeElements.first().text();
			String noticeBgndeFormatted = noticeBgndeString.substring(0, 4) + "-" + noticeBgndeString.substring(4, 6) + "-" + noticeBgndeString.substring(6);
			Date noticeBgnde = Date.valueOf(noticeBgndeFormatted);
			serviceInfoDTO.setNoticeBgnde(noticeBgnde);

// noticeEndde
			Elements noticeEnddeElements = doc.getElementsByTag("noticeEndde");
			String noticeEnddeString = noticeEnddeElements.first().text();
			String noticeEnddeFormatted = noticeEnddeString.substring(0, 4) + "-" + noticeEnddeString.substring(4, 6) + "-" + noticeEnddeString.substring(6);
			Date noticeEndde = Date.valueOf(noticeEnddeFormatted);
			serviceInfoDTO.setNoticeEndde(noticeEndde);


// rcritNmpr
			Elements rcritNmprElements = doc.getElementsByTag("rcritNmpr");
			int rcritNmpr = Integer.parseInt(rcritNmprElements.first().text());
			serviceInfoDTO.setRcritNmpr(rcritNmpr);

// sidoCd
			Elements sidoCdElements = doc.getElementsByTag("sidoCd");
			int sidoCd = Integer.parseInt(sidoCdElements.first().text());
			serviceInfoDTO.setSidoCd(sidoCd);
			System.out.println("실행결과");
			System.out.println(serviceInfoDTO.toString());
            dao.insertServiceInfo(serviceInfoDTO);

		}

	}

}