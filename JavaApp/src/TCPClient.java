import org.json.JSONObject;

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

    public static final int STEP_FORWARD = 3;
    public static final int STEP_BACKWARD = 4;
    public static final int STEP_LEFT = 5;
    public static final int STEP_RIGHT = 6;
    public static final int ROTATE = 1;
    public static final int STOP_ROTATE = 2;
    public static final int RELEASE_MOTORS = 7;

    public static final int WALK_FORWARD_RIGHT = 8;
    public static final int WALK_BACKWARD_RIGHT = 9;
    public static final int WALK_FORWARD_LEFT = 10;
    public static final int WALK_BACKWARD_LEFT = 11;
    public static final int WALK_FORWARD_BOTH = 12;
    public static final int WALK_BACKWARD_BOTH = 13;

    Socket client;
    String line = "";
    DataOutputStream dOut;
    private int port;
    private String address;
    private boolean running;
    private int bps;

    public TCPClient(String address, int port) {
        this.port = port;
        this.address = address;
    }


    public void startClient() {

        try {
            client = new Socket(address, port);
            client.setSoTimeout(10000);
            dOut = new DataOutputStream(client.getOutputStream());
            this.start();

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    //System.out.println(bps + "packs per sec");
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
                    PacketReceiveHandler.handle(line);
                    bps++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCommand(Object c) {
        JSONObject obj = new JSONObject();
        obj.put("c", c);
        try {
            dOut.write(obj.toString().getBytes());
            dOut.write('#');
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sent " + obj.toString());
    }

    public void sendTravelDistance(Object c, Object d){
        JSONObject obj = new JSONObject();
        obj.put("c", c);
        obj.put("v",d);
        try {
            dOut.write(obj.toString().getBytes());
            dOut.write('#');
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("sent " + obj.toString());
    }


    public void sendString(String s) {
        try {
            byte[] data = s.getBytes("UTF-8");
            dOut.write(data);
            dOut.write('#');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
