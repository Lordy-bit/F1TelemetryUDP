import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelemetryGUI extends JFrame {
    private JLabel Jspeed;
    private JLabel Jgear;
    private JLabel Jrpm;
    private CarTelemetry ct;

    public TelemetryGUI(CarTelemetry ct) {
        this.ct = ct;
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
        //startTestValueUpdate();
    }

    private void addJLabels(JLabel[] labels, Container pane) {
        if (labels == null) {
            return;
        }
        for (int i = 0; i < labels.length; i ++) {
            add(labels[i], pane);
            labels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            labels[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            labels[i].setVisible(true);
        }
    }

    private void setSpeed(short speed) {
        Jspeed.setText(speed + " km/h");
    }

    private void setGear(byte gear) {
        Jgear.setText("gear " + gear);
    }

    private void setRpm(short rpm) {
        Jrpm.setText(rpm + " rpm");
    }

    private void updateGUI() {
        setSpeed(ct.getSpeed());
        setGear(ct.getGear());
        setRpm(ct.getEngineRPM());
    }

    private void startUpdater() {
        Timer timer = new Timer(-1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateGUI();
            }
        });
        timer.start();
    }
}