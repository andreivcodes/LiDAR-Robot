import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by andrei on 1/4/17.
 */
public class TCPClient extends Thread {
    private int port;
    private String address;
    private boolean running;
    Socket client;
    String line = "";
    DataOutputStream dOut;

    public int bps;

    public TCPClient(String address, int port) {
        this.port = port;
        this.address = address;
    }


    public void startClient() {
        try {
            client = new Socket(address, port);
            client.setSoTimeout(3000);
            dOut = new DataOutputStream(client.getOutputStream());
            this.start();


            Timer tbps = new Timer(1000, e -> {
                MainFrame.getInstance().bpsText = String.valueOf(bps);
                bps = 0;
            });
            tbps.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        running = true;
        while (running) {
            try {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while ((line = inFromServer.readLine()) != null) {
                    //MainFrame.getInstance().textArea1Text = MainFrame.getInstance().textArea1Text + "Received: " + line + "\n";
                    MainFrame.getInstance().textArea1Text = "Received: " + line + "\n";
                    bps++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendChar(char c) {
        try {
            dOut.write(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sent " + c);
    }

    public void stopClient() {
        running = false;
    }

}
