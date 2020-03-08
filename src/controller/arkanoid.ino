#include <BluetoothSerial.h>

enum Mode {
  DIGITAL = 2,
  PWM,
};


BluetoothSerial esp_bt;

byte msg [3];
int byteCount = 0;

const int freq = 4096;
const int resolution = 8;

void setup() {
  esp_bt.begin(9600);

  pinMode(12, OUTPUT);
  pinMode(13, OUTPUT);
  pinMode(14, OUTPUT);

  pinMode(25, OUTPUT);
  pinMode(26, OUTPUT);
  pinMode(27, OUTPUT);

  ledcSetup(0, freq, resolution);
  ledcAttachPin(13, 0);

  ledcSetup(1, freq, resolution);
  ledcAttachPin(27, 1);
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

    byteCount = 0;
  }

}
