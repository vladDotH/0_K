package ZeroK.CameraProcessing;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Camera {
    protected VideoCapture capture;
    protected Mat frame;
    protected int port;

    public Camera(int port) {
        this.port = port;
        frame = new Mat();
        capture = new VideoCapture(port);
    }

    public boolean read() {
        return capture.read(frame);
    }

    public void close() {
        frame.release();
        capture.release();
    }
}
