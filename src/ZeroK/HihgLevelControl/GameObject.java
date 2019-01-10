package ZeroK.HihgLevelControl;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.Arrays;
import java.util.stream.Stream;

public class GameObject {

    private Scalar color;

    private Point pos;
    private Point oldPos;

    private int area;
    private int xSummary, ySummary;

    public void detect(Moments moment) {
        area = (int) moment.get_m00();
        xSummary = (int) moment.get_m10();
        ySummary = (int) moment.get_m01();

        pos = new Point(xSummary / area, ySummary / area);
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

        pos = new Point(xSummary / area, ySummary / area);
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
