#include <BluetoothSerial.h>

enum Mode {
  DIGITAL,
  PWM,

  MOVE,

  KICK_CONFIG_UP_TIME,
  KICK_CONFIG_DOWN_TIME,

  KICK_CONFIG_UP_SPEED,
  KICK_CONFIG_DOWN_SPEED,

  KICK,
};


BluetoothSerial esp_bt;

byte msg [3];
int byteCount = 0;

const int freq = 4096,
          resolution = 8;

struct Motor {
  int dir1, dir2, speed_pin, channel;
  Motor(int dir1, int dir2, int speed_pin, int channel) {
    this->dir1 = dir1;
    this->dir2 = dir2;
    this->speed_pin = speed_pin;
    this->channel = channel;
  }

  void move(int power) {
    if (power > 0) {
      digitalWrite(dir1, HIGH);
      digitalWrite(dir2, LOW);
    }
    else {
      digitalWrite(dir2, HIGH);
      digitalWrite(dir1, LOW);
    }

    ledcWrite( channel, power );
  }
};

Motor mover(12, 14, 13, 0), kicker(25, 26, 27, 1);

TaskHandle_t kick;

int up_time,
    down_time,
    up_power,
    down_power;

volatile int kick_flag = false;

void kick_task( void *pvParameters ) {
  while (true) {
    if (kick_flag) {
      kicker.move(up_power);
      delay(up_time);
      kicker.move(down_power);
      delay(down_time);
    }
  }
}

void setup() {
  esp_bt.begin("Esp32 arkanoid");

  pinMode(mover.dir1, OUTPUT);
  pinMode(mover.dir2, OUTPUT);
  pinMode(mover.speed_pin, OUTPUT);

  pinMode(kicker.dir1, OUTPUT);
  pinMode(kicker.dir2, OUTPUT);
  pinMode(kicker.speed_pin, OUTPUT);

  ledcSetup(mover.channel, freq, resolution);
  ledcAttachPin(mover.speed_pin, mover.channel);

  ledcSetup(kicker.channel, freq, resolution);
  ledcAttachPin(kicker.speed_pin, kicker.channel);

  xTaskCreatePinnedToCore(
    kick_task,   /* Task function. */
    "kick task",     /* name of task. */
    10000,       /* Stack size of task */
    NULL,        /* parameter of the task */
    1,           /* priority of the task */
    &kick,      /* Task handle to keep track of created task */
    0);          /* pin task to core 0 */

  delay(500);
}

void loop() {
  if ( esp_bt.available() ) {
    msg[byteCount] = esp_bt.read();
    byteCount ++;
  }

  if ( byteCount == 3 ) {

    if ( msg[0] == DIGITAL ) {
      digitalWrite( msg[1], msg[2] );
    }

    if ( msg[0] == PWM ) {
      ledcWrite( msg[1], msg[2] );
    }

    if ( msg[0] == MOVE ) {
      mover.move(msg[1] * (msg[2] ? 1 : -1));
    }

    if ( msg[0] == KICK ) {
      kick_flag = true;
    }

    if ( msg[0] == KICK_CONFIG_UP_TIME ) {
      up_time = msg[1] * 4;
    }

    if ( msg[0] == KICK_CONFIG_DOWN_TIME ) {
      down_time = msg[1] * 4;
    }

    if ( msg[0] == KICK_CONFIG_UP_SPEED ) {
      up_power = msg[1] * (msg[2] ? 1 : -1);
    }

    if ( msg[0] == KICK_CONFIG_DOWN_SPEED ) {
      down_power = msg[1] * (msg[2] ? 1 : -1);
    }

    byteCount = 0;
  }

}
