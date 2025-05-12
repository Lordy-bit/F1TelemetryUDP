import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SteeringWheelPanel extends JLabel {
    double value; //rotation in gradient (not the change)
    BufferedImage image;

    public SteeringWheelPanel(String imgPath, double value) {
        ImageIcon icon = new ImageIcon(imgPath);
        Image img = icon.getImage();
        int width = img.getWidth(null);
        int height = img.getHeight(null);

        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        this.image = buffered;
        this.value = value;

        setPreferredSize(new Dimension(width, height));

        setHorizontalAlignment(SwingConstants.CENTER);
    }


    public void setValue(double value) {
        this.value = value;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        Graphics2D g2 = (Graphics2D) g.create();

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(value);
        transform.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, transform, null);

        g2.dispose();
    }
}
