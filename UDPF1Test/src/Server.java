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



        Timer timer3 = new Timer(-1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    server.receive(packet);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                data = packet.getData();
                header.loadInfo(data);
                if(header.getID() == 6){
                    cl.loadInfo(data);
                }

            }
        });
        timer3.start();
        }

}
