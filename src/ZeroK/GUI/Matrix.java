package ZeroK.GUI;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Matrix extends View {

    private JLabel image;

    public Matrix(String name, Size size) {

        this.name = name;
        image = new JLabel();

        image.setAlignmentX(Component.LEFT_ALIGNMENT);
        image.setAlignmentY(Component.TOP_ALIGNMENT);

        Mat initial = new Mat(size, CvType.CV_8UC3);
        update(initial);
    }

    public void setMouseListener(MouseAdapter listener) {
        image.addMouseListener(listener);
    }

    public void update(Mat mat) {
        Image outputImage = HighGui.toBufferedImage(mat);
        image.setIcon(new ImageIcon(outputImage));
    }

    @Override
    protected Object getJComponent() {
        return image;
    }
}
