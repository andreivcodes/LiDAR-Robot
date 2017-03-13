import javax.swing.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andrei on 1/4/17.
 */
public class MainForm extends JFrame {

    private static MainForm instance;
    public JTextArea textArea1;
    public String textArea1Text = "";
    public String bpsText;

    TCPClient client;
    private JTextField textField1;
    private JButton sendCharButton;
    private JPanel panel;
    private JButton LEDOnButton;
    private JButton LEDOffButton;
    private JButton resetTickButton;

    private MainForm() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300 , 250);
        sendCharButton.addActionListener(e -> client.sendChar(textField1.getText().charAt(0)));
        LEDOnButton.addActionListener(e -> client.sendChar('1'));
        LEDOffButton.addActionListener(e -> client.sendChar('0'));
        resetTickButton.addActionListener(e -> client.sendChar('r'));
        setVisible(true);
    }

    public static MainForm getInstance() {
        if (instance == null) {
            instance = new MainForm();
        }
        return instance;
    }


    public static void main(String[] args) throws IOException {
        MainForm.getInstance().onStart();
    }

    private void onStart() {
        client = new TCPClient("192.168.4.1", 1212);
        client.startClient();
    }

    public void updateUI()
    {
        SwingUtilities.invokeLater(() -> {
            textArea1.setText(textArea1Text);
        });
    }
}
