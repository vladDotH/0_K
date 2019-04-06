package ZeroK.HihgLevelControl;

import ZeroK.LowLevelControl.Lego.jEV3;

public class LegoBot extends Bot {

    private jEV3 controller;

    private jEV3.Motor kickMotor, helpKicker;

    private int upSpeed = 100, downSpeed = 60;

    public void setKickMotor(jEV3.Motor kickMotor) {
        this.kickMotor = kickMotor;
    }

    public void setHelpKicker(jEV3.Motor helpKicker) {
        this.helpKicker = helpKicker;
    }

    public void setLR(jEV3.Motor left, jEV3.Motor right) {
        controller.setLR(left, right);
    }

    public LegoBot(String port) {
        connect(port);
    }

    public LegoBot() {
    }

    @Override
    public void move(int speed) {
        if (speed > 100) speed = 100;
        if (speed < -100) speed = -100;

        controller.ride(speed * direction, speed * direction);
    }

    @Override
    public void kick() {
        if (kickLock)
            return;

        kickLock = true;

        new Thread(() -> {
            try {
                hammerMove(upSpeed);
                Thread.sleep(upTime);

                hammerMove(downSpeed);
                Thread.sleep(downTime);

                hammerMove(0);

                kickLock = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void hammerMove(int speed) {
        if (kickMotor != null) {
            kickMotor.setSpeed(speed);

            if (speed == 0)
                kickMotor.stopBreak();
            else
                kickMotor.start();
        }
        if (helpKicker != null) {
            helpKicker.setSpeed(speed);

            if (speed == 0)
                helpKicker.stopBreak();
            else
                kickMotor.start();
        }
    }

    @Override
    public void connect(String port) {
        controller = new jEV3(port);
    }

    @Override
    public void close() {
        controller.close();
    }

    public jEV3 getController() {
        return controller;
    }
}
