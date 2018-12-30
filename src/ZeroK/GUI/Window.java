package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Window extends View {
    private JFrame frame;
    private HashSet<View> views = new HashSet<>();

    public Window(String name) {
        this.name = name;
        frame = new JFrame(name);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void start(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void addWidget(View view){
        frame.add((Component) view.getJComponent());
        views.add(view);
    }

    public void addWidget(Slider view){
        frame.add(view.getLabel());
        frame.add((Component) view.getJComponent());

        views.add(view);
    }

    @Override
    protected Object getJComponent() {
        return frame;
    }
}
