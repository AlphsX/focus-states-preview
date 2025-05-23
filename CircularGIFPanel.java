import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;

/**
 * The {@code CircularGIFPanel} class is a custom Swing component that displays a GIF image within a circular area.
 * It extends {@link JPanel} and overrides the {@code paintComponent} method to draw the GIF in a circular clip.
 * This class is used to display animated GIFs with a circular shape in the GUI.
 */
public class CircularGIFPanel extends JPanel {
    /** The GIF image to be displayed in the panel. */
    private Image gifImage;

    /**
     * Constructs a new {@code CircularGIFPanel} with the specified GIF image path.
     * It sets the background color, loads the GIF, and sets the preferred size of the panel.
     *
     * @param gifPath The file path to the GIF image to be displayed.
     */
    public CircularGIFPanel(String gifPath) {
        setBackground(Color.decode("#53c2ef"));
        loadGif(gifPath);
        setPreferredSize(new Dimension(200, 200));
    }

    /**
     * Sets a new GIF image to be displayed in the panel.
     * Loads the new GIF image from the specified path and repaints the panel.
     *
     * @param gifPath The file path to the new GIF image.
     */
    public void setGif(String gifPath) {
        loadGif(gifPath);
        repaint();
    }
    
    /**
     * Loads the GIF image from the specified file path.
     * Checks if the file exists before loading to prevent errors.
     *
     * @param gifPath The file path to the GIF image.
     */
    private void loadGif(String gifPath) {
        File gifFile = new File(gifPath);
        if (!gifFile.exists()) {
            System.err.println("GIF not found: " + gifPath);
            return;
        }
        gifImage = new ImageIcon(gifPath).getImage();
    }
    
    /**
     * Overrides the {@code paintComponent} method to draw the GIF image within a circular clip.
     * It sets anti-aliasing rendering hints, calculates the circular area, clips the graphics context,
     * draws the image, and then restores the clip and draws a border around the circle.
     *
     * @param g The {@link Graphics} object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gifImage != null) {
            Graphics2D g2 = (Graphics2D) g;
            // Enable anti-aliasing for smoother edges
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            
            // Calculate the diameter and position for the circle
            int diameter = Math.min(panelWidth, panelHeight);
            int x = (panelWidth - diameter) / 2;
            int y = (panelHeight - diameter) / 2 + 30; // Adjusted y-position

            // Create a circular shape for clipping
            Shape circle = new Ellipse2D.Double(x, y, diameter, diameter);
            // Set the clip to the circle shape
            g2.setClip(circle);
            // Draw the GIF image scaled to fit within the circle
            g2.drawImage(gifImage, x, y, diameter, diameter, this);

            // Reset the clip to default
            g2.setClip(null);
            // Set the color for the circle border
            g2.setColor(Color.WHITE);
            // Set the stroke width for the border
            g2.setStroke(new BasicStroke(8));
            // Draw the circle border
            g2.draw(circle);
        }
    }
}
