import javax.swing.*;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by andrei on 1/4/17.
 */
public class MainFrame extends JFrame {

    private JTextField textField1;
    private JButton sendCharButton;
    public JTextArea textArea1;
    public String textArea1Text = "";
    private JPanel panel;
    private JButton LEDOnButton;
    private JButton LEDOffButton;
    private JButton resetTickButton;
    private JLabel bpsLabel;
    private JProgressBar progressBar1;
    public String bpsText;

    private static MainFrame instance;
    TCPClient client;
    public String receivedValue;

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }


    private MainFrame() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        sendCharButton.addActionListener(e -> client.sendChar(textField1.getText().charAt(0)));
        LEDOnButton.addActionListener(e -> client.sendChar('1'));
        LEDOffButton.addActionListener(e -> client.sendChar('0'));
        resetTickButton.addActionListener(e -> client.sendChar('r'));
        setVisible(true);
    }


    public static int scale(final int valueIn, final int baseMin, final int baseMax, final int limitMin, final int limitMax) {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }

    private void onStart() {
        client = new TCPClient("192.168.4.1", 1212);
        client.startClient();

        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    progressBar1.setValue(scale(Integer.valueOf(receivedValue),0,8190,0,100));
                    textArea1.setText(textArea1Text);
                    textArea1.setCaretPosition(textArea1.getDocument().getLength());
                    bpsLabel.setText(bpsText);
            });
            }
        };

        timer.schedule(myTask, 100, 100);

    }

    public static void main(String[] args) throws IOException {
        MainFrame.getInstance().onStart();
    }
}
