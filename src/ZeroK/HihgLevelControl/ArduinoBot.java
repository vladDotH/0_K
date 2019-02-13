package ZeroK.HihgLevelControl;

import ZeroK.LowLevelControl.Arduino.Arduino;
import ZeroK.LowLevelControl.Arduino.Moveable;

import java.util.ArrayList;

public class ArduinoBot extends Bot {

    private Arduino controller;
    private ArrayList<Moveable> movers, hammers;

    private int lowDegree = 5, highDegree = 90;

    public ArduinoBot() {
        movers = new ArrayList<>();
        hammers = new ArrayList<>();
    }

    public ArduinoBot(String port) {
        this();
        connect(port);
    }

    public void addMover(Moveable motor) {
        motor.attachToArduino(controller);
        movers.add(motor);
    }

    public void addHammer(Moveable motor) {
        motor.attachToArduino(controller);
        hammers.add(motor);
    }

    public void removeMover(Moveable motor) {
        if (movers.contains(motor))
            movers.remove(motor);
    }

    public void removeHammer(Moveable motor) {
        if (hammers.contains(motor))
            hammers.remove(motor);
    }

    @Override
    public void move(int speed) {
        for (Moveable motor : movers) {
            motor.move(speed);
        }
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
        for (Moveable motor : hammers) {
            motor.move(speed);
        }
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
