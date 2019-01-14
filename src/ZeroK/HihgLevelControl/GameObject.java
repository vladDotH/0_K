package ZeroK.HihgLevelControl;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class GameObject {

    private static Point frameSize = new Point(640, 480);

    public static void setDefaultFrameSize(Point point) {
        frameSize = point;
    }

    public static class ROI {
        private Point dot1 = new Point(0, 0), dot2 = frameSize;

        public void setDot1(Point dot1) {
            this.dot1 = dot1;
        }

        public void setDot2(Point dot2) {
            this.dot2 = dot2;
        }

        public Point getDot1() {
            return dot1;
        }

        public Point getDot2() {
            return dot2;
        }

        public Rect getRoi() {
            return new Rect(dot1, dot2);
        }
    }

    private Scalar color;
    private Point pos;

    private Point oldPos;
    private int area;

    private int xSummary, ySummary;

    public final ROI roi = new ROI();

    public GameObject() {
        color = new Scalar(0, 0, 0);
    }

    public void detect(Moments moment) {
        area = (int) moment.get_m00();
        xSummary = (int) moment.get_m10();
        ySummary = (int) moment.get_m01();

        if (area != 0)
            pos = new Point(xSummary / area, ySummary / area);
        else
            pos = new Point(0, 0);
    }

    public void detect(MatOfPoint corners) {
        detect(corners, null);
    }

    public void detect(MatOfPoint corners, Mat markingImg) {
        area = xSummary = ySummary = 0;

        for (Point corner : corners.toArray()) {
            area++;
            xSummary += corner.x;
            ySummary += corner.y;

            if (markingImg != null) {
                Imgproc.circle(markingImg, corner, 5, color);
            }
        }

        if (area != 0)
            pos = new Point(xSummary / area, ySummary / area);
        else
            pos = new Point(0, 0);
    }

    public Point getPos() {
        return pos;
    }

    public int getArea() {
        return area;
    }

    public Scalar getColor() {
        return color;
    }

    public void setColor(Scalar color) {
        this.color = color;
    }

    public Point getOld() {
        return oldPos;
    }

    public void setOld(Point oldPos) {
        this.oldPos = oldPos;
    }
}
