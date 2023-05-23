package Network.Server;

public class VolunteerMain {
    public static void main(String[] main) {
        String host = "127.0.0.1";
        int port = 80;
        new VolunteerServer(host, port);
    }
}
