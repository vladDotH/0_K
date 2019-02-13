package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class Window extends View {

    private static int xPos = 160, yPos = 160;

    private JFrame frame;
    private HashSet<View> views = new HashSet<>();

    public Window(String name) {
        this.name = name;
        frame = new JFrame(name);
        frame.setBackground(Color.WHITE);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setFocusable(true);
    }

    public void start() {
        frame.pack();
        frame.setLocation(new Point(xPos, yPos));
        frame.setVisible(true);

        xPos += frame.getWidth() + 50;

        if( xPos >= 1000 ){
            xPos = 160;

            yPos += frame.getHeight() + 50;
        }

    }

    public Window addView(View view) {
        frame.add((Component) view.getJComponent());
        views.add(view);

        return this;
    }

    public Window addView(Slider view) {
        this.addView((View) view.getLabel());
        frame.add((Component) view.getJComponent());

        views.add(view);

        return this;
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
