package ZeroK.HihgLevelControl;

import ZeroK.LowLevelControl.Arduino.*;

public class ArduinoBot extends Bot {

    private Arduino controller;

    public ArduinoBot(String port) {
        connect(port);
        setUpTime(upTime);
        setUpSpeed(upSpeed);
        setDownTime(downTime);
        setDownSpeed(downSpeed);
    }

    @Override
    public void move(int speed) {
        if (speed > 255) speed = 255;
        if (speed < -255) speed = -255;
        controller.move(speed * direction);
    }

    @Override
    public void kick() {
        controller.kick();
    }

    @Deprecated
    @Override
    public void hammerMove(int speed) {
    }

    @Override
    public void connect(String port) {
        controller = new Arduino(port);
    }

    @Override
    public void close() {
        controller.close();
    }

    @Override
    public void setUpTime(int upTime) {
        super.setUpTime(upTime);
        controller.kickConfigUpTime(upTime);
    }

    @Override
    public void setDownTime(int downTime) {
        super.setDownTime(downTime);
        controller.kickConfigDownTime(downTime);
    }

    @Override
    public void setDownSpeed(int downSpeed) {
        super.setDownSpeed(downSpeed);
        controller.kickConfigDownSpeed(downSpeed);
    }

    @Override
    public void setUpSpeed(int upSpeed) {
        super.setUpSpeed(upSpeed);
        controller.kickConfigUpSpeed(upSpeed);
    }
}
