import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Dashboard extends JFrame {
    //prima colonna, temps
    private JLabel TyresInnerTemperatureFL;
    private JLabel TyresInnerTemperatureFR;
    private JLabel TyresInnerTemperatureRL;
    private JLabel TyresInnerTemperatureRR;

    //prima colonna, pre = new JLabel("0)s
    private JLabel TyresPressureTemperatureFL;
    private JLabel TyresPressureTemperatureFR;
    private JLabel TyresPressureTemperatureRL;
    private JLabel TyresPressureTemperatureRR;

    //seconda colonn = new JLabel("0)a
    private JLabel Jspeed;
    private JLabel Jgear;
    private JLabel Jrpm;

    //terza colonna, brake & throttle
    private JLabel Jbrake;
    private JLabel Jthrottle;

    //terza colonna, laps
    private JLabel JLastLapInMS;
    private JLabel JbestLapInMS;
    private JLabel JoptimalLapInMS;

    private CarTelemetry ct;

    public Dashboard(CarTelemetry ct) {
        this.ct = ct;
        init("Dashboard - " + ct.getIndex());
        try{
            setIconImage(ImageIO.read(new File(".\\Resources\\icon.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public Dashboard(String title, CarTelemetry ct) {
        this.ct = ct;
        init(title);
        URL iconURL = getClass().getResource("C:\\Informatica\\F1TelemetryUDP\\graficTest\\src\\icon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());
    }

    private void init(String title) {
        //window
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //components
        TyresInnerTemperatureFL = new JLabel("0");
        TyresInnerTemperatureFR = new JLabel("0");
        TyresInnerTemperatureRL = new JLabel("0");
        TyresInnerTemperatureRR = new JLabel("0");
        TyresPressureTemperatureFL = new JLabel("0");
        TyresPressureTemperatureFR = new JLabel("0");
        TyresPressureTemperatureRL = new JLabel("0");
        TyresPressureTemperatureRR = new JLabel("0");
        Jspeed = new JLabel("0");
        Jgear = new JLabel("0");
        Jrpm = new JLabel("0");
        Jbrake = new JLabel("0");
        Jthrottle = new JLabel("0");
        JLastLapInMS = new JLabel("nope");
        JbestLapInMS = new JLabel("nope");
        JoptimalLapInMS = new JLabel("nope");

        //dashboard
        Container pane = getContentPane();
        pane.setBackground(new Color(6, 8, 20));
        pane.setLayout(new GridBagLayout());

        buildDashBoard(pane);

        startUpdater();
    }

    public void start() {
        startUpdater();
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildDashBoard(Container pane) {
        JLabel[] labels = new JLabel[]{TyresInnerTemperatureFL, TyresInnerTemperatureFR , TyresInnerTemperatureRL, TyresInnerTemperatureRR, TyresPressureTemperatureFL,TyresPressureTemperatureFR, TyresPressureTemperatureRL, TyresPressureTemperatureRR, Jspeed, Jgear, Jrpm, Jbrake, Jthrottle, JLastLapInMS, JbestLapInMS, JoptimalLapInMS};

        GridBagConstraints c = new GridBagConstraints();

        c.gridheight = 6;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 1;

        c.gridheight = 3;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(Jspeed, c);

        c.gridheight = 3;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(Jgear, c);

        c.gridheight = 3;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(Jrpm, c);

        //Initializing components
        Font customFont; //if it gives problem because not initialized, Font customFont = new JLabel.getFont();
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/Formula1-Bold.ttf"));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        customFont = customFont.deriveFont(Font.BOLD, 32f); // Adjust style and size as needed
        for (int i = 0; i < labels.length; i++) {
            setUpLabel(labels[i], Color.pink, Color.white, customFont);
        }
    }

    private void setUpLabel(JLabel label, Color bg, Color fg, Font f) {
        label.setOpaque(true);
        label.setBackground(bg);
        label.setForeground(fg);
        label.setFont(f);
        label.setVisible(true);
    }

    private void setSpeed(short speed) {
        Jspeed.setText("" + speed);
    }

    private void setGear(byte gear) {
        if (gear == -1) {
            Jgear.setText("N");
            return;
        }
        if (gear == 0) {
            Jgear.setText("R");
            return;
        }
        Jgear.setText("" + gear);
    }

    private void setRpm(short rpm) {
        Jrpm.setText(rpm + " RPM");
    }

    private void updateGUI() {
        setSpeed(ct.getSpeed());
        setGear(ct.getGear());
        setRpm(ct.getEngineRPM());
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