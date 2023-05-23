import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import persistence.dto.*;
public class Server {
    public static void main(String[] args) {
        try {
            // 서버 소켓 생성
            ServerSocket serverSocket = new ServerSocket(1234);

            // 클라이언트 연결 대기
            System.out.println("서버가 클라이언트 연결을 기다리고 있습니다...");
            Socket socket = serverSocket.accept();
            System.out.println("클라이언트가 연결되었습니다.");

            // 객체를 전송하기 위한 스트림 생성
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            // 객체 생성 및 직렬화
           ServiceInfoDTO serviceInfoDTO = new ServiceInfoDTO();
           serviceInfoDTO.setServiceInfoPK(1234);
           serviceInfoDTO.setSrvcCLCode("예시");
           serviceInfoDTO.setSrvcCSCode("예시 상세분야");
           serviceInfoDTO.setEmail("as102302@naver.com");
           outputStream.writeObject(serviceInfoDTO);
            serviceInfoDTO = (ServiceInfoDTO) inputStream.readObject();
            System.out.println("서버에서 받은 객체 정보:");
            System.out.println("이름: " + serviceInfoDTO.toString());
            //outputStream.flush();
            System.out.println("객체를 클라이언트로 전송하였습니다.");

            // 연결 종료
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
