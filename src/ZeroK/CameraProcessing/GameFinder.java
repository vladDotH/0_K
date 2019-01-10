package ZeroK.CameraProcessing;

import ZeroK.HihgLevelControl.GameObject;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class GameFinder extends FrameMaker {

    protected int hueDispersion = 10,
            minSaturation = 20,
            minBrightness = 20;

    protected double cornerQuality = 0.03;
    protected int maxCorners = 50, minDistance = 6;

    protected Mat binaryImg = new Mat();

    protected boolean markCenters = true, markCorners = true;

    public GameFinder(int port, Size frameSize, Size blurSize) {
        super(port, frameSize, blurSize);
    }

    public GameFinder(int port, Size frameSize) {
        super(port, frameSize);
    }

    public GameFinder(int port) {
        super(port);
    }

    public void findByCorners(GameObject object) {
        MatOfPoint corners = new MatOfPoint();
        Imgproc.goodFeaturesToTrack(grayImg, corners, maxCorners, cornerQuality, minDistance);

        if (markCorners)
            object.detect(corners, rgbImg);
        else object.detect(corners);

        if (markCenters) {
            Imgproc.circle(rgbImg, object.getPos(), 5, object.getColor(), -1);
        }
    }

    public void findByColor(GameObject object) {
        Scalar low = new Scalar(object.getColor().val[0] - hueDispersion,
                minSaturation, minBrightness);
        Scalar high = new Scalar(object.getColor().val[0] + hueDispersion,
                minSaturation, minBrightness);

        Core.inRange(hsvImg, low, high, binaryImg);

        object.detect(Imgproc.moments(binaryImg));

        if (markCenters) {
            Imgproc.circle(rgbImg, object.getPos(), 5, object.getColor(), -1);
        }
    }

    public void markObject(GameObject object) {
        Imgproc.circle(rgbImg, object.getPos(), 5, object.getColor(), -1);
    }
}
