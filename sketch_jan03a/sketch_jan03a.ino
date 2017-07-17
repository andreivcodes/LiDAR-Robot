#include <ESP8266WiFi.h>
#include <ArduinoOTA.h>
#include <Wire.h>
#include <Adafruit_MotorShield.h>
#include "utility/Adafruit_MS_PWMServoDriver.h"
#include <AccelStepper.h>
#include <Wire.h>
#include <VL53L0X.h>
#include <ArduinoJson.h>
#include <TaskScheduler.h>

WiFiServer server(1212);
WiFiClient client;

VL53L0X sensor;

Adafruit_MotorShield AFMSTop = Adafruit_MotorShield(0x60);
Adafruit_MotorShield AFMSBot = Adafruit_MotorShield(0x61);
Adafruit_StepperMotor *rightMotor = AFMSTop.getStepper(48, 2);
Adafruit_StepperMotor *leftMotor = AFMSTop.getStepper(48, 1);
Adafruit_StepperMotor *rotateMotor = AFMSBot.getStepper(48, 2);

#define MOVE_TYPE                INTERLEAVE

void forwardstepright() {
  rightMotor->onestep(FORWARD, MOVE_TYPE);
}
void backwardstepright() {
  rightMotor->onestep(BACKWARD, MOVE_TYPE);
}

void forwardstepleft() {
  leftMotor->onestep(FORWARD, MOVE_TYPE);
}
void backwardstepleft() {
  leftMotor->onestep(BACKWARD, MOVE_TYPE);
}
void forwardsteprotate() {
  rotateMotor->onestep(FORWARD, DOUBLE);
}
void backwardsteprotate() {
  rotateMotor->onestep(BACKWARD, DOUBLE);
}

AccelStepper stepperright(forwardstepright, backwardstepright);
AccelStepper stepperleft(forwardstepleft, backwardstepleft);
AccelStepper stepperrotate(forwardsteprotate, backwardsteprotate);

char rx_buffer[100];
int rx_buffer_index = 0;
int sensor_data[35];
int sensor_data_index = 0;
int right_data;
int left_data;


unsigned char isRotatingReading = false;

int last_sensor_read = 0;

unsigned char has_data = 0;
unsigned char send_data = 0;

// Callback methods prototypes
void task_led_blink_callback();
void task_send_data_callback();
void task_motor_control_callback();
void task_sensor_read_callback();
void task_rotate_callback();
void task_loop_callback();

//Tasks
Task task_led_blink(250, TASK_FOREVER, &task_led_blink_callback);
Task task_send_data(10, TASK_FOREVER, &task_send_data_callback);
Task task_motor_control(250, TASK_FOREVER, &task_motor_control_callback);
Task task_sensor_read(35, TASK_FOREVER, &task_sensor_read_callback);
Task task_rotate(50, TASK_FOREVER, &task_rotate_callback);
Task task_loop(100, TASK_FOREVER, &task_loop_callback);

Scheduler runner;

void task_led_blink_callback() {
  digitalWrite(BUILTIN_LED, !digitalRead(BUILTIN_LED));
}

void task_send_data_callback() {

  if (!send_data)
    return;

  const size_t bufferSizetx = 2 * JSON_ARRAY_SIZE(1) + JSON_ARRAY_SIZE(6) + JSON_OBJECT_SIZE(3) + 50;
  DynamicJsonBuffer jsonBuffertx(bufferSizetx);

  JsonObject& roottx = jsonBuffertx.createObject();
  JsonArray& sensor = roottx.createNestedArray("s");

  while (sensor_data_index > 0) {
    sensor_data_index--;
    sensor.add(sensor_data[sensor_data_index]);
  }

  roottx["r"] = right_data;
  roottx["l"] = left_data;


  char buffer[250];
  roottx.printTo(buffer, sizeof(buffer));
  client.println(buffer);

  left_data = 0;
  right_data = 0;
  send_data = 0;
}

