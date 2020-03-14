#include <BluetoothSerial.h>

enum Mode {
  DIGITAL = 2,
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

  void setup() {
    pinMode(dir1, OUTPUT);
    pinMode(dir2, OUTPUT);
    pinMode(speed_pin, OUTPUT);

    ledcSetup(channel, freq, resolution);
    ledcAttachPin(speed_pin, channel);
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

    ledcWrite( channel, abs(power) );
  }
};

Motor kicker(12, 14, 13, 0), mover(25, 26, 27, 1);

int up_time,
    down_time,
    up_power,
    down_power;

volatile bool kick_flag = false;

void kick_task(void *args) {
  while (true) {
    delay(1);
    if (kick_flag) {

      kicker.move(up_power / 2);
      delay(up_time / 2);

      kicker.move(up_power);
      delay(up_time);

      kicker.move(down_power);
      delay(down_time);

      kicker.move(down_power / 1.5);
      delay(down_time);

      kicker.move(0);
      kick_flag = false;
    }
  }
}

void setup() {
  esp_bt.begin("Esp32 arkanoid");
  Serial.begin(115200);

  mover.setup();
  kicker.setup();

  xTaskCreate(
    kick_task,
    "kick task",
    10000,
    NULL,
    1,
    NULL);

  delay(500);
}

void loop() {
  delay(1);
  if ( esp_bt.available() ) {
    msg[byteCount] = esp_bt.read();
    byteCount ++;

    Serial.print(msg[byteCount - 1]);
    Serial.print(" ");
  }

  if ( byteCount == 3 ) {

    Serial.println("");

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