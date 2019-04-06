package ZeroK.LowLevelControl.Arduino;

@Deprecated
public abstract class StepMotor implements Moveable {

    protected Arduino controller;

    protected int pin_step, pin_enable, pin_dir,
            speed = 0;

    public StepMotor(int pin_dir, int pin_step, int pin_enable) {
        this.pin_step = pin_step;
        this.pin_enable = pin_enable;
        this.pin_dir = pin_dir;
    }

    @Override
    public void attachToArduino(Arduino controller) {
        this.controller = controller;

        controller.pinMode(pin_dir, Arduino.Mode.OUT);
        controller.pinMode(pin_step, Arduino.Mode.OUT);
        controller.pinMode(pin_enable, Arduino.Mode.OUT);

        move(0);
    }
}
