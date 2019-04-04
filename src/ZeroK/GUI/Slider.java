package ZeroK.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


public class Slider extends View {

    private JSlider slider;
    private final Label sliderName;

    public Slider(String name, int min, int max, int init) {
        this.name = name;

        sliderName = new Label(name);
        sliderName.setMessage(Integer.toString(init));

        slider = new JSlider(JSlider.HORIZONTAL,
                min, max, init);

        slider.setMinorTickSpacing((max - min) / 10);
        slider.setPaintTicks(true);

        setChangeListener( (e -> {}) );
    }

    public Label getLabel(){
        return sliderName;
    }

    public void setChangeListener(ChangeListener listener){
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                listener.stateChanged(e);
                sliderName.setMessage( "" + slider.getValue());
            }
        });
    }

    public int getValue(){
        return slider.getValue();
    }

    @Override
    protected Component getJComponent() {
        return slider;
    }
}

