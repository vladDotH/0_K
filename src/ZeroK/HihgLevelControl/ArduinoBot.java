package ZeroK.HihgLevelControl;

import ZeroK.LowLevelControl.Arduino.*;

public class ArduinoBot extends Bot {

    private Arduino controller;

    Moveable mover, kicker;

    private int lowSpeed = -100, highSpeed = 255;

    public ArduinoBot(String port, Moveable mover, Moveable kicker) {
        this.mover = mover;
        this.kicker = kicker;

        connect(port);

        mover.attachToArduino(controller);
        kicker.attachToArduino(controller);
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
                hammerMove(highSpeed);
                Thread.sleep(upTime);

                hammerMove(lowSpeed);
                Thread.sleep(downTime);

                hammerMove(0);

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
