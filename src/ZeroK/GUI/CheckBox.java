package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;

public class CheckBox extends View {

    private JCheckBox box;

    public CheckBox(String name) {
        this.name = name;
        box = new JCheckBox(name);

    }

    public boolean getState() {
        return box.isSelected();
    }

    public void changeState() {
        box.doClick();
    }

    @Override
    protected Component getJComponent() {
        return box;
    }
}
