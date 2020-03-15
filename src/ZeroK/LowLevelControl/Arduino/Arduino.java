package ZeroK.LowLevelControl.Arduino;

import ZeroK.LowLevelControl.SafeSerialPort;
import jssc.*;
import org.opencv.core.Mat;

import java.util.*;

public class Arduino implements SerialPortEventListener, AutoCloseable {

    private SafeSerialPort port;

    public boolean isOpened() {
        return port.isOpened();
    }

    public Arduino(String portName) {
        port = new SafeSerialPort(portName);
        try {
            port.openPort();

            port.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            port.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

            port.addEventListener(this, SerialPort.MASK_RXCHAR);

            Thread.sleep(500);
        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void digitalWrite(int pin, Mode mode) {
        try {
            byte[] msg = {(byte) Mode.DIGITAL.ordinal(), (byte) pin, (byte) mode.ordinal()};
            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void analogWrite(int pin, int value) {
        try {
            byte[] msg = {(byte) Mode.PWM.ordinal(), (byte) pin, (byte) value};
            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                byte[] data = port.readBytes(event.getEventValue());
                System.out.println(Arrays.toString(data));
                Thread.sleep(1);
            } catch (SerialPortException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        try {
            if (port.isOpened())
                port.closePort();
        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param time [0...1023] ms
     */
    public void kickConfigUpTime(int time) {
        try {
            byte[] msg = {(byte) Mode.KICK_CONFIG_UP_TIME.ordinal(), (byte) (time / 4), 0};
            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param time [0...1023] ms
     */
    public void kickConfigDownTime(int time) {
        try {
            byte[] msg = {(byte) Mode.KICK_CONFIG_DOWN_TIME.ordinal(), (byte) (time / 4), 0};
            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param speed [0...1023] ms
     */
    public void kickConfigUpSpeed(int speed) {
        try {
            byte[] msg = {(byte) Mode.KICK_CONFIG_UP_SPEED.ordinal(),
                    (byte) (Math.abs(speed)),
                    (byte) (speed > 0 ? 1 : 0)};
            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param speed [0...1023] ms
     */
    public void kickConfigDownSpeed(int speed) {
        try {
            byte[] msg = {(byte) Mode.KICK_CONFIG_DOWN_SPEED.ordinal(),
                    (byte) (Math.abs(speed)),
                    (byte) (speed > 0 ? 1 : 0)};
            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @param speed [-255...255]
     */
    public void move(int speed) {
        try {
            byte[] msg = {(byte) Mode.MOVE.ordinal(),
                    (byte) Math.abs(speed),
                    (byte) (speed > 0 ? 1 : 0)};

            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void kick() {
        try {
            byte[] msg = {(byte) Mode.KICK.ordinal(), 0, 0};

            port.writeBytes(msg);

            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public enum Mode {
        LOW,
        HIGH,

        DIGITAL,
        PWM,

        MOVE,

        KICK_CONFIG_UP_TIME,
        KICK_CONFIG_DOWN_TIME,

        KICK_CONFIG_UP_SPEED,
        KICK_CONFIG_DOWN_SPEED,

        KICK,
    }
}
