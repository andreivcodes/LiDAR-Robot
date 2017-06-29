import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by andrei on 6/22/17.
 */
public class PacketReceiveHandler {

    public static void handle(String line) {
        System.out.println("Received line: " + line);
        JSONObject jsonObject = new JSONObject(line);

        JSONArray sensorJson = jsonObject.getJSONArray("s");
        int rightValue =  jsonObject.getInt("r");
        int leftValue = jsonObject.getInt("l");

        //MainForm.getInstance().circleScreen.points.clear();

        for (int i = 0; i < sensorJson.length(); ++i) {
            int sensor = sensorJson.getInt(i);

            sensor = sensor/5;

            MainForm.getInstance().circleScreen.points.add(sensor);
        }

        MainForm.getInstance().circleScreen.updateUI();

        System.out.println("Right : " + rightValue + "   Left : " + leftValue);
    }
}
