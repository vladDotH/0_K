package ZeroK.GUI;

import org.opencv.core.Mat;

import org.opencv.core.*;
import org.opencv.videoio.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Example {
    public static void main(String[] args) throws InterruptedException {

        final boolean[] exit = {false};

        Window imgWin = new Window("window");

        Matrix img = new Matrix("img", new Size(640, 480));
        imgWin.addView(img);

        imgWin.setKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println(e.getKeyChar());
                if (e.getKeyChar() == 'q')
                    exit[0] = true;
            }
        });

        imgWin.start();


        Window statWin = new Window("stats");

        Button btn = new Button("button");
        Label flag = new Label("flag");
        flag.setMessage("true");

        btn.setOnClickListener((e) -> {
            if( flag.getMessage().equals("true") ){
                flag.setMessage("false");
            }
            else flag.setMessage("true");
        });

        statWin.addView(flag);

        statWin.addView(btn);

        Slider slider = new Slider("slider", 1, 99, 20);
        statWin.addView(slider);


        statWin.addView(new Label("Antoher lable"));
        statWin.addView(new Button("buttonnnn"));

        CheckBox box = new CheckBox("checker");
        statWin.addView(box);

        statWin.start();



        VideoCapture cap = new VideoCapture(0);
        Mat mat = new Mat();

        int fps = 0;
        long startTime = System.currentTimeMillis();

        while (!exit[0]) {
            cap.read(mat);
            img.update(mat);

            if (System.currentTimeMillis() - startTime > 1000) {
                System.out.println(fps);
                fps = 0;
                startTime = System.currentTimeMillis();

                System.out.println(box.getState());
            }

            fps++;
        }

        cap.release();

        System.exit(0);
    }

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}