#include <ESP8266WiFi.h>
#include <ArduinoOTA.h>
#include <Wire.h>
#include <VL53L0X.h>
#include <Servo.h>

WiFiServer server(1212);
WiFiClient cl;

VL53L0X sensor;

Servo servo;

void setup()
{

  initHardware();

  WiFi.softAP("ESPap");
  server.begin();
  Wire.begin();
  ArduinoOTA.begin();

  /*sensor.init();
    sensor.setTimeout(500);
    sensor.setMeasurementTimingBudget(20000);
    sensor.startContinuous();
  */

}

void loop()
{
  int i = 0;
  if (server.hasClient()) {
    if (!cl.connected()) {
      cl = server.available();
      Serial.println("New client!");
      for (i = 0; i < 20; i++) {
        digitalWrite(LED_BUILTIN, LOW);
        delay(50);
        digitalWrite(LED_BUILTIN, HIGH);
        delay(50);
      }
    }
  }

  ArduinoOTA.handle();
  digitalWrite(LED_BUILTIN, LOW);
  delay(500);
  digitalWrite(LED_BUILTIN, HIGH);
  delay(500);

  /*switch (cl.read())
    {
    case 'r':

      break;
    case '0':
      digitalWrite(LED_BUILTIN, HIGH);
      break;
    case '1':
      digitalWrite(LED_BUILTIN, LOW);
      break;

    default:
      break;
    }

    cl.println(sensor.readRangeContinuousMillimeters());
    if (sensor.timeoutOccurred()) {
    cl.println(" TIMEOUT");
    }



    servo.write(135);
  */
}

void initHardware()
{
  servo.attach(D0);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);
}
