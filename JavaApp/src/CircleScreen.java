import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andrei on 3/31/17.
 */
public class CircleScreen extends JPanel {
    private static final float RPS = .5f;
    private static final float STEP_VALUE = 1/RPS * 1000.0f/35.0f;
    private static final int POINT_SIZE = 3;
    List<Integer> points = new ArrayList<>();

    public CircleScreen() {
        super(true);
        this.setPreferredSize(new Dimension(getWidth(), getWidth()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        List<Integer> pointsclone = new ArrayList<>(points);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.blue);
        int a = getWidth() / 2;
        int b = getHeight() / 2;

            Iterator<Integer> pointsIterator = pointsclone.iterator();
            int currentPoint = 0;
            while (pointsIterator.hasNext()) {
                int p = pointsIterator.next();
                double t = 2 * Math.PI * currentPoint / STEP_VALUE;

                int x = (int) Math.round(a + p * Math.cos(t));
                int y = (int) Math.round(b + p * Math.sin(t));
                g2d.fillOval(x, y, POINT_SIZE, POINT_SIZE);
                currentPoint++;
            }
        }
}