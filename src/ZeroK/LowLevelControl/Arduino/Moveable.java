package ZeroK.LowLevelControl.Arduino;

public interface Moveable {
    void move(int val);
    void attachToArduino(Arduino controller);
}
