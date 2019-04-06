package ZeroK.LowLevelControl.Arduino;

@Deprecated
public class StepKicker extends StepMotor {

    public StepKicker(int pin_dir, int pin_step, int pin_enable) {
        super(pin_dir, pin_step, pin_enable);
    }

    @Override
    public void move(int val) {
        if (this.speed == speed)
            return;

        if (speed > 255) speed = 255;
        if (speed < -255) speed = -255;

        this.speed = speed;

        if (speed == 0)
            controller.setKickAble(false);
        else
            controller.setKickAble(true);

        if (speed > 0)
            controller.setKickDir(true);
        else
            controller.setKickDir(false);


        controller.setKickSpeed(speed);
    }

}
