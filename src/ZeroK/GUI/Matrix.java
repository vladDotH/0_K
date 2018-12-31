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

public class Matrix extends Label  {

    public Matrix(String name, Size size) {
        super(name);

        this.name = name;

        label.setText(null);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentY(Component.TOP_ALIGNMENT);

        Mat initial = new Mat(size, CvType.CV_8UC3);
        update(initial);
    }

    public void setMouseListener(MouseAdapter listener) {
        label.addMouseListener(listener);
    }

    public void update(Mat mat) {
        Image outputImage = HighGui.toBufferedImage(mat);
        label.setIcon(new ImageIcon(outputImage));
    }
}
