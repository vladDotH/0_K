package ZeroK.GUI;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

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
