import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    JLabel Jspeed;
    JLabel Jgear;
    JLabel Jrpm;
    JLabel Jtemp;
    private CarTelemetry ct;

    public Dashboard(CarTelemetry ct) {
        this.ct = ct;
        init();
    }

    private void init() {
        //pane
        Container pane = getContentPane();
        pane.setBackground(Color.blue);

        //window
        setTitle("Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //speed, gear and RPM labels
        Jspeed = new JLabel("69");
        Jgear = new JLabel("0");
        Jrpm = new JLabel("0");
        Jtemp = new JLabel("0");
        JLabel[] labels = new JLabel[]{Jspeed, Jgear, Jrpm, Jtemp};
        addJLabels(labels, pane);

        startUpdater();
    }

    public void start() {
        startUpdater();
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addJLabels(JLabel[] labels, Container pane) {
        if (labels == null) {
            return;
        }
        for (JLabel label : labels) {
            add(label, pane);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setAlignmentY(Component.CENTER_ALIGNMENT);
            label.setVisible(true);
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

    private void setTemp(byte temp) { Jtemp.setText("" + temp); }

    private void updateGUI() {
        setSpeed(ct.getSpeed());
        setGear(ct.getGear());
        setRpm(ct.getEngineRPM());
        setTemp(ct.getTyresSurfaceTemperatureFL());
    }

    private void startUpdater() {
        Timer timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateGUI();
            }
        });
        timer.start();
    }
}