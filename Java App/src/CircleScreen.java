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
    private static final int POINT_SIZE = 5;

    List<Pt> points = new ArrayList<>();

    int forwarddelta;

    public float calibrateValue = 0;

    public CircleScreen() {
        super(true);
        this.setPreferredSize(new Dimension(getWidth(), getWidth()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        List<Pt> pointsclone = new ArrayList<>(points);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int a = getWidth() / 2;
        int b = getHeight() / 2;

            Iterator<Pt> pointsIterator = pointsclone.iterator();
            int currentPoint = 0;
            while (pointsIterator.hasNext()) {
                Pt p = pointsIterator.next();
                    double t = 2 * Math.PI * currentPoint / STEP_VALUE + calibrateValue;

                    if(p.x == 0 && p.y == 0) {
                        p.x = (int) Math.round(a + p.distance * Math.cos(t));
                        p.y = (int) Math.round(b + p.distance * Math.sin(t)) - forwarddelta;
                    }
                    Color tmpcolor = new Color(p.color.getRed(), p.color.getGreen(),
                            p.color.getBlue(), p.alpha < 0? 0:p.alpha);
                    p.alpha -= 50;
                    g2d.setColor(tmpcolor);
                    g2d.fillOval(p.x, p.y, POINT_SIZE, POINT_SIZE);
                    currentPoint++;
            }
        }
    }