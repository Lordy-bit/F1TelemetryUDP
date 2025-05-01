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
    //prima colonna
    private JPanel Jdrsls;
    private JLabel  JtyresInnerTemperatureFL;
    private JLabel  JtyresInnerTemperatureFR;
    private JLabel  JtyresInnerTemperatureRL;
    private JLabel  JtyresInnerTemperatureRR;

    //prima colonna
    private JLabel  JtyresPressureFL;
    private JLabel  JtyresPressureFR;
    private JLabel  JtyresPressureRL;
    private JLabel  JtyresPressureRR;

    //seconda colonna
    private JPanel JrpmLow;
    private JLabel Jspeed;
    private JLabel Jgear;
    private JLabel Jrpm;
    private JPanel Jsteer;

    //terza colonna, brake & throttle
    private JPanel JrpmHigh;
    private JLabel Jbrake;
    private JLabel Jthrottle;

    //terza colonna, laps
    private JLabel JLastLapInMS;
    private JLabel JbestLapInMS;
    private JLabel JoptimalLapInMS;

    private CarTelemetry ct;
    private LapData ld;

    public Dashboard(CarTelemetry ct, LapData ld) {
        this.ct = ct;
        this.ld = ld;
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
    }

    private void init(String title) {
        //window
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //components

        Jdrsls = new JPanel();
        Jdrsls.setBackground(new Color(75,75,75));
        JrpmLow = new JPanel();
        JrpmLow.setBackground(new Color(75, 75, 75));
        JrpmHigh = new JPanel();
        JrpmHigh.setBackground(new Color(75, 75, 75));
        JtyresInnerTemperatureFL = new JLabel("0");
        JtyresInnerTemperatureFR = new JLabel("0");
        JtyresInnerTemperatureRL = new JLabel("0");
        JtyresInnerTemperatureRR = new JLabel("0");
        JtyresPressureFL = new JLabel("0");
        JtyresPressureFR = new JLabel("0");
        JtyresPressureRL = new JLabel("0");
        JtyresPressureRR = new JLabel("0");
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
    }

    public void start() {
        startUpdater();
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildDashBoard(Container pane) {
        JLabel[] labels = new JLabel[]{
                JtyresInnerTemperatureFL,
                JtyresInnerTemperatureFR ,
                JtyresInnerTemperatureRL,
                JtyresInnerTemperatureRR,
                JtyresPressureFL,
                JtyresPressureFR,
                JtyresPressureRL,
                JtyresPressureRR,
                Jspeed,
                Jgear,
                Jrpm,
                Jbrake,
                Jthrottle,
                JLastLapInMS,
                JbestLapInMS,
                JoptimalLapInMS};

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 3;
        c.gridheight = 100;

        //colonna 1
        c.gridy = 1;
        c.gridx = 1;

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

    private void setThrottle(float throttle) { Jthrottle.setText("" + throttle);}

    private void setSteer(float steer) { ;} // for now it does nothing

    private void setBrake(float brake) { Jbrake.setText("" + brake);}

    private void setDrsls(boolean isOn) {
        if (isOn) {
            Jdrsls.setBackground(new Color(24, 203, 0));
        }
        Jdrsls.setBackground(new Color(75, 75, 75));
    }

    private void setTyresSurfaceTemperatureRL(byte tyresSurfaceTemperatureRL) {
        JtyresInnerTemperatureFL.setText("" + tyresSurfaceTemperatureRL);
    }
    private void setTyresSurfaceTemperatureRR(byte tyresSurfaceTemperatureRR) {
        JtyresInnerTemperatureFL.setText("" + tyresSurfaceTemperatureRR);
    }
    private void setTyresSurfaceTemperatureFL(byte tyresSurfaceTemperatureFL) {
        JtyresInnerTemperatureFL.setText("" + tyresSurfaceTemperatureFL);
    }
    private void setTyresSurfaceTemperatureFR(byte tyresSurfaceTemperatureFR) {
        JtyresInnerTemperatureFL.setText("" + tyresSurfaceTemperatureFR);
    }

    private void setTyresInnerTemperatureRL(byte tyresInnerTemperatureRL) {
        JtyresInnerTemperatureRL.setText("" + tyresInnerTemperatureRL);
    }
    private void setTyresInnerTemperatureRR(byte tyresInnerTemperatureRR) {
        JtyresInnerTemperatureRR.setText("" + tyresInnerTemperatureRR);
    }
    private void setTyresInnerTemperatureFL(byte tyresInnerTemperatureFL) {
        JtyresInnerTemperatureFL.setText("" + tyresInnerTemperatureFL);
    }
    private void setTyresInnerTemperatureFR(byte tyresInnerTemperatureFR) {
        JtyresInnerTemperatureFR.setText("" + tyresInnerTemperatureFR);
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

    private void setTyresPressureRL(float tyresPressureRL) {
        JtyresPressureRL.setText("" + tyresPressureRL);
    }

    private void setTyresPressureRR(float tyresPressureRR) {
        JtyresPressureRR.setText("" + tyresPressureRR);
    }

    private void setTyresPressureFL(float tyresPressureFL) {
        JtyresPressureFL.setText("" + tyresPressureFL);
    }

    private void setTyresPressureFR(float tyresPressureFR) {
        JtyresPressureFR.setText("" + tyresPressureFR);
    }

    private void setLastLapTimeInMS(int lastLapTimeInMS) { JLastLapInMS.setText(ld.timeToString(lastLapTimeInMS));}

    private void setBestLapTimeInMS(int bestLapInMS) { JbestLapInMS.setText(ld.timeToString(bestLapInMS));}

    private void setOptiLapTimeInMS(int optimalLapInMS) { JoptimalLapInMS.setText(ld.timeToString(optimalLapInMS));}

    private void setRpm(short rpm) {
        Jrpm.setText(rpm + " RPM");
    }

    private void updateGUI() {
        setSpeed(ct.getSpeed());
        setGear(ct.getGear());
        setRpm(ct.getEngineRPM());
        setTyresInnerTemperatureFL(ct.getTyresInnerTemperatureFL());
        setTyresInnerTemperatureFR(ct.getTyresInnerTemperatureFR());
        setTyresInnerTemperatureRL(ct.getTyresInnerTemperatureRL());
        setTyresInnerTemperatureRR(ct.getTyresInnerTemperatureRR());
        setTyresPressureFL(ct.getM_tyresPressureFL());
        setTyresPressureFR(ct.getM_tyresPressureFR());
        setTyresPressureRL(ct.getM_tyresPressureRL());
        setTyresPressureRR(ct.getM_tyresPressureRR());
        setBrake(ct.getBrake());
        setThrottle(ct.getThrottle());
        setLastLapTimeInMS(ld.getLastLapTimeInMS());
        setBestLapTimeInMS(ld.getBestLapTimeInMS());
        setOptiLapTimeInMS(ld.getOptimalLapTimeMS());
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