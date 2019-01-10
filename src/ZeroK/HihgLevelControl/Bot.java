package ZeroK.HihgLevelControl;

public abstract class Bot extends GameObject {
    abstract public void move(int speed);

    abstract public void kick();

    abstract public void hammerMove(int speed);

    abstract public void switchMode();

    abstract public boolean getMode();

    abstract public void switchDirection();

    abstract public void connect(String port);

    abstract public void close();

    protected int upTime = 500, downTime = 600;
    protected boolean automate = true;
    protected int direction = 1;

    protected boolean kickLock = false;
}
