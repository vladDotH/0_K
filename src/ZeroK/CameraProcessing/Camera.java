package ZeroK.CameraProcessing;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Camera {
    protected VideoCapture capture;
    protected Mat frame;

    public Camera(int port) {
        frame = new Mat();
        capture = new VideoCapture(port);
    }

    boolean read(){
        return  capture.read(frame);
    }
}