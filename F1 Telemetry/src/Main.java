import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {
    static byte [] data;
    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(20777);   //UDP port
        byte[] event = new byte[1347];
        DatagramPacket packet = new DatagramPacket(event, event.length);
        server.receive(packet);
        data = packet.getData();
        Header header = new Header();
        header.loadInfo(data);
        CarTelemetry ct = new CarTelemetry(header.getPlayerIndex());
        LapData ld = new LapData(header.getPlayerIndex());

        //gui
        SwingUtilities.invokeLater(() -> {
            System.setProperty("sun.java2d.uiScale","1.0");
            Dashboard gui = new Dashboard(ct, ld);
            gui.start();
        });

        //data updater
        Timer timer3 = new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    server.receive(packet);
                    data = packet.getData();
                } catch (IOException ops) {
                    System.out.println("something went wrong");
                }
                header.loadInfo(data);
                if (header.getID() == 6) {
                    ct.loadInfo(data);

                } else if (header.getID() == 2) {
                    ld.loadInfo(data);
                }
            }
        });
        timer3.start();
    }
}
