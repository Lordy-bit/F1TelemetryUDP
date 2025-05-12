import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RotatedProgressBar extends JComponent {
    private final JProgressBar bar;
    private Font customFont = new Font("Arial", Font.BOLD, 14);

    public RotatedProgressBar(JProgressBar bar) {
        this.bar = bar;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/Formula1-Bold.ttf"));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        customFont = customFont.deriveFont(Font.PLAIN, 15f);
        setPreferredSize(new Dimension(40, 100)); // Larghezza x Altezza (pre-rotazione)
    }

    public void setValue(int value) {
        bar.setValue(value);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Buffer grafico per evitare modifiche globali
        Graphics2D g2 = (Graphics2D) g.create();

        // Rotazione della barra: sposta il punto 0,0 in basso a sinistra e ruota
        g2.translate(0, getHeight());
        g2.rotate(Math.toRadians(-90));

        // Imposta la dimensione della barra come se fosse orizzontale (perché ora lo è)
        bar.setSize(getHeight(), getWidth());
        bar.setPreferredSize(new Dimension(getHeight(), getWidth()));
        bar.paint(g2);

        // Disegna testo centrato sopra (nella trasformazione attuale)
        String text = bar.getValue() + "%";
        g2.setFont(customFont);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        // Centra il testo
        int x = (getHeight() - textWidth) / 2;
        int y = (getWidth() + textHeight) / 2;

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        g2.dispose(); // Pulisce il buffer grafico
    }

    @Override
    public Dimension getPreferredSize() {
        // Larghezza logica e altezza logica (per layout manager)
        Dimension barSize = bar.getPreferredSize();
        return new Dimension(25, 200);
    }
}
