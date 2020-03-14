package ZeroK.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends View {

    private static int xPos = 30, yPos = 30;

    private JFrame frame;

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

        xPos += frame.getWidth() + 20;

        if( xPos >= 1000 ){
            xPos = 30;

            yPos += frame.getHeight() + 20;
        }

    }

    public Window addView(View view) {
        frame.add(view.getJComponent());

        return this;
    }

    public Window addView(Slider view) {
        this.addView(view.getLabel());
        frame.add(view.getJComponent());

        return this;
    }

    public void setKeyListener(KeyListener listener) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(e -> {
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
        });
    }

    @Override
    protected Component getJComponent() {
        return frame;
    }
}
