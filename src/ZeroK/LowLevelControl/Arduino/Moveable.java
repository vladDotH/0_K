package ZeroK.LowLevelControl.Arduino;

@Deprecated
public interface Moveable {
    void move(int val);

    void attachToArduino(Arduino controller);
}
