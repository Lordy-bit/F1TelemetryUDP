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

    private JLabel  JtyresSurfaceTemperatureFL;
    private JLabel  JtyresSurfaceTemperatureFR;
    private JLabel  JtyresSurfaceTemperatureRL;
    private JLabel  JtyresSurfaceTemperatureRR;

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
        Jdrsls.setBackground(Color.green);
        JrpmLow = new JPanel();
        JrpmLow.setBackground(Color.red);
        JrpmHigh = new JPanel();
        JrpmHigh.setBackground(Color.BLUE);
        Jsteer = new JPanel();
        Jsteer.setBackground(Color.yellow);
        JtyresInnerTemperatureFL = new JLabel("0", SwingConstants.CENTER);
        JtyresInnerTemperatureFR = new JLabel("0", SwingConstants.CENTER);
        JtyresInnerTemperatureRL = new JLabel("0", SwingConstants.CENTER);
        JtyresInnerTemperatureRR = new JLabel("0", SwingConstants.CENTER);
        JtyresSurfaceTemperatureFL = new JLabel("0", SwingConstants.CENTER);
        JtyresSurfaceTemperatureFR = new JLabel("0", SwingConstants.CENTER);
        JtyresSurfaceTemperatureRL = new JLabel("0", SwingConstants.CENTER);
        JtyresSurfaceTemperatureRR = new JLabel("0", SwingConstants.CENTER);
        JtyresPressureFL = new JLabel("0", SwingConstants.CENTER);
        JtyresPressureFR = new JLabel("0", SwingConstants.CENTER);
        JtyresPressureRL = new JLabel("0", SwingConstants.CENTER);
        JtyresPressureRR = new JLabel("0", SwingConstants.CENTER);
        Jspeed = new JLabel("0", SwingConstants.CENTER);
        Jgear = new JLabel("0", SwingConstants.CENTER);
        Jrpm = new JLabel("0", SwingConstants.CENTER);
        Jbrake = new JLabel("0", SwingConstants.CENTER);
        Jthrottle = new JLabel("0", SwingConstants.CENTER);
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
                JtyresSurfaceTemperatureFL,
                JtyresSurfaceTemperatureFR ,
                JtyresSurfaceTemperatureRL,
                JtyresSurfaceTemperatureRR,
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
        Jdrsls.setVisible(true);
        JrpmLow.setVisible(true);
        JrpmHigh.setVisible(true);
        Jsteer.setVisible(true);
        //x --> in che colonna è
        //y --> in che riga è
        //w --> numero righe
        //h --> numero colonne
        //wx --> altezza
        //wy --> larghezza

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        int row = 0;

        c.gridx = 0;
        c.gridy = row;
        c.gridheight = 10;
        c.weightx = 1.0;
        c.weighty = 10.0;
        add(Jdrsls, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 10;
        c.weighty = 10.0;
        add(JrpmLow, c);

        c.gridx = 2;
        add(JrpmHigh, c);

        row += 10;

        c.gridx = 0;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        JLabel tempsTitle = new JLabel("Temps:", SwingConstants.CENTER);
        setUpLabel(tempsTitle, Color.BLUE, Color.WHITE, customFont);
        add(tempsTitle, c);
        row += 4;

        JPanel row1 = new JPanel(new GridLayout(1, 2));
        row1.add(JtyresInnerTemperatureFL);
        row1.add(JtyresInnerTemperatureFR);

        JPanel row2 = new JPanel(new GridLayout(1, 2));
        row2.add(JtyresSurfaceTemperatureFL);
        row2.add(JtyresSurfaceTemperatureFR);

        JPanel row3 = new JPanel(new GridLayout(1, 2));
        row3.add(JtyresInnerTemperatureRL);
        row3.add(JtyresInnerTemperatureRR);

        JPanel row4 = new JPanel(new GridLayout(1, 2));
        row4.add(JtyresSurfaceTemperatureRL);
        row4.add(JtyresSurfaceTemperatureRR);

        for (JPanel pnl : new JPanel[]{ row1, row2, row3, row4 }) {
            c.gridy = row;
            c.gridheight = 4;
            c.weighty = 4.0;
            add(pnl, c);
            row += 4;
        }

        int usedUnits = 10 + 10 + 10 + 4 + 16;
        int remaining = 100 - usedUnits;

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weighty = remaining;
        add(Box.createVerticalStrut(0), c);
    }


    private void setUpLabel(JLabel label, Color bg, Color fg, Font f) {
        label.setOpaque(true);
        label.setBackground(bg);
        label.setForeground(fg);
        label.setFont(f);
        label.setVisible(true);
    }

    private void setSpeed() {
        Jspeed.setText("" + ct.getSpeed());
    }

    private void setThrottle() { Jthrottle.setText("" + ct.getThrottle());}

    private void setSteer() { ;} // for now it does nothing

    private void setBrake() { Jbrake.setText("" + ct.getBrake());}

    private void setDrsls() {
        if (ct.DRSisOn()) {
            Jdrsls.setBackground(new Color(24, 203, 0));
        }
        Jdrsls.setBackground(new Color(75, 75, 75));
    }

    private void setTyresSurfaceTemperatureRL() {
        JtyresSurfaceTemperatureRL.setText("" + ct.getTyresSurfaceTemperatureRL());
    }
    private void setTyresSurfaceTemperatureRR() {
        JtyresSurfaceTemperatureRR.setText("" + ct.getTyresSurfaceTemperatureRR());
    }
    private void setTyresSurfaceTemperatureFL() {
        JtyresSurfaceTemperatureFL.setText("" + ct.getTyresSurfaceTemperatureFL());
    }
    private void setTyresSurfaceTemperatureFR() {
        JtyresSurfaceTemperatureFR.setText("" + ct.getTyresSurfaceTemperatureFR());
    }

    private void setTyresInnerTemperatureRL() {
        JtyresInnerTemperatureRL.setText("" + ct.getTyresInnerTemperatureRL());
    }
    private void setTyresInnerTemperatureRR() {
        JtyresInnerTemperatureRR.setText("" + ct.getTyresInnerTemperatureRR());
    }
    private void setTyresInnerTemperatureFL() {
        JtyresInnerTemperatureFL.setText("" + ct.getTyresInnerTemperatureFL());
    }
    private void setTyresInnerTemperatureFR() {
        JtyresInnerTemperatureFR.setText("" + ct.getTyresInnerTemperatureFR());
    }

    private void setGear() {
        byte g = ct.getGear();
        if (g == -1) {
            Jgear.setText("N");
            return;
        }
        if (g == 0) {
            Jgear.setText("R");
            return;
        }
        Jgear.setText("" + g);
    }

    private void setTyresPressureRL() {
        JtyresPressureRL.setText("" + ct.getM_tyresPressureRL());
    }

    private void setTyresPressureRR() {
        JtyresPressureRR.setText("" + ct.getM_tyresPressureRR());
    }

    private void setTyresPressureFL() {
        JtyresPressureFL.setText("" + ct.getM_tyresPressureFL());
    }

    private void setTyresPressureFR() {
        JtyresPressureFR.setText("" + ct.getM_tyresPressureFR());
    }

    private void setLastLapTimeInMS() { JLastLapInMS.setText(ld.timeToString(ld.getLastLapTimeInMS()));}

    private void setBestLapTimeInMS() { JbestLapInMS.setText(ld.timeToString(ld.getBestLapTimeInMS()));}

    private void setOptiLapTimeInMS() { JoptimalLapInMS.setText(ld.timeToString(ld.getOptimalLapTimeMS()));}

    private void setRpm() {
        Jrpm.setText(ct.getEngineRPM() + " RPM");
    }

    private void updateGUI() {
        setSpeed();
        setGear();
        setRpm();
        setTyresInnerTemperatureFL();
        setTyresInnerTemperatureFR();
        setTyresInnerTemperatureRL();
        setTyresInnerTemperatureRR();
        setTyresPressureFL();
        setTyresPressureFR();
        setTyresPressureRL();
        setTyresPressureRR();
        setBrake();
        setThrottle();
        setLastLapTimeInMS();
        setBestLapTimeInMS();
        setOptiLapTimeInMS();
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