package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends View
        implements Messenger {

    private JButton button;
    private String message;

    public Button(String name) {
        this.name = name;
        button = new JButton(name);
        message = null;
    }

    public void setOnClickListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    @Override
    public void setMessage(String msg) {
        message = msg;
        if (msg != null)
            button.setText(name + " : " + msg);
        else button.setText(name);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    protected Component getJComponent() {
        return button;
    }
}
