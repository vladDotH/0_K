package ZeroK.Samples;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.Arrays;

public class CornersSample {
    public static void main(String[] args) {

        VideoCapture cap = new VideoCapture(0);

        Mat img = new Mat(), gray = new Mat(), mask = new Mat(new Size(640, 480), CvType.CV_8UC1, new Scalar(0));

        Imgproc.rectangle(mask, new Point(0, 0), new Point(640, 480), new Scalar(255), -1);

        MatOfPoint corners = new MatOfPoint();


        long fps = 0, time = System.currentTimeMillis();

        while (true) {
            cap.read(img);

            Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);

            Imgproc.goodFeaturesToTrack(gray, corners, 50, 0.03, 6);

            Arrays.stream(corners.toArray()).forEach(point -> {
                Imgproc.circle(img, point, 3, new Scalar(255, 255, 255, -1));
            });

            HighGui.imshow("win", img);

            if (HighGui.waitKey(5) != -1)
                break;

        }

        cap.release();

        System.exit(0);
    }

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
