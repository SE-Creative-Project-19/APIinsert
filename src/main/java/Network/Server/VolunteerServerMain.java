package Network.Server;

public class VolunteerServerMain {
    public static void main(String[] main) {
        String host = "192.168.50.20";
        int port = 3333;
        new VolunteerServer(host, port);
    }
}
