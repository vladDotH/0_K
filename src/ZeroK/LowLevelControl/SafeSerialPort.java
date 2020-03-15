package ZeroK.LowLevelControl;

import jssc.*;

public class SafeSerialPort extends SerialPort {
    public SafeSerialPort(String portName) {
        super(portName);
    }

    @Override
    public boolean writeBytes(byte[] buffer) {
        synchronized (this) {
            try {
                return super.writeBytes(buffer);
            } catch (SerialPortException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
