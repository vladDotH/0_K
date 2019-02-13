package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CheckBox extends View {

    private JCheckBox box;

    public CheckBox(String name) {
        this.name = name;
        box = new JCheckBox(name);
    }

    public CheckBox(String name, boolean state){
        this(name);
        if( state != box.isSelected() )
            box.doClick();
    }

    public boolean getState() {
        return box.isSelected();
    }

    public void changeState() {
        box.doClick();
    }

    public void setActionListener(ActionListener listener){
        box.addActionListener(listener);
    }

    @Override
    protected Component getJComponent() {
        return box;
    }
}
