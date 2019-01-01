package ZeroK.HihgLevelControl;

public interface Bot {
    void move(int speed);

    void kick();
    void hammerMove(int speed);

    void switchMode();
    boolean getMode();

    void switchDirection();

    boolean connect();
}
