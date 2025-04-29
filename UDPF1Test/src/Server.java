import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
public class Server {
    static byte [] data;
    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(20777);
        byte[] event = new byte[1257];
        DatagramPacket packet = new DatagramPacket(event, event.length);
        server.receive(packet);
        data = packet.getData();
        Header header = new Header();
        header.loadAndprintInfo(data);

        CarTelemetry cl = new CarTelemetry(header.getPlayerIndex());
        SwingUtilities.invokeLater(() -> {
            TelemetryGUI gui = new TelemetryGUI(cl);
            gui.setSize(500, 400);
            gui.setResizable(false);
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });


        LapData ld = new LapData(header.getPlayerIndex());
        Event ev = new Event();
        Event.FastestLap fl = ev.new FastestLap();


        Timer timer3 = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    server.receive(packet);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                data = packet.getData();
                header.loadInfo(data);

                if(header.getID() == 3){
                    ev.loadEventCode(data);
                    if(fl.getEventCode().equals(ev.getEventCode())){
                        fl.loadInfo(data);
                        System.out.println("FASTEST LAP: "+fl.getLapTime());
                    }

                }

                if(header.getID() == 6){
                    cl.loadInfo(data);
                }
                if(header.getID() == 2){
                    ld.loadInfo(data);
                    System.out.println("BS1: "+ld.getBestSector1TimeInMS()+"\t"+"BS2: "+ld.getBestSector2TimeInMS()+"\t"+"BS3: "+ld.getBestSector3TimeInMS());
                    System.out.println("last lap: "+ld.timeToString(ld.getLastLapTimeInMS()));
                    System.out.println("best lap: "+ld.timeToString(ld.getBestLapTimeInMS()));
                    System.out.println("optimal: "+ld.timeToString(ld.getOptimalLapTimeMS()));
                    System.out.println("S: "+ld.getSector());
                }

            }
        });
        timer3.start();
        }

}
