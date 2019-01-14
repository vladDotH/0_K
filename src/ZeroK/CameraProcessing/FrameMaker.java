package ZeroK.CameraProcessing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.Videoio;

public class FrameMaker extends Camera {
    protected Mat rgbImg,
            hsvImg,
            grayImg;

    protected Size frameSize, blurSize;

    public FrameMaker(int port, Size frameSize, Size blurSize) {
        super(port);
        this.frameSize = frameSize;
        this.blurSize = blurSize;

        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, frameSize.width);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, frameSize.height);

        rgbImg = new Mat();
        hsvImg = new Mat();
        grayImg = new Mat();
    }

    public FrameMaker(int port, Size frameSize) {
        this(port, frameSize, new Size(3, 3));
    }

    public FrameMaker(int port) {
        this(port, new Size(640, 480));
    }

    public void makeFrame() {
        this.read();

        Imgproc.blur(frame, rgbImg, blurSize);

        Imgproc.cvtColor(rgbImg, hsvImg, Imgproc.COLOR_BGR2HSV);

        Imgproc.cvtColor(rgbImg, grayImg, Imgproc.COLOR_BGR2GRAY);
    }
}
