package ZeroK.LowLevelControl.Arduino;

public class Motor implements Moveable{
    protected Arduino controller;

    private int speedPin, dirPin;
    private int speed;

    public Motor(int speedPin, int dirPin) {
        this.speedPin = speedPin;
        this.dirPin = dirPin;
    }

    public Motor(Arduino controller, int speedPin, int dirPin) {
        this(speedPin, dirPin);
        attachToArduino(controller);
    }

    @Override
    public void attachToArduino(Arduino controller){
        this.controller = controller;
        controller.pinMode(speedPin, Arduino.Mode.OUT);
        controller.pinMode(dirPin, Arduino.Mode.OUT);
    }

    @Override
    public void move(int speed) {
        if (speed > 255) speed = 255;
        if (speed < -255) speed = -255;

        if (this.speed == speed)
            return;

        this.speed = speed;

        if (speed > 0)
            controller.digitalWrite(dirPin, Arduino.Mode.HIGH);
        else
            controller.digitalWrite(dirPin, Arduino.Mode.LOW);

        controller.analogWrite(speedPin, Math.abs(speed));
    }
}
