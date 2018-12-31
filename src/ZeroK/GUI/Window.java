package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class Window extends View {

    private JFrame frame;
    private HashSet<View> views = new HashSet<>();

    public Window(String name) {
        this.name = name;
        frame = new JFrame(name);
        frame.setBackground(Color.WHITE);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setFocusable(true);
    }

    public void start() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void addView(View view) {
        frame.add((Component) view.getJComponent());
        views.add(view);
    }

    public void addView(Slider view) {
        this.addView((View) view.getLabel());
        frame.add((Component) view.getJComponent());

        views.add(view);
    }

    public void setKeyListener(KeyListener listener) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                switch (e.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        listener.keyPressed(e);
                        break;
                    case KeyEvent.KEY_RELEASED:
                        listener.keyReleased(e);
                        break;
                    case KeyEvent.KEY_TYPED:
                        listener.keyTyped(e);
                        break;
                }
                return false;
            }
        });
    }

    public View getView(String name) {
        for (View v : views) {
            if (v.getName().equals(name))
                return v;
        }
        return null;
    }

    @Override
    protected Component getJComponent() {
        return frame;
    }
}
