enum Mode {
  DIGITAL = 2,
  ANALOG,

  PIN_MODE,
  OUT,
  IN,

  ///@Depricated
  US_GET,

  ///@Depricated
  SERVO,
  ATTACH,
  DETACH,
  ///@
  
  REFRESH


  SET_MOVESPEED,
  SET_KICKSPEED,

  SET_MOVE_ABLE,
  SET_KICK_ABLE,

  SET_MOVE_DIR,
  SET_KICK_DIR
};



class StepMotor{
  int pinDir, pinEnable, pinStep;
  unsigned long long int stepDelay, stepTimer;
  boolean enable;

  const int minDelay = 900, maxDelay = 10000;
  const float coef = (maxDelay - minDelay) / 255;
public:
  StepMotor(int pin_dir, int pin_en, int pin_step){
    pinDir = pin_dir;
    pinEnable = pin_en;
    pinStep = pin_step;

    stepTimer = micros();
  }

  void on(){
    enable = true;
    digitalWrite(pinEnable, LOW);
  }

  void off(){
    enable = false;
    digitalWrite(pinEnable, HIGH);
  }

  void setDir(boolean dir){
    digitalWrite(pinDir, dir);
  }

  void makeStep(){
    if(enable && micros() - stepTimer >= stepDelay){
      digitalWrite(pinStep, !digitalRead(pinStep));
      stepTimer = micros();
    }
  }

  ///value must be in [0...255] (byte). 0 - min speed, 255 - max
  void setDelay(int value){
    stepDelay = minDelay + (int)abs(value - 255) * coef;
  }
};



void setup() {
  Serial.begin(9600);
}

byte msg [3];
int byteCount = 0;

StepMotor mover(2, 3, 4), kicker(5, 6, 7);

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

    if ( msg[0] == PIN_MODE ) {
      if ( msg[2] == OUT ){
        pinMode( msg[1], OUTPUT );
      }

      if ( msg[2] == IN )
        pinMode( msg[1], INPUT );
    }

    if ( msg[0] == SET_MOVESPEED ) { 
      mover.setDelay(msg[1]);
    }

    if ( msg[0] == SET_KICKSPEED ) {
      kicker.setDelay(msg[1]);
    }

    if ( msg[0] == SET_MOVE_ABLE ) {
      if(msg[1] == 0)
        mover.off();
      else 
        mover.on();
    }

    if ( msg[0] == SET_KICK_ABLE ) {
      if(msg[1] == 0)
        kicker.off();
      else 
        kicker.on();
    }

    if ( msg[0] == SET_MOVE_DIR ) {
      if(msg[1] == 0)
        mover.setDir(false);
      else 
        mover.setDir(true);
    }

    if ( msg[0] == SET_KICK_DIR ) {
      if(msg[1] == 0)
        kicker.setDir(false);
      else 
        kicker.setDir(true);
    }

    byteCount = 0;
  }

  mover.makeStep();
//  kicker.makeStep();

}






