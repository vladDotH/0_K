package ZeroK.LowLevelControl.Arduino;

@Deprecated
public class StepMover extends StepMotor {

    public StepMover(int pin_dir, int pin_step, int pin_enable) {
        super(pin_dir, pin_step, pin_enable);
    }

    @Override
    public void move(int speed) {
        if (this.speed == speed)
            return;

        if (speed > 255) speed = 255;
        if (speed < -255) speed = -255;

        this.speed = speed;

        if (speed == 0)
            controller.setMoveAble(false);
        else
            controller.setMoveAble(true);

        if (speed > 0)
            controller.setMoveDir(true);
        else
            controller.setMoveDir(false);


        controller.setMoveSpeed(Math.abs(speed));
    }
}
