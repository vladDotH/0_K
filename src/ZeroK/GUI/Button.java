package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends View {
    private JButton button;

    public Button(String name) {
        this.name = name;
        button = new JButton(name);
    }

    public void setOnClickListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    @Override
    protected Object getJComponent() {
        return button;
    }
}
