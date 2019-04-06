package ZeroK.Samples;

import ZeroK.GUI.Label;
import ZeroK.GUI.Window;
import ZeroK.HihgLevelControl.LegoBot;
import ZeroK.LowLevelControl.Arduino.Arduino;
import ZeroK.LowLevelControl.Lego.jEV3;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class LegoExample {
    public static void main(String[] args) {

        Window win = new Window("lego sample");

        LegoBot bot = new LegoBot("COM4");
        bot.setLR(bot.getController().A, bot.getController().C);

        bot.setKickMotor(bot.getController().B);

        win.setKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println(e.getKeyChar());

                switch (e.getKeyChar()) {
                    case 'w':
                        bot.move(70);
                        break;

                    case 's':
                        bot.move(-70);
                        break;

                    case 'k':
                        bot.kick();
                        break;

                    case 'q':
                        bot.close();
                        System.exit(0);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                bot.move(0);
            }
        });

        Label lbl = new Label("JUST LABEL FOR WINDOW");

        win.addView(lbl);

        win.start();

    }
}
