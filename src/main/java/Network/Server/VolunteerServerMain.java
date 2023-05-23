package Network.Server;

public class VolunteerServerMain {
    public static void main(String[] main) {
        String host = "127.0.0.1";
        int port = 5555;
        new VolunteerServer(host, port);
    }
}
