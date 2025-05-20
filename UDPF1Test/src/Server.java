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
        LapData ld = new LapData(header.getPlayerIndex());
        Motion mo = new Motion(header.getPlayerIndex());

        for (int i = 0; i < 10000000; i++){
            server.receive(packet);
            data = packet.getData();
            header.loadInfo(data);
            if (header.getID() == 7){
                cs.loadInfo(data);
                System.out.println("ERS: "+cs.getErsStoreEnergyPercent(1)+" %");
                System.out.println("ERS Deployed: "+cs.getErsDeployedThisLapPercent(2)+" %");
            }
            if (header.getID() == 2){
                ld.loadInfo(data);
                System.out.println("BS1: "+ ld.getBestSector1TimeInMS()+"   "+"BS2: "+ ld.getBestSector2TimeInMS()+"   "+"BS3: "+ ld.getBestSector3TimeInMS());
                System.out.println("Opti Lap: "+ld.getOptimalLapTimeMS());
            }
            if (header.getID() == 0){
                mo.loadInfo(data);
                System.out.println("G Force: "+mo.getGForceXY(2));
            }
        }
    }
}
