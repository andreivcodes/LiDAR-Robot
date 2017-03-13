#include <ESP8266WiFi.h>
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

  sensor.init();
  sensor.setTimeout(500);
  sensor.setMeasurementTimingBudget(20000);
  sensor.startContinuous();
  
  servo.attach(16);
}

void loop()
{
    if (server.hasClient()) {
      if (!cl.connected()) {
        cl = server.available();
        Serial.println("New client!");
      }
    }

  switch (cl.read())
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
  if (sensor.timeoutOccurred()) { cl.println(" TIMEOUT"); }

  
/*
   servo.write(45);  
*/   
}

void initHardware()
{
  Serial.begin(115200);
  Serial.println("");
  //pinMode(A0, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
}
