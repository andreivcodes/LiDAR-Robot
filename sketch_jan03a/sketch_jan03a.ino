#include <ESP8266WiFi.h>

WiFiServer server(1212);
WiFiClient cl;

char c;
long ticks;

void setup()
{
  initHardware();
  WiFi.softAP("ESPap");
  server.begin();
}

void loop()
{
  ticks++;

  if (server.hasClient()) {
    if (!cl.connected()) {
      cl = server.available();
      Serial.println("New client!");
    }
  }

  switch (cl.read())
  {
    case 'r':
      ticks = 0;
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

  cl.println(ticks);
}

void initHardware()
{
  Serial.begin(115200);
  Serial.println("");
  //pinMode(A0, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
}
