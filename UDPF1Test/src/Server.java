import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(20777);
        byte[] event = new byte[1257];
        DatagramPacket packet = new DatagramPacket(event, event.length);
        server.receive(packet);
        byte[] data = packet.getData();
        Header header = new Header();
        header.loadAndprintInfo(data);

      // for (int i = 0; i < data.length; i++) {
      //     System.out.println(data[i]);
      // }


        while (header.getID() != 4){
            server.receive(packet);
            data = packet.getData();
            header.loadInfo(data);
        }
        System.out.println("");
        header.printInfo();
        System.out.println("");
        Participant player = new Participant(header.getPlayerIndex());
        player.loadInfo(data);
        player.printInfo();

        System.out.println("");
        Participant p1 = new Participant(3);
        Participant p2 = new Participant(1);
        Participant p3 = new Participant(0);
        p1.loadInfo(data);
        p2.loadInfo(data);
        p3.loadInfo(data);
        p1.printInfo();
        System.out.println("");
        p2.printInfo();
        System.out.println("");
        p3.printInfo();

        char s = 'Ä';
        System.out.println((int)s);


        Event e = new Event();
    }
}
