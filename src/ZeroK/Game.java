package ZeroK;


import ZeroK.CameraProcessing.GameFinder;
import ZeroK.GUI.*;
import ZeroK.HihgLevelControl.GameObject;
import ZeroK.HihgLevelControl.LegoBot;
import org.opencv.core.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends GameFinder {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        GameObject.setDefaultFrameSize(new Point(640 / 2, 480 / 2));
        new Game(0, new Size(640 / 2, 480 / 2), new Size(3, 3))
                .startGame();
    }

    public Game(int port, Size frameSize, Size blurSize) {
        super(port, frameSize, blurSize);
    }

    public Game(int port, Size frameSize) {
        super(port, frameSize);
    }

    public Game(int port) {
        super(port);
    }


    private LegoBot bot;
    private GameObject ball;

    private Window rgbWin;
    private CheckBox fpsBox, cornersBox, centersBox;
    private Button exit;

    private Window hsvWin, binWin;

    private Matrix rgbMat, hsvMat, binMat;

    private Window robotSetting;
    private Slider propCoef, kickRange, minBallPixels;
    private Button direction;

    private Window imgSetting;
    private Slider hueDisp, minS, minB;
    private Slider maxCornersSlider, qualitySlider, minDistanceSlider, blockSizeSlider;

    private Window roiWin;
    private Slider botX1, botX2, botY1, botY2,
            ballX1, ballX2, ballY1, ballY2;
    private CheckBox showRoiS;

    private boolean gameProcess = true;

    public void startGame() {
        init();

        int fps = 0;
        long time = System.currentTimeMillis();

        while (gameProcess) {

            makeFrame();

            findByColor(ball);
            findByCorners(bot);

            if (markCenters) {
                markObject(ball);
                markObject(bot);
            }

            if (showRoi) {
                showRoi(ball);
                showRoi(bot);
            }

            updateMats();

            if (showFps) {
                fps++;
                if (System.currentTimeMillis() - time >= 1000) {
                    System.out.println(fps);

                    time = System.currentTimeMillis();
                    fps = 0;
                }
            }
        }

        bot.close();
        this.close();

        System.exit(0);
    }

    private void updateMats() {
        rgbMat.update(rgbImg);
        hsvMat.update(hsvImg);
        binMat.update(binaryImg);
    }

    private void init() {
        ball = new GameObject();
        bot = new LegoBot("/dev/rfcomm0");
        bot.setColor(new Scalar(0, 255, 0));

        bot.setKickMotor(bot.getController().A);
        bot.setHelpKicker(bot.getController().B);

        bot.setLR(bot.getController().C, bot.getController().D);


        rgbWin = new Window("rgb image");
        rgbMat = new Matrix("rgb", frameSize);

        rgbMat.setMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ball.setColor(
                        new Scalar(
                                hsvImg.get(e.getY(), e.getX())
                        )
                );
            }
        });

        rgbWin.setKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == 'q')
                    gameProcess = false;
            }
        });

        exit = new Button("exit");
        exit.setOnClickListener(actionEvent -> gameProcess = false);

        fpsBox = new CheckBox("show fps", showFps);
        fpsBox.setActionListener(actionEvent -> showFps = fpsBox.getState());

        rgbWin.addView(exit)
                .addView(fpsBox)
                .addView(rgbMat);


        hsvWin = new Window("hsv image");
        hsvMat = new Matrix("hsv", frameSize);
        hsvWin.addView(hsvMat);


        binWin = new Window("binary image");
        binMat = new Matrix("binary", frameSize);
        binWin.addView(binMat);


        imgSetting = new Window("image setting");

        cornersBox = new CheckBox("show corners", markCorners);

        centersBox = new CheckBox("show centers", markCenters);

        cornersBox.setActionListener(actionEvent -> markCorners = cornersBox.getState());
        centersBox.setActionListener(actionEvent -> markCenters = centersBox.getState());

        hueDisp = new Slider("hue dispersion", 0, 255, 10);
        hueDisp.setChangeListener(changeEvent -> hueDispersion = hueDisp.getValue());

        minS = new Slider("min saturarion", 0, 255, 30);
        minS.setChangeListener(changeEvent -> minSaturation = minS.getValue());

        minB = new Slider("min brightness", 0, 255, 30);
        minB.setChangeListener(changeEvent -> minSaturation = minB.getValue());

        maxCornersSlider = new Slider("max count of corners", 0, 200, maxCorners);
        maxCornersSlider.setChangeListener(changeEvent -> maxCorners = maxCornersSlider.getValue());

        qualitySlider = new Slider("quality level", 1, 100, (int) (cornerQuality * 100));
        qualitySlider.setChangeListener(changeEvent -> cornerQuality = (double) qualitySlider.getValue() / 100);

        minDistanceSlider = new Slider("min distance", 0, 40, minDistance);
        minDistanceSlider.setChangeListener(changeEvent -> minDistance = minDistanceSlider.getValue());

        blockSizeSlider = new Slider("block size", 1, 40, blockSize);
        blockSizeSlider.setChangeListener(changeEvent -> blockSize = blockSizeSlider.getValue());

        imgSetting.addView(cornersBox)
                .addView(centersBox)
                .addView(hueDisp)
                .addView(minB)
                .addView(minS)
                .addView(maxCornersSlider)
                .addView(qualitySlider)
                .addView(minDistanceSlider)
                .addView(blockSizeSlider);


        robotSetting = new Window("robot parameters");

        direction = new Button("direction");
        direction.setMessage(String.valueOf(bot.getDirection()));
        direction.setOnClickListener(actionEvent -> {
            bot.switchDirection();
            direction.setMessage(String.valueOf(bot.getDirection()));
        });

        propCoef = new Slider("proportional coefficient", 0, 1000, 20);
        kickRange = new Slider("kick range", 0, 60, 30);
        minBallPixels = new Slider("min ball pixels", 0, 100, 20);

        robotSetting.addView(direction)
                .addView(propCoef)
                .addView(kickRange)
                .addView(minBallPixels);


        roiWin = new Window("intresting ranges");

        botX1 = new Slider("bot - x1", 0, (int) frameSize.width, (int) bot.roi.getDot1().x);
        botX1.setChangeListener(changeEvent ->
                bot.roi.setDot1(new Point(botX1.getValue(), bot.roi.getDot1().y)));

        botX2 = new Slider("bot - x2", 0, (int) frameSize.width, (int) bot.roi.getDot2().x);
        botX2.setChangeListener(changeEvent ->
                bot.roi.setDot2(new Point(botX2.getValue(), bot.roi.getDot2().y)));

        botY1 = new Slider("bot - y1", 0, (int) frameSize.height, (int) bot.roi.getDot1().y);
        botY1.setChangeListener(changeEvent ->
                bot.roi.setDot1(new Point(bot.roi.getDot1().x, botY1.getValue())));

        botY2 = new Slider("bot - y2", 0, (int) frameSize.height, (int) bot.roi.getDot2().y);
        botY2.setChangeListener(changeEvent ->
                bot.roi.setDot2(new Point(bot.roi.getDot2().x, botY2.getValue())));

        ballX1 = new Slider("ball - x1", 0, (int) frameSize.width, (int) ball.roi.getDot1().x);
        ballX1.setChangeListener(changeEvent ->
                ball.roi.setDot1(new Point(ballX1.getValue(), ball.roi.getDot1().y)));

        ballX2 = new Slider("ball - x2", 0, (int) frameSize.width, (int) ball.roi.getDot2().x);
        ballX2.setChangeListener(changeEvent ->
                ball.roi.setDot2(new Point(ballX2.getValue(), ball.roi.getDot2().y)));

        ballY1 = new Slider("ball - y1", 0, (int) frameSize.height, (int) ball.roi.getDot1().y);
        ballY1.setChangeListener(changeEvent ->
                ball.roi.setDot1(new Point(ball.roi.getDot1().x, ballY1.getValue())));

        ballY2 = new Slider("ball - y2", 0, (int) frameSize.height, (int) ball.roi.getDot2().y);
        ballY2.setChangeListener(changeEvent ->
                ball.roi.setDot2(new Point(ball.roi.getDot2().x, ballY2.getValue())));

        showRoiS = new CheckBox("show borders", showRoi);
        showRoiS.setActionListener(actionEvent -> showRoi = showRoiS.getState());

        roiWin.addView(showRoiS)
                .addView(botX1)
                .addView(botX2)
                .addView(botY1)
                .addView(botY2)
                .addView(ballX1)
                .addView(ballX2)
                .addView(ballY1)
                .addView(ballY2);

        rgbWin.start();
        hsvWin.start();
        binWin.start();

        robotSetting.start();
        imgSetting.start();

        roiWin.start();
    }
}
