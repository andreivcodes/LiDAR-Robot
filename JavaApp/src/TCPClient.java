import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andrei on 1/4/17.
 */
public class TCPClient extends Thread {
    Socket client;
    String line = "";
    DataOutputStream dOut;
    private int port;
    private String address;
    private boolean running;
    private int bps;
    public int currentSensorValue = 100;

    public TCPClient(String address, int port) {
        this.port = port;
        this.address = address;
    }


    public void startClient() {

        try {
            client = new Socket(address, port);
            client.setSoTimeout(1000);
            dOut = new DataOutputStream(client.getOutputStream());
            this.start();

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(bps);
                    bps = 0;
                }
            }, 1000, 1000);

            System.out.println("startClient");
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {

        running = true;
        while (running) {
            try {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while ((line = inFromServer.readLine()) != null) {
                    MainForm.getInstance().textArea1Text = "Received: " + line + "\n";
                    currentSensorValue = Integer.valueOf(line);
                    MainForm.getInstance().updateUI();
                    bps++;
                    //MainForm.getInstance().bpsText = String.valueOf(bps);

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
