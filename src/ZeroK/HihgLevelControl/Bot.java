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
        if( ball.getArea() < minBallPixels ) {
            move(0);
            return;
        }

        double delY = Math.abs(ball.getPos().y - this.getPos().y);

        if( delY < kickRange )
            kick();

        double delX = Math.abs(ball.getPos().x - this.getPos().x);

        double speed = delX * coefs.prop
                + Math.pow(delX, 3) * coefs.cube;


        move((int) speed);
    }

    protected int kickRange = 30;

    protected int upTime = 500, downTime = 600;
    protected boolean automate = true;
    protected int direction = 1;
    protected int minBallPixels = 30;

    protected boolean kickLock = false;

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
        public int prop = 10, cube = 0, diff = 0, intg = 0;
    }

}
