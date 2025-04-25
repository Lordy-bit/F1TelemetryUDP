import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Accelleration {
    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(20777);
        byte[] event = new byte[1347];
        DatagramPacket packet = new DatagramPacket(event, event.length);
        server.receive(packet);
        byte[] data = packet.getData();
        Header header = new Header();
        header.loadInfo(data);
        CarTelemetry t1 = new CarTelemetry(header.getPlayerIndex());

        while (true){
            server.receive(packet);
            data = packet.getData();
            header.loadInfo(data);
            if(header.getID() == 6){
                t1.loadInfo(data);
                System.out.println(t1.m_speed);
            }
        }

    }

}
