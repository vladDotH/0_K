package ZeroK.LowLevelControl;

import jssc.*;

public class SafeSerialPort extends SerialPort {
    public SafeSerialPort(String portName) {
        super(portName);
    }

    @Override
    public boolean writeBytes(byte[] buffer) throws SerialPortException {
        synchronized (this) {
            return super.writeBytes(buffer);
        }
    }
}
