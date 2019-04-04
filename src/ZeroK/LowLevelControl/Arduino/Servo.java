package ZeroK.LowLevelControl.Arduino;

@Deprecated
public class Servo implements Moveable{
    private Arduino controller;

    private int pin;
    private int degree;

    public Servo(int pin) {
        this.pin = pin;

        controller.pinMode(pin, Arduino.Mode.OUT);
    }

    public Servo(Arduino controller, int pin) {
        this(pin);
        attachToArduino(controller);
    }

    @Override
    public void attachToArduino(Arduino controller) {
        this.controller = controller;
    }

    @Override
    public void move(int degree) {
        if (degree < 0) degree = 0;
        if (degree > 180) degree = 180;

        if (this.degree == degree)
            return;

        this.degree = degree;

        controller.servoStart(pin, degree);
    }
}
