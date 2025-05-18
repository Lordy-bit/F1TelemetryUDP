import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
public class Server {
    static byte[] data;

    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(20777);
        byte[] event = new byte[1257];
        DatagramPacket packet = new DatagramPacket(event, event.length);
        server.receive(packet);
        data = packet.getData();
        Header header = new Header();
        header.loadAndprintInfo(data);

        CarStatus cs = new CarStatus(header.getPlayerIndex());

        for (int i = 0; i < 10000000; i++){
            server.receive(packet);
            data = packet.getData();
            header.loadInfo(data);
            if (header.getID() == 7){
                cs.loadInfo(data);
                System.out.println("ERS: "+cs.getErsStoreEnergyPercent(2)+" %");
                System.out.println("ERS Deployed: "+cs.getErsDeployedThisLapPercent(2)+" %");
            }
        }
    }
}
