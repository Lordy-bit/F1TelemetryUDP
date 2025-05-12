import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private SteeringWheelPanel Jsteer;

    //terza colonna, brake & throttle
    private JPanel JrpmHigh;
    private RotatedProgressBar JbrakeBar;
    private RotatedProgressBar JthrottleBar;

    //terza colonna, laps
    private JLabel JLastLapInMS;
    private JLabel JbestLapInMS;
    private JLabel JoptimalLapInMS;

    private CarTelemetry ct;
    private LapData ld;

    //queste verrano usare per non occupare troppa memoria
    private static final Color GRIN = new Color(24, 203, 0);
    private static final Color GREI = new Color(75, 75, 75);
    private static final Color BLU = new Color(34, 0, 203);
    private static final Color RAD = new Color(167,0,0);
    private static final Color BG = new Color(6, 8, 20);

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
        Jdrsls.setBackground(GREI);
        Jdrsls.setVisible(true);
        JrpmLow = new JPanel();
        JrpmLow.setBackground(GREI);
        JrpmLow.setVisible(true);
        JrpmHigh = new JPanel();
        JrpmHigh.setBackground(GREI);
        JrpmHigh.setVisible(true);
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
        Jsteer = new SteeringWheelPanel("Resources/steeringWheel.png",0);
        Jsteer.setVisible(true);
        JLastLapInMS = new JLabel("nope", SwingConstants.CENTER);
        JbestLapInMS = new JLabel("nope", SwingConstants.CENTER);
        JoptimalLapInMS = new JLabel("nope", SwingConstants.CENTER);

        JProgressBar barBrake = new JProgressBar();
        JbrakeBar = new RotatedProgressBar(barBrake);
        JbrakeBar.setValue(0);
        barBrake.setStringPainted(false);
        barBrake.setForeground(GRIN);
        barBrake.setBackground(BG);
        RotatedProgressBar Jbrake = new RotatedProgressBar(barBrake);
        JProgressBar barThrottle = new JProgressBar();
        JthrottleBar = new RotatedProgressBar(barThrottle);
        JthrottleBar.setValue(0);
        barThrottle.setStringPainted(false);
        barThrottle.setForeground(RAD);
        barThrottle.setBackground(BG);
        RotatedProgressBar Jthrottle = new RotatedProgressBar(barThrottle);

        JLabel steer = new JLabel();
        steer.setVisible(true);

        //dashboard
        Container pane = getContentPane();
        pane.setBackground(BG);
        pane.setLayout(new GridBagLayout());

        buildDashBoard(pane);
    }

    public void start() {
        startUpdater();
        setSize(900, 600);
        setMinimumSize(new Dimension(900, 600));
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
        customFont = customFont.deriveFont(Font.PLAIN, 24f); // Adjust style and size as needed
        for (int i = 0; i < labels.length; i++) {
            setUpLabel(labels[i], customFont);
        }
        FontMetrics fm = Jrpm.getFontMetrics(Jrpm.getFont());
        int width = fm.stringWidth("88888888 RPM"); // la combinazione più larga che può apparire
        int height = fm.getHeight();
        Jrpm.setPreferredSize(new Dimension(width, height));
        //x --> in che colonna è
        //y --> in che riga è
        //w --> numero righe
        //h --> numero colonne
        //wx --> altezza
        //wy --> larghezza

        int row = 0;

        //la prima colonna

        GridBagConstraints gbc0 = new GridBagConstraints();
        JPanel col0 = new JPanel();
        col0.setLayout(new GridBagLayout());
        col0.setOpaque(false);
        gbc0.fill = GridBagConstraints.BOTH;
        gbc0.gridy = 0;
        gbc0.gridx = 0;
        gbc0.gridheight = 10;
        gbc0.weightx = 3.0;
        gbc0.weighty = 3.0;

        row = 10;

        gbc0.fill = GridBagConstraints.HORIZONTAL;

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        JLabel tempsTitle = new JLabel("Temps", SwingConstants.CENTER);
        setUpLabel(tempsTitle, customFont);
        col0.add(tempsTitle, gbc0);
        row += 4;

        JPanel tempsInnerFront = new JPanel(new GridLayout(1, 2));
        tempsInnerFront.setOpaque(false);
        tempsInnerFront.add(JtyresInnerTemperatureFL);
        tempsInnerFront.add(JtyresInnerTemperatureFR);

        JPanel tempsSurfaceFront = new JPanel(new GridLayout(1, 2));
        tempsSurfaceFront.setOpaque(false);
        tempsSurfaceFront.add(JtyresSurfaceTemperatureFL);
        tempsSurfaceFront.add(JtyresSurfaceTemperatureFR);

        JPanel tempsInnerRear = new JPanel(new GridLayout(1, 2));
        tempsInnerRear.setOpaque(false);
        tempsInnerRear.add(JtyresInnerTemperatureRL);
        tempsInnerRear.add(JtyresInnerTemperatureRR);

        JPanel tempsSurfaceRear = new JPanel(new GridLayout(1, 2));
        tempsSurfaceRear.setOpaque(false);
        tempsSurfaceRear.add(JtyresSurfaceTemperatureRL);
        tempsSurfaceRear.add(JtyresSurfaceTemperatureRR);

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        col0.add(tempsInnerFront, gbc0);
        row += 4;

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        col0.add(tempsSurfaceFront, gbc0);
        row += 12;

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        col0.add(tempsInnerRear, gbc0);
        row += 4;

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        col0.add(tempsSurfaceRear, gbc0);
        row += 4;

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        JLabel pressTitle = new JLabel("Press", SwingConstants.CENTER);
        setUpLabel(pressTitle, customFont);
        col0.add(pressTitle, gbc0);
        row += 4;

        JPanel pressFront = new JPanel(new GridLayout(1, 2));
        pressFront.setOpaque(false);
        pressFront.add(JtyresPressureFL);
        pressFront.add(JtyresPressureFR);

        JPanel pressRear = new JPanel(new GridLayout(1, 2));
        pressRear.setOpaque(false);
        pressRear.add(JtyresPressureRL);
        pressRear.add(JtyresPressureRR);

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        col0.add(pressFront, gbc0);
        row += 4;

        gbc0.gridy = row;
        gbc0.gridheight = 4;
        gbc0.weighty = 4.0;
        col0.add(pressRear, gbc0);
        row += 4;

        //la seconda colonna

        GridBagConstraints gbc1 = new GridBagConstraints();
        JPanel col1 = new JPanel();
        col1.setLayout(new GridBagLayout());
        col1.setOpaque(false);
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.gridy = 0;
        gbc1.gridheight = 10;
        gbc1.weighty = 10.0;
        gbc1.fill = GridBagConstraints.HORIZONTAL;

        row = 10;

        gbc1.gridy = row;
        gbc1.weighty = 16;
        col1.add(Jspeed, gbc1);
        row += 10;

        gbc1.gridy = row;
        gbc1.gridheight = 4;
        gbc1.weighty = 16;
        col1.add(Jgear, gbc1);
        row += 10;

        gbc1.gridy = row;
        gbc1.gridheight = 4;
        gbc1.weighty = 16;
        col1.add(Jrpm, gbc1);
        row += 10;

        gbc1.gridy = row;
        gbc1.gridheight = 4;
        gbc1.weighty = 16;
        col1.add(Jsteer, gbc1);
        row += 10;

        //la terza colonna

        GridBagConstraints gbc2 = new GridBagConstraints();
        JPanel col2 = new JPanel();
        col2.setLayout(new GridBagLayout());
        col2.setOpaque(false);
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridy = 0;
        gbc2.gridheight = 10;
        gbc2.weighty = 30.0;
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        gbc2 = new GridBagConstraints();
        gbc2.weightx = 3;
        row = 10;

        gbc2.gridy = row;
        gbc2.gridheight = 4;
        gbc2.weighty = 16;
        GridLayout titleLayout = new GridLayout(1, 2);
        titleLayout.setHgap(40);
        JPanel title = new JPanel(titleLayout);
        title.setOpaque(false);
        JLabel brk = new JLabel("BRK");
        setUpLabel(brk, customFont);
        JLabel thr = new JLabel("THR");
        setUpLabel(thr, customFont);
        title.add(brk);
        title.add(thr);
        col2.add(title, gbc2);
        row += 10;

        gbc2.gridy = row;
        gbc2.gridheight = 4;
        gbc2.weighty = 16;
        GridLayout barsLayout = new GridLayout(1, 2);
        barsLayout.setHgap(70);
        JPanel bars = new JPanel(barsLayout);
        bars.setOpaque(false);
        bars.add(JbrakeBar);
        bars.add(JthrottleBar);
        col2.add(bars, gbc2);
        row += 10;

        gbc2.gridy = row;
        gbc2.gridheight = 2;
        gbc2.weighty = 8;
        col2.add(JLastLapInMS, gbc2);
        row += 10;

        gbc2.gridy = row;
        gbc2.gridheight = 2;
        gbc2.weighty = 8;
        col2.add(JbestLapInMS, gbc2);
        row += 10;

        gbc2.gridy = row;
        gbc2.gridheight = 2;
        gbc2.weighty = 8;
        col2.add(JoptimalLapInMS, gbc2);
        row += 10;

        GridBagConstraints c = new GridBagConstraints();

        //aggingo Jdrls, JrpmLow e JrpmHigh
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.2;

        c.gridx = 0;
        c.weightx = 1.0;
        add(Jdrsls, c);

        c.gridx = 1;
        c.weightx = 1.0;
        add(JrpmLow, c);

        c.gridx = 2;
        c.weightx = 1.0;
        add(JrpmHigh, c);

        //aggiungo le 3 colonne
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;

        c.gridx = 0;
        c.weightx = 1.0;
        add(col0, c);

        c.gridx = 1;
        c.weightx = 1.0;
        add(col1, c);

        c.gridx = 2;
        c.weightx = 1.0;
        add(col2, c);
    }

    private void setUpLabel(JLabel label, Font f) {
        label.setFont(f);
        label.setForeground(Color.WHITE);
        label.setVisible(true);
    }

    private void setTempsColor() {
        if (ct.innerTempsInWindow(85,100)) {
            JtyresInnerTemperatureFL.setForeground(Color.WHITE);
            JtyresInnerTemperatureFR.setForeground(Color.WHITE);
            JtyresInnerTemperatureRL.setForeground(Color.WHITE);
            JtyresInnerTemperatureRR.setForeground(Color.WHITE);
        } else {
            JtyresInnerTemperatureFL.setForeground(Color.YELLOW);
            JtyresInnerTemperatureFR.setForeground(Color.YELLOW);
            JtyresInnerTemperatureRL.setForeground(Color.YELLOW);
            JtyresInnerTemperatureRR.setForeground(Color.YELLOW);
        }
    }

    private void setSpeed() {
        Jspeed.setText("" + ct.getSpeed());
    }

    private void setThrottle() { JthrottleBar.setValue(ct.getThrottlePercent());}

    private void setBrake() { JbrakeBar.setValue(ct.getBrakePercent());}

    private void setDrsls() {
        if (ct.DRSisOn()) {
            Jdrsls.setBackground(GRIN);
        } else {
            Jdrsls.setBackground(GREI);
        }
    }

    private void setRpmLow() {
        if (ct.getRevLightsPercent() > 0) {// && ct.getRevLightsPercent() < 60) {
            JrpmLow.setBackground(RAD);
        } else {
            JrpmLow.setBackground(GREI);
        }
    }

    private void setRpmHigh() {
        if (ct.getRevLightsPercent() > 60) {
            JrpmHigh.setBackground(BLU);
        } else {
            JrpmHigh.setBackground(GREI);
        }
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
        JtyresPressureRL.setText("" + Math.floor(ct.getM_tyresPressureRL()));
    }
    private void setTyresPressureRR() {
        JtyresPressureRR.setText("" + Math.floor(ct.getM_tyresPressureRR()));
    }

    private void setTyresPressureFL() {
        JtyresPressureFL.setText("" + Math.floor(ct.getM_tyresPressureFL()));
    }

    private void setTyresPressureFR() {
        JtyresPressureFR.setText("" + Math.floor(ct.getM_tyresPressureFR()));
    }

    private void setLastLapTimeInMS() { JLastLapInMS.setText("Last: " + ld.timeToString(ld.getLastLapTimeInMS()));}

    private void setBestLapTimeInMS() { JbestLapInMS.setText("Best: " + ld.timeToString(ld.getBestLapTimeInMS()));}

    private void setOptiLapTimeInMS() { JoptimalLapInMS.setText("Opti: " + ld.timeToString(ld.getOptimalLapTimeMS()));}

    private void setRpm() {
        Jrpm.setText(ct.getEngineRPM() + " RPM");
    }

    private void setSteer() {
        Jsteer.setValue(ct.getSteerInRad());
    }

    private void updateGUI() {
        setSpeed();
        setGear();
        setRpm();
        setDrsls();
        setRpmLow();
        setRpmHigh();
        setSteer();
        setTempsColor();
        setTyresInnerTemperatureFL();
        setTyresInnerTemperatureFR();
        setTyresInnerTemperatureRL();
        setTyresInnerTemperatureRR();
        setTyresSurfaceTemperatureFL();
        setTyresSurfaceTemperatureFR();
        setTyresSurfaceTemperatureRL();
        setTyresSurfaceTemperatureRR();
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