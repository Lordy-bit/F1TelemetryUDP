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
import javax.swing.border.EmptyBorder;
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
    private RotatedProgressBar Jbrake;
    private RotatedProgressBar Jthrottle;

    //terza colonna, laps
    private JLabel JLastLapInMS;
    private JLabel JbestLapInMS;
    private JLabel JoptimalLapInMS;

    private CarTelemetry ct;
    private LapData ld;

    //queste verrano usare per non occupare troppa memoria
    private static final Color GRIN = new Color(24, 203, 0);
    private static final Color GREI = new Color(75, 75, 75);
    private static final Color RAD = new Color(167,0,0);

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
        JLastLapInMS = new JLabel("nope");
        JbestLapInMS = new JLabel("nope");
        JoptimalLapInMS = new JLabel("nope");
        Jdrsls.setVisible(true);
        JrpmLow.setVisible(true);
        JrpmHigh.setVisible(true);
        Jsteer.setVisible(true);
        JProgressBar barBrake = new JProgressBar();
        Jbrake = new RotatedProgressBar(barBrake);
        Jbrake.setValue(0);
        barBrake.setStringPainted(true);
        RotatedProgressBar Jbrake = new RotatedProgressBar(barBrake);
        JProgressBar barThrottle = new JProgressBar();
        Jthrottle = new RotatedProgressBar(barThrottle);
        Jthrottle.setValue(0);
        barBrake.setStringPainted(true);
        RotatedProgressBar Jthrottle = new RotatedProgressBar(barThrottle);

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
        customFont = customFont.deriveFont(Font.PLAIN, 32f); // Adjust style and size as needed
        for (int i = 0; i < labels.length; i++) {
            setUpLabel(labels[i], Color.PINK, Color.white, customFont);
        }
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
        c.gridy = 0;
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
        c.gridy = 0;
        c.gridheight = 10;
        c.weighty = 30.0;
        add(JrpmHigh, c);

        //la prima colonna

        row = 10;

        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        JLabel tempsTitle = new JLabel("Temps", SwingConstants.CENTER);
        setUpLabel(tempsTitle, Color.BLUE, Color.WHITE, customFont);
        add(tempsTitle, c);
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

        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        add(tempsInnerFront, c);
        row += 4;

        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        add(tempsSurfaceFront, c);
        row += 12;

        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        add(tempsInnerRear, c);
        row += 4;

        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        add(tempsSurfaceRear, c);
        row += 4;

        c.gridx = 0;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        JLabel pressTitle = new JLabel("Press", SwingConstants.CENTER);
        setUpLabel(pressTitle, Color.BLUE, Color.WHITE, customFont);
        add(pressTitle, c);
        row += 4;

        JPanel pressFront = new JPanel(new GridLayout(1, 2));
        pressFront.setOpaque(false);
        pressFront.add(JtyresPressureFL);
        pressFront.add(JtyresPressureFR);

        int banana = row;

        JPanel pressRear = new JPanel(new GridLayout(1, 2));
        pressRear.setOpaque(false);
        pressRear.add(JtyresPressureRL);
        pressRear.add(JtyresPressureRR);

        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        add(pressFront, c);
        row += 4;

        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 4.0;
        add(pressRear, c);
        row += 4;

        //la seconda colonna
        row = 10;

        c.gridx = 1;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 16;
        add(Jspeed, c);
        row += 10;

        c.gridx = 1;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 16;
        add(Jgear, c);
        row += 10;

        c.gridx = 1;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 16;
        add(Jrpm, c);
        row += 10;

        //la terza colonna
        row = 10;

        c.gridx = 2;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 16;
        JPanel bars = new JPanel(new GridLayout(1, 2));
        bars.add(Jbrake);
        bars.add(Jthrottle);
        add(bars, c);
        row += 10;

        c.gridx = 2;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 16;
        add(JLastLapInMS, c);
        row += 10;

        c.gridx = 2;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 16;
        add(JbestLapInMS, c);
        row += 10;

        c.gridx = 2;
        c.gridy = row;
        c.gridheight = 4;
        c.weighty = 16;
        add(JoptimalLapInMS, c);
        row += 10;
    }


    private void setUpLabel(JLabel label, Color bg, Color fg, Font f) {
        //label.setOpaque(true);
        //label.setBackground(bg);
        label.setForeground(fg);
        label.setFont(f);
        label.setVisible(true);
    }

    private void setSpeed() {
        Jspeed.setText("" + ct.getSpeed());
    }

    private void setThrottle() { Jthrottle.setValue(ct.getThrottlePercent());}

    private void setSteer() { ;} // for now it does nothing

    private void setBrake() { Jbrake.setValue(ct.getBrakePercent());}

    private void setDrsls() {
        if (ct.DRSisOn()) {
            Jdrsls.setBackground(GRIN);
        } else {
            Jdrsls.setBackground(GREI);
        }
    }

    private void setRpmLow() {
        byte rpmL = ct.getRevLightsPercent();
        if (rpmL > 87) {
            JrpmLow.setBackground(RAD);
        } else {
            JrpmLow.setBackground(GRIN);
        }
    }

    private void setRpmHIGH() {
        byte rpmH = ct.getRevLightsPercent();
        if (rpmH > 90) {
            JrpmLow.setBackground(RAD);
        } else {
            JrpmLow.setBackground(GRIN);
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

    private void updateGUI() {
        setSpeed();
        setGear();
        setRpm();
        setDrsls();
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

    private class RotatedProgressBar extends JComponent {
        private JProgressBar bar;

        public RotatedProgressBar(JProgressBar bar) {
            this.bar = bar;
        }

        public void setValue(int n) {
            bar.setValue(n);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Porta l'origine in basso a sinistra
            g2.translate(0, getHeight());
            // Ruota -90° per disporre la barra verticalmente, crescita dal basso verso l'alto
            g2.rotate(Math.toRadians(-90));
            // Imposta la dimensione della barra correttamente ruotata
            bar.setSize(getHeight(), getWidth());
            // Disegna la barra
            bar.paint(g2);
            g2.dispose();
        }


        @Override
        public Dimension getPreferredSize() {
            Dimension size = bar.getPreferredSize();
            return new Dimension(size.height, size.width);
        }
    }
}