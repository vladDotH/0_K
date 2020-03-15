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

        double diffY = Math.abs(ball.getPos().y - this.getPos().y);

        if (diffY < kickRange) {
            kick();
        }

        double diffX;

        if (Math.abs(roi.getDot1().x - ball.getPos().x) < borderRange)
            diffX = (roi.getDot1().x + borderRange) - getPos().x;

        else if (Math.abs(roi.getDot2().x - ball.getPos().x) < borderRange)
            diffX = (roi.getDot2().x - borderRange) - getPos().x;

        else
            diffX = (ball.getPos().x - this.getPos().x);

        double diffVal = (diffX - prevDiff) * coefs.diff;
        prevDiff = diffX;

        integralVal += diffVal;

        double speed = diffX * coefs.prop
                + Math.pow(diffX, 3) * coefs.cube
                + diffVal
                + integralVal * coefs.intg;

        move((int) speed);
    }

    protected int kickRange = 30;
    protected double prevDiff = 0, integralVal = 0;

    protected int upTime = 140, downTime = 330;
    protected int downSpeed = -120, upSpeed = 255;

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
        public double prop = 10, cube = 0, diff = 0, intg = 0;
    }

    public void setUpTime(int upTime) {
        this.upTime = upTime;
    }

    public void setDownTime(int downTime) {
        this.downTime = downTime;
    }

    public void setDownSpeed(int downSpeed) {
        this.downSpeed = downSpeed;
    }

    public void setUpSpeed(int upSpeed) {
        this.upSpeed = upSpeed;
    }

    public int getUpTime() {
        return upTime;
    }

    public int getDownTime() {
        return downTime;
    }

    public int getDownSpeed() {
        return downSpeed;
    }

    public int getUpSpeed() {
        return upSpeed;
    }
}
