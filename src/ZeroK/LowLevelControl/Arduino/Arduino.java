package ZeroK.LowLevelControl.Arduino;

import jssc.*;

import java.util.*;

@Deprecated
public class Arduino implements SerialPortEventListener, AutoCloseable {

    private SerialPort port;

    public Arduino(String portName) {
        port = new SerialPort(portName);
        try {
            port.openPort();

            port.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            port.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

            port.addEventListener(this, SerialPort.MASK_RXCHAR);

            Thread.sleep(2000);
        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }

        refreshBytes();
    }

    public void digitalWrite(int pin, Mode mode) {
        try {
            port.writeByte((byte) Mode.DIGITAL.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) mode.ordinal());

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void analogWrite(int pin, int value) {
        try {
            port.writeByte((byte) Mode.ANALOG.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) value);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void pinMode(int pin, Mode mode) {
        try {
            port.writeByte((byte) Mode.PIN_MODE.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) mode.ordinal());

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Deprecated
    public void sonicRead(int trig, int echo) {
        try {
            port.writeByte((byte) Mode.US_GET.ordinal());
            port.writeByte((byte) trig);
            port.writeByte((byte) echo);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Deprecated
    public void servoStart(int pin, int degree) {
        try {
            port.writeByte((byte) Mode.SERVO.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) degree);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Deprecated
    public void servoAttach(int pin) {
        try {
            port.writeByte((byte) Mode.ATTACH.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Deprecated
    public void servoDetach(int pin) {
        try {
            port.writeByte((byte) Mode.DETACH.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * steppers
     */

    public void setMoveSpeed(int speed) {
        try {
            port.writeByte((byte) Mode.SET_MOVESPEED.ordinal());
            port.writeByte((byte) speed);
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void setKickSpeed(int speed) {
        try {
            port.writeByte((byte) Mode.SET_KICKSPEED.ordinal());
            port.writeByte((byte) speed);
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void setMoveDir(boolean dir) {
        try {
            port.writeByte((byte) Mode.SET_MOVE_DIR.ordinal());
            port.writeByte((byte) (dir ? 1 : 0));
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void setKickDir(boolean dir) {
        try {
            port.writeByte((byte) Mode.SET_KICK_DIR.ordinal());
            port.writeByte((byte) (dir ? 1 : 0));
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void setMoveAble(boolean state) {
        try {
            port.writeByte((byte) Mode.SET_MOVE_ABLE.ordinal());
            port.writeByte((byte) (state ? 1 : 0));
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void setKickAble(boolean state) {
        try {
            port.writeByte((byte) Mode.SET_KICK_ABLE.ordinal());
            port.writeByte((byte) (state ? 1 : 0));
            port.writeByte((byte) 0);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     */

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

    private void refreshBytes() {
        try {
            port.writeByte((byte) Mode.REFRESH.ordinal());
            port.writeByte((byte) Mode.REFRESH.ordinal());
            port.writeByte((byte) Mode.REFRESH.ordinal());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            port.closePort();
        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }
    }

    public enum Mode {
        LOW,
        HIGH,

        DIGITAL,
        ANALOG,

        PIN_MODE,
        OUT,
        IN,

        @Deprecated
        US_GET,

        @Deprecated
        SERVO,

        @Deprecated
        ATTACH,
        @Deprecated
        DETACH,

        REFRESH,

        SET_MOVESPEED,
        SET_KICKSPEED,

        SET_MOVE_ABLE,
        SET_KICK_ABLE,

        SET_MOVE_DIR,
        SET_KICK_DIR
    }
}
