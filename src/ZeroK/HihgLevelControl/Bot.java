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

    public void switchMode() {
        automate = !automate;
        this.move(0);
    }

    public void switchDirection() {
        direction = -direction;
    }

    public int getDirection() {
        return direction;
    }

    public void react(GameObject ball) {
        if (ball.getArea() < minBallPixels) {
            move(0);
            return;
        }

        double delY = Math.abs(ball.getPos().y - this.getPos().y);

        if (delY < kickRange) {
            kick();
        }

        double delX = 0;

        if (Math.abs(roi.getDot1().x - ball.getPos().x) < borderRange)
            delX = (roi.getDot1().x + borderRange) - getPos().x;

        else if (Math.abs(roi.getDot2().x - ball.getPos().x) < borderRange)
            delX = (roi.getDot2().x - borderRange) - getPos().x;

        else
            delX = (ball.getPos().x - this.getPos().x);

        double speed = delX * coefs.prop
                + Math.pow(delX, 3) * coefs.cube
                + (delX - oldDelta) * coefs.diff;

        oldDelta = delX;

        move((int) speed);
    }

    protected int kickRange = 30;
    protected double oldDelta = 0;

    protected int upTime = 170, downTime = 440;
    protected boolean automate = false;
    protected int direction = 1;
    protected int minBallPixels = 30;

    protected int borderRange = 20;

    protected boolean kickLock = false;

    public int getBorderRange() {
        return borderRange;
    }

    public void setBorderRange(int borderRange) {
        this.borderRange = borderRange;
    }

    public final Coefs coefs = new Coefs();

    public int getKickRange() {
        return kickRange;
    }

    public void setKickRange(int kickRange) {
        this.kickRange = kickRange;
    }

    public int getMinBallPixels() {
        return minBallPixels;
    }

    public void setMinBallPixels(int minBallPixels) {
        this.minBallPixels = minBallPixels;
    }

    public static class Coefs {
        public float prop = 400, cube = 0, diff = 0, intg = 0;
    }

}
