package ZeroKGUI;

import org.opencv.core.Mat;

import org.opencv.core.*;
import org.opencv.videoio.*;

public class Example {
    public static void main(String[] args) throws InterruptedException {

        Window win = new Window("window");

        Button btn = new ZeroKGUI.Button("button");

        final boolean[] exit = {false};
        btn.setOnClickListener( e -> {
            exit[0] = true;
        });

        win.addWidget(btn);

        Slider slider = new Slider("slider", 1, 99, 20);
        win.addWidget(slider);

        Matrix img = new Matrix("img", new Size(640, 480));
        win.addWidget(img);

        win.start();

        VideoCapture cap = new VideoCapture(0);
        Mat mat = new Mat();

        while (!exit[0]) {
            cap.read(mat);
            img.update(mat);
        }

        cap.release();

        System.exit(0);

    }

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}