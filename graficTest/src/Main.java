import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    static int carIndex = 69;
    static int width = 500;
    static int height = 400;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelemetryGUI gui = new TelemetryGUI(carIndex);
            gui.setSize(500, 400);
            gui.setResizable(false);
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
}
