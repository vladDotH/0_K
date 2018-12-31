package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;

public class Label extends View
        implements Messenger {

    protected JLabel label;
    private String message;

    public Label(String name) {
        this.name = name;
        label = new JLabel();
        label.setText(name);
        message = null;
    }

    @Override
    public void setMessage(String msg) {
        message = msg;
        if (msg != null)
            label.setText(name + " : " + msg);
        else label.setText(name);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    protected Component getJComponent() {
        return label;
    }
}
