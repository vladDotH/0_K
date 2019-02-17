#include <Servo.h>

enum Mode {
  DIGITAL = 2,
  ANALOG,

  PIN_MODE,
  OUT,
  IN,

  ///@Depricated
  US_GET,

  SERVO,
  
  CLOSE
};

Servo motors[13];

void setup() {
  Serial.begin(9600);
  
  motors[10].attach(10);
}

byte msg [3];
int byteCount = 0;

void loop() {
  if ( Serial.available() ) {
    msg[byteCount] = Serial.read();
    byteCount ++;
  }

  if ( byteCount == 3 ) {

    if ( msg[0] == DIGITAL ) {
      digitalWrite( msg[1], msg[2] );
    }

    if ( msg[0] == ANALOG ) {
      analogWrite( msg[1], msg[2] );
    }

    ///@Depricated
    if ( msg[0] == US_GET ) {
      /*      NewPing sonar(msg[1], msg[2], 200);
       int range = sonar.ping_cm();
       Serial.write( (byte)range );
       */    }

    if ( msg[0] == PIN_MODE ) {
      if ( msg[2] == OUT ){
        pinMode( msg[1], OUTPUT );
      }

      if ( msg[2] == IN )
        pinMode( msg[1], INPUT );
    }

    if ( msg[0] == SERVO ) {
      if( motors[msg[1]].attached() ){
        motors[msg[1]].write(msg[2]);
      }
    }
    
    if ( msg[0] == CLOSE ){
      delay(1000);
      while(Serial.available()){
        Serial.read();
      }
      delay(100);
    }
    byteCount = 0;
  }
}


