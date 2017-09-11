import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by andrei on 6/22/17.
 */
public class PacketReceiveHandler {

    static int lastRight;
    static int lastLeft;
    public static void handle(String line) {
        System.out.println("Received line: " + line);
        JSONObject jsonObject = new JSONObject(line);

        JSONArray sensorJson = jsonObject.getJSONArray("s");
        int rightValue =  jsonObject.getInt("r");
        int leftValue = jsonObject.getInt("l");


        int right = rightValue * 22 / 96;
        int left = leftValue * 22 / 96;

        System.out.println(right  +  "    " + left);

        for (int i = 0; i < sensorJson.length(); ++i) {
            int sensor = sensorJson.getInt(i);
            sensor = sensor/5;
            Pt point = new Pt(sensor);
            MainForm.getInstance().circleScreen.points.add(point);
        }

        MainForm.getInstance().circleScreen.updateUI();

        System.out.println("Right : " + rightValue + "   Left : " + leftValue);
    }
}
