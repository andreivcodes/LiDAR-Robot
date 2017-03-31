import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by andrei on 3/31/17.
 */
public class CircleScreen extends JPanel {

    private static final int SIZE = 256;
    private static final int POINT_SIZE = 5;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;
    private int[] points;
    Random rng;
    private static final int RNG_SIZE = 75;
    /**
     * @param n the desired number of circles.
     */
    public CircleScreen(int n) {
        super(true);
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        this.n = n;
        rng = new Random();
        points = new int[n];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.blue);
        a = getWidth() / 2;
        b = getHeight() / 2;
        int m = Math.min(a, b);

        for (int i = 0; i < n; i++) {
            r = 4 * m / 5;


            int k = rng.nextInt(RNG_SIZE) + 1;
            r = r+k;
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(a + r * Math.cos(t));
            int y = (int) Math.round(b + r * Math.sin(t));
            g2d.fillOval(x, y,  POINT_SIZE, POINT_SIZE);
        }
    }
}