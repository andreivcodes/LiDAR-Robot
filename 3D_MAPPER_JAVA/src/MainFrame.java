import javax.swing.*;
import java.io.IOException;

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
    public String bpsText;

    private static MainFrame instance;
    TCPClient client;

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


    private void onStart() {
        client = new TCPClient("192.168.4.1", 1212);
        client.startClient();

        Timer timer = new Timer(40, e -> {
            SwingUtilities.invokeLater(() -> {
                textArea1.setText(textArea1Text);
                textArea1.setCaretPosition(textArea1.getDocument().getLength());
                bpsLabel.setText(bpsText);
            });
        });
        timer.start();

    }

    public static void main(String[] args) throws IOException {
        MainFrame.getInstance().onStart();
    }
}
