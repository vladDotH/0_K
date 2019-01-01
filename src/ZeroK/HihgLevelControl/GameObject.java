package ZeroK.HihgLevelControl;

import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class GameObject {

    private Scalar color;

    private Point pos;
    private Point oldPos;

    private int pixCounter;

    public void refuse() {

    }

    public void addPixel(int x, int y) {

    }

    public void detect() {

    }

    public Point getOld() {
        return oldPos;
    }

    public void setOld(Point oldPos) {
        this.oldPos = oldPos;
    }
}
