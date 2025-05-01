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
        Header header = new Header();

        CarTelemetry cl = new CarTelemetry(header.getPlayerIndex());
        LapData lp = new LapData(header.getPlayerIndex());
        //gui
        SwingUtilities.invokeLater(() -> {
            System.setProperty("sun.java2d.uiScale","1.0");
            Dashboard gui = new Dashboard(cl, lp);
            gui.start();
        });

        //data updater
        Timer timer3 = new Timer(0, new ActionListener() {
            DataReader dr = new DataReader("data.txt");
            public void actionPerformed(ActionEvent e) {
                try {
                    data = dr.read(0);
                } catch (IOException ops) {
                    System.out.println("ops");
                }
                header.loadInfo(data);
                if (header.getID() == 6) { //&& header.getM_packetFormat() == 2021) {
                    cl.loadInfo(data);
                }
            }
        });
        timer3.start();
    }

}