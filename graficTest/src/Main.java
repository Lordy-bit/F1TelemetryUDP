import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        int carIndex = 69;
        SwingUtilities.invokeLater(() -> {
            TelemetryGUI gui = new TelemetryGUI(carIndex);
            gui.setSize(500, 400);
            gui.setResizable(false);
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
}
