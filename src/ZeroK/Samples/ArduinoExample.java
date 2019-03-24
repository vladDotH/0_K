package ZeroK.Samples;

import ZeroK.LowLevelControl.Arduino.Arduino;
import jssc.*;

import java.util.Scanner;

public class ArduinoExample {

    private static SerialPort serialPort;

    public static void main(String[] args) {
        Arduino ard = new Arduino("/dev/ttyUSB0");

        Scanner cin = new Scanner(System.in);

        ard.pinMode(2, Arduino.Mode.OUT);
        ard.pinMode(3, Arduino.Mode.OUT);
        ard.pinMode(4, Arduino.Mode.OUT);

        char input = '0';
        while (input != 'q') {
            input = cin.next().charAt(0);

            if (input == 'w') {
                ard.setMoveDir(true);
            }

            if (input == 'd') {
                ard.setMoveDir(false);
            }

            if (input == 'r') {
                ard.setMoveAble(true);
            }

            if (input == 'l') {
                ard.setMoveAble(false);
            }

            if (input == 'a') {
                ard.setMoveSpeed(40);
            }

            if (input == 's') {
                ard.setMoveSpeed(240);
            }
        }

        ard.close();
    }

}