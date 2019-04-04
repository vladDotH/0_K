package ZeroK.HihgLevelControl;

import ZeroK.LowLevelControl.Arduino.*;

import java.util.ArrayList;

public class ArduinoBot extends Bot {

    private Arduino controller;

    StepMotor mover, kicker;

    private int lowDegree = 5, highDegree = 90;

    public ArduinoBot(String port, StepMover mover, StepKicker kicker) {
        this.mover = mover;
        this.kicker = kicker;
        connect(port);
    }

    @Override
    public void move(int speed) {
        mover.move(speed);
    }

    @Override
    public void kick() {
        if (kickLock)
            return;

        kickLock = true;

        new Thread(() -> {
            try {
                hammerMove(highDegree);
                Thread.sleep(upTime);

                hammerMove(lowDegree);
                Thread.sleep(downTime);

                kickLock = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void hammerMove(int speed) {
        kicker.move(speed);
    }


    @Override
    public void connect(String port) {
        controller = new Arduino(port);
    }

    @Override
    public void close() {
        controller.close();
    }
}
