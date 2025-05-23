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

        CarTelemetry ct = new CarTelemetry(0); //when real match --> header.getPlayerIndex());
        LapData ld = new LapData(0);//when real match --> header.getPlayerIndex());
        //gui
        SwingUtilities.invokeLater(() -> {
            System.setProperty("sun.java2d.uiScale","1.0");
            Dashboard gui = new Dashboard(ct, ld);
            gui.start();
        });

        //data updater
        Timer timer3 = new Timer(0, new ActionListener() {
            DataReader dr = new DataReader("data2.txt");
            public void actionPerformed(ActionEvent e) {
                try {
                    data = dr.read(0);
                } catch (IOException ops) {
                    System.out.println("ops");
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