void task_motor_control_callback() {
  if (!has_data)
    return;

  const size_t bufferSizerx = JSON_OBJECT_SIZE(2) + 20;
  DynamicJsonBuffer jsonBufferrx(bufferSizerx);
  JsonObject& rootrx = jsonBufferrx.parseObject(rx_buffer);

  int command = rootrx["c"];
  int value = rootrx["v"];
  if (rootrx.success()) {
    switch (command) {
      case 1:
        isRotatingReading = true;
        break;

      case 2:
        isRotatingReading = false;
        break;

      case 3:
        stepperright.move(10);
        stepperleft.move(10);
        right_data = 10;
        left_data = 10;
        break;

      case 4:
        stepperright.move(-10);
        stepperleft.move(-10);
        right_data = -10;
        left_data = -10;
        break;

      case 5:
        stepperright.move(1);
        stepperleft.move(-1);
        right_data = 1;
        left_data = -1;
        break;

      case 6:
        stepperright.move(-1);
        stepperleft.move(1);
        right_data = -1;
        left_data = 1;
        break;

      case 7:
        rightMotor->release();
        leftMotor->release();
        rotateMotor->release();
        break;

      case 8:
        stepperright.move(value);
        right_data = value;
        break;

      case 9:
        stepperright.move(-value);
        right_data = -value;
        break;

      case 10:
        stepperleft.move(value);
        left_data = value;
        break;

      case 11:
        stepperleft.move(-value);
        left_data = -value;
        break;

      case 12:
        stepperright.move(value);
        stepperleft.move(value);
        right_data = value;
        left_data = value;
        break;

      case 13:
        stepperright.move(-value);
        stepperleft.move(-value);
        right_data = -value;
        left_data = -value;
        break;

      case 20: 
        stepperrotate.move(1);
        break;

      default:
        break;
    }
    rx_buffer_index = 0;
    has_data = 0;
  }
  else
    Serial.write("JSON Failed");
}

void task_sensor_read_callback() {
  last_sensor_read = sensor.readRangeContinuousMillimeters();
  sensor_data[sensor_data_index] = last_sensor_read;
  sensor_data_index++;
  if (sensor_data_index == 28)
    send_data = 1;
}

void task_rotate_callback() {
  if (isRotatingReading)
  {
    stepperrotate.setSpeed(25);
  }
  else {
    stepperrotate.setSpeed(0);
  }
}

void task_loop_callback() {
  ArduinoOTA.handle();

  if (server.hasClient()) {
    if (!client.connected()) {
      client = server.available();
      client.setNoDelay(false);
      server.setNoDelay(false);
      Serial.println("New client!");
    }
  }

  while (client.available()) {
    rx_buffer[rx_buffer_index] = client.read();
    if (rx_buffer[rx_buffer_index] == '#')
      has_data = 1;
    else
      rx_buffer_index++;
  }
}

void setup() {
  Serial.begin(115200);

  Serial.println("boot...");
  AFMSTop.begin();
  AFMSBot.begin();

  stepperright.setMaxSpeed(100.0);
  stepperright.setAcceleration(50.0);

  stepperleft.setMaxSpeed(100.0);
  stepperleft.setAcceleration(50.0);

  stepperrotate.setMaxSpeed(25.0);

  WiFi.softAP("ESPap");
  server.begin();

  ArduinoOTA.begin();

  Wire.begin();

  sensor.init();
  sensor.setTimeout(20);

  sensor.startContinuous();

  pinMode(BUILTIN_LED, OUTPUT);

  runner.init();
  runner.addTask(task_led_blink);
  runner.addTask(task_send_data);
  runner.addTask(task_sensor_read);
  runner.addTask(task_rotate);
  runner.addTask(task_motor_control);
  runner.addTask(task_loop);

  task_led_blink.enable();
  task_send_data.enable();
  task_sensor_read.enable();
  task_motor_control.enable();
  task_rotate.enable();
  task_loop.enable();
}

void loop() {
  runner.execute();
  stepperright.run();
  stepperleft.run();
  stepperrotate.runSpeed();
}
