package Network.Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
    public static void main(String args[]) {
        Socket cliSocket = null;
        String host = "127.0.0.1";

        try {

            cliSocket = new Socket(host, 3333);
            ObjectOutputStream oos = new ObjectOutputStream((cliSocket.getOutputStream()));
            ObjectInputStream ois = new ObjectInputStream((cliSocket.getInputStream()));

            UserEventController userEventController = new UserEventController(oos, ois);

            userEventController.inquiryServiceList();

        }
        catch (UnknownHostException e) {
            System.err.println("서버를 찾을 수 없습니다");
        }
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                cliSocket.close();
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

