import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by andrei on 1/4/17.
 */
public class MainForm extends JFrame implements KeyListener {

    private static MainForm instance;
    TCPClient client;
    CircleScreen circleScreen;

    private JTextField sendTextField;
    private JButton sendCharButton;
    private JPanel panel;
    private JPanel circlePane;
    private JButton screenshotButton;
    private JButton releaseButton;
    private JButton rotateButton;
    private JButton stopRotateButton;
    private JButton LFButton;
    private JButton RFButton;
    private JButton BFButton;
    private JButton LBButton;
    private JButton RBButton;
    private JButton BBButton;
    private JTextField distanceTextField;
    private JButton clear;

    private int screenshotCnt = 0;

    private MainForm() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                client.sendCommand(TCPClient.RELEASE_MOTORS);
                System.exit(0);
            }
        });

        sendTextField.addKeyListener(this);


        RFButton.addActionListener(e -> client.sendTravelDistance(TCPClient.WALK_FORWARD_RIGHT, distanceTextField.getText()));
        LFButton.addActionListener(e -> client.sendTravelDistance(TCPClient.WALK_FORWARD_LEFT, distanceTextField.getText()));
        BFButton.addActionListener(e -> client.sendTravelDistance(TCPClient.WALK_FORWARD_BOTH, distanceTextField.getText()));

        RBButton.addActionListener(e -> client.sendTravelDistance(TCPClient.WALK_BACKWARD_RIGHT, distanceTextField.getText()));
        LBButton.addActionListener(e -> client.sendTravelDistance(TCPClient.WALK_BACKWARD_LEFT, distanceTextField.getText()));
        BBButton.addActionListener(e -> client.sendTravelDistance(TCPClient.WALK_BACKWARD_BOTH, distanceTextField.getText()));

        sendCharButton.addActionListener(e -> client.sendString(sendTextField.getText()));
        releaseButton.addActionListener(e -> client.sendCommand(TCPClient.RELEASE_MOTORS));
        rotateButton.addActionListener(e -> {client.sendCommand(TCPClient.ROTATE);
                                                circleScreen.points.clear();});

        clear.addActionListener(e -> circleScreen.points.clear());

        stopRotateButton.addActionListener(e -> client.sendCommand(TCPClient.STOP_ROTATE));
        screenshotButton.addActionListener(e -> {
            BufferedImage bi = new BufferedImage(circlePane.getSize().width, circlePane.getSize().height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            circlePane.paint(g);  //this == JComponent
            g.dispose();
            try {
                ImageIO.write(bi, "png", new File("screenshot" + screenshotCnt + ".png"));
                screenshotCnt++;
            } catch (Exception excep) {
            }
        });

        circlePane.setLayout(new BoxLayout(circlePane, BoxLayout.PAGE_AXIS));
        circleScreen = new CircleScreen();
        circlePane.add(circleScreen);

        client = new TCPClient("192.168.4.1", 1212);
        client.startClient();

        System.out.println("onStart");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                client.sendCommand(TCPClient.STEP_FORWARD);
                break;
            case KeyEvent.VK_DOWN:
                client.sendCommand(TCPClient.STEP_BACKWARD);
                break;
            case KeyEvent.VK_RIGHT:
                client.sendCommand(TCPClient.STEP_RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                client.sendCommand(TCPClient.STEP_LEFT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
            client.sendCommand(TCPClient.RELEASE_MOTORS);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static MainForm getInstance() {
        if (instance == null) {
            instance = new MainForm();
        }
        return instance;
    }

    public static void main(String[] args) throws IOException {
        MainForm.getInstance();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
