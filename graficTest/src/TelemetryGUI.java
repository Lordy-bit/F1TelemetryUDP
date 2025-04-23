import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelemetryGUI extends JFrame {
    private JLabel Jspeed;
    private JLabel Jgear;
    private JLabel Jrpm;
    private CarTelemetry ct;

    public TelemetryGUI(int carIndex) {
        ct = new CarTelemetry(carIndex);
        init();
    }

    private void init() {
        //pane
        Container pane = getContentPane();

        //window
        setTitle("Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        //speed, gear and RPM labels
        Jspeed = new JLabel("0");
        Jgear = new JLabel("1");
        Jrpm = new JLabel("0");
        JLabel[] labels = new JLabel[]{Jspeed, Jgear, Jrpm};
        addJLabels(labels, pane);

        startUpdater();
    }

    private void addJLabels(JLabel[] labels, Container pane) {
        if (labels == null) {
            return;
        }
        for (int i = 0; i < labels.length; i ++) {
            add(labels[i], pane);
            labels[i].setVisible(true);
        }
    }

    private void setSpeed(short speed) {
        Jspeed.setText("" + speed);
    }

    private void setGear(byte gear) {
        Jgear.setText("" + gear);
    }

    private void setRpm(short rpm) {
        Jrpm.setText("" + rpm);
    }

    private void updateGUI() {
        setSpeed(ct.getSpeed());
        setGear(ct.getGear());
        setRpm(ct.getRpm());
    }

    private void startUpdater() {
        Timer timer = new Timer(17, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateGUI();
                ct.updateValues(); // ora per testing
            }
        });
        timer.start();
    }
}