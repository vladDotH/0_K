package ZeroK.LowLevelControl.Arduino;

import ZeroK.LowLevelControl.SafeSerialPort;
import jssc.*;

import java.util.*;

public class Arduino implements SerialPortEventListener, AutoCloseable {

    private SafeSerialPort port;

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

        refreshBytes();
    }

    public void digitalWrite(int pin, Mode mode) {
        try {
            byte[] msg = {(byte) Mode.DIGITAL.ordinal(), (byte) pin, (byte) mode.ordinal()};
            port.writeBytes(msg);
            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void analogWrite(int pin, int value) {
        try {
            byte[] msg = {(byte) Mode.ANALOG.ordinal(), (byte) pin, (byte) value};
            port.writeBytes(msg);
            Thread.sleep(1);

        } catch (SerialPortException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void pinMode(int pin, Mode mode) {
        try {
            byte[] msg = {(byte) Mode.PIN_MODE.ordinal(), (byte) pin, (byte) mode.ordinal()};
            port.writeBytes(msg);
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

        REFRESH
    }
}
