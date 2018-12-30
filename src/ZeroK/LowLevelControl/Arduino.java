package ZeroK.LowLevelControl;

import jssc.*;

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
        } catch (SerialPortException ex) {
            System.out.println(ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void digitalWrite(int pin, Mode mode) {
        try {
            port.writeByte((byte) Mode.DIGITAL.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) mode.ordinal());

            Thread.sleep(1);

        } catch (SerialPortException ex) {
            System.out.println(ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void analogWrite(int pin, int value) {
        try {
            port.writeByte((byte) Mode.ANALOG.ordinal());
            port.writeByte((byte) pin);
            port.writeByte((byte) value);

            Thread.sleep(1);

        } catch (SerialPortException ex) {
            System.out.println(ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                byte[] data = port.readBytes(event.getEventValue());

                System.out.println(Arrays.toString(data));

                Thread.sleep(1);
            } catch (SerialPortException ex) {
                System.out.println(ex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        try {
            port.closePort();
        } catch (SerialPortException ex){
            System.out.println(ex);
        }
    }


    static enum Mode {
        LOW,
        HIGH,

        DIGITAL,
        ANALOG
    }
}
