package ZeroK.HihgLevelControl;

import ZeroK.LowLevelControl.Lego.jEV3;

public class LegoBot extends Bot {

    private jEV3 controller;

    private jEV3.Motor kickMotor, helpKicker;

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
                hammerMove(-upSpeed);
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
                kickMotor.stopFloat();
            else
                kickMotor.start();
        }
        if (helpKicker != null) {
            helpKicker.setSpeed(-speed);

            if (speed == 0)
                helpKicker.stopFloat();
            else
                helpKicker.start();
        }
    }

    @Override
    public void connect(String port) {
        controller = new jEV3(port);
    }

    @Override
    public void close() {
        controller.A.setSpeed(0);
        controller.B.setSpeed(0);
        controller.C.setSpeed(0);
        controller.D.setSpeed(0);

        controller.A.start();
        controller.B.start();
        controller.C.start();
        controller.D.start();

        controller.A.stopFloat();
        controller.B.stopFloat();
        controller.C.stopFloat();
        controller.D.stopFloat();

        controller.close();
    }

    public jEV3 getController() {
        return controller;
    }
}
