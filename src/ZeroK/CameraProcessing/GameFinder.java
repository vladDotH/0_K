package ZeroK.CameraProcessing;

import ZeroK.HihgLevelControl.GameObject;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class GameFinder extends FrameMaker {

    protected int hueDispersion = 10,
            minSaturation = 20,
            minBrightness = 20;

    protected double cornerQuality = 0.03;
    protected int maxCorners = 50,
            minDistance = 6,
            blockSize = 3;

    protected Mat binaryImg = new Mat();

    protected boolean markCenters = true,
            markCorners = true,
            showFps = false,
            showRoi = false;

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
        Mat mask = Mat.zeros(frameSize, CvType.CV_8UC1);
        Imgproc.rectangle(mask, object.roi.getDot1(), object.roi.getDot2(), new Scalar(255), -1);
        Imgproc.goodFeaturesToTrack(grayImg, corners, maxCorners, cornerQuality, minDistance, mask, blockSize, true, 0.04);

        if (markCorners)
            object.detect(corners, rgbImg);
        else object.detect(corners);
    }

    public void findByColor(GameObject object) {
        Scalar low = new Scalar(object.getColor().val[0] - hueDispersion,
                minSaturation, minBrightness);
        Scalar high = new Scalar(object.getColor().val[0] + hueDispersion,
                255, 255);

        Core.inRange(hsvImg, low, high, binaryImg);
        Mat mask = new Mat(frameSize, CvType.CV_8UC1, new Scalar(255));
        Imgproc.rectangle(mask, object.roi.getDot1(), object.roi.getDot2(), new Scalar(0), -1);
        Core.subtract(binaryImg, mask, binaryImg);
        object.detect(Imgproc.moments(binaryImg));
    }

    public void markObject(GameObject object) {
        Imgproc.circle(rgbImg, object.getPos(), 5, object.getColor(), -1);
    }

    public void showRoi(GameObject object) {
        Imgproc.rectangle(rgbImg, object.roi.getDot1(), object.roi.getDot2(), object.getColor(), 2);
    }
}

