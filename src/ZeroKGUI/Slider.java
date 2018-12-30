package ZeroKGUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Slider extends View {

    private JSlider slider;
    private final JLabel sliderName;

    public Slider(String name, int min, int max, int init) {
        this.name = name;

        sliderName = new JLabel(name + " : " + init, JLabel.CENTER);
        sliderName.setAlignmentX(Component.CENTER_ALIGNMENT);

        slider = new JSlider(JSlider.HORIZONTAL,
                min, max, init);

//        slider.setMajorTickSpacing((max - min) / 10);
        slider.setMinorTickSpacing((max - min) / 10);
        slider.setPaintTicks(true);
//        slider.setPaintLabels(true);
    }

    public JLabel getLabel(){
        return sliderName;
    }

    public void setChangeListener(ChangeListener listener){
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                listener.stateChanged(e);
                sliderName.setText(name + " : " + slider.getValue());
            }
        });
    }

    public int getValue(){
        return slider.getValue();
    }

    @Override
    protected Object getJComponent() {
        return slider;
    }
}

