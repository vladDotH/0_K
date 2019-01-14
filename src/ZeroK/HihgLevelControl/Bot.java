package ZeroK.HihgLevelControl;

public abstract class Bot extends GameObject {
    abstract public void move(int speed);

    abstract public void kick();

    abstract public void hammerMove(int speed);

    abstract public void connect(String port);

    abstract public void close();

    public boolean getMode() {
        return automate;
    }

    public void switchMode(){
        automate = !automate;
        this.move(0);
    }

    public void switchDirection(){
        direction = -direction;
    }

    public int getDirection(){
        return direction;
    }

    protected int upTime = 500, downTime = 600;
    protected boolean automate = true;
    protected int direction = 1;

    protected static class Coef {
        int prop, cube, diff, intg;
    }

    protected boolean kickLock = false;
}
