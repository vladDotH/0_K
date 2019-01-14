package ZeroK.LowLevelControl.Arduino;

import jssc.*;
import org.opencv.core.CvType;

import java.util.*;

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

    public void servoStart(int pin, int degree){
        try {
            port.writeByte((byte) Mode.SERVO.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) degree);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void pinMode(int pin, Mode mode){
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
    public void sonicRead(int trig, int echo){
        try {
            port.writeByte((byte) Mode.US_GET.ordinal());
            port.writeByte((byte) trig);
            port.writeByte((byte) echo);

            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
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
            port.closePort();
        } catch (SerialPortException ex){
            ex.printStackTrace();
        }
    }


    public static enum Mode {
        LOW,
        HIGH,

        DIGITAL,
        ANALOG,

        PIN_MODE,
        OUT,
        IN,

        @Deprecated
        US_GET,

        SERVO
    }
}
