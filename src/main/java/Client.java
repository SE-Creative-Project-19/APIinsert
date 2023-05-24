import java.io.*;
import java.net.Socket;
import persistence.dto.*;
public class Client {
    public static void main(String[] args) {
        try {
            // 서버에 연결
            Socket socket = new Socket("localhost", 1234);
            System.out.println("서버에 연결되었습니다.");

            // 객체를 수신하기 위한 스트림 생성
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // 객체 역직렬화 및 출력
           ServiceInfoDTO serviceInfoDTO = (ServiceInfoDTO) inputStream.readObject();
            System.out.println("서버에서 받은 객체 정보:");
            System.out.println("이름: " + serviceInfoDTO.toString());

            outputStream.writeObject(serviceInfoDTO);
            // 연결 종료

            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
