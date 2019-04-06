package ZeroK.Samples;

import ZeroK.LowLevelControl.Arduino.Arduino;
import ZeroK.LowLevelControl.Arduino.StepMover;
import jssc.*;

import java.util.Scanner;

@Deprecated
public class ArduinoExample {

    private static SerialPort serialPort;

    public static void main(String[] args) {
        Arduino ard = new Arduino("/dev/ttyUSB0");

        StepMover mover = new StepMover(2, 3, 4);
        mover.attachToArduino(ard);

        Scanner cin = new Scanner(System.in);

        int input = 0;
        while (input != 256) {

            input = cin.nextInt();

            mover.move(input);
//
//            if (input == 'w') {
//                mover.move(0);
//            }
//
//            if (input == 'd') {
//                mover.move(-255);
//            }
//
//            if (input == 'r') {
//                mover.move(-200);
//            }
//
//            if (input == 'l') {
//                mover.move(10);
//            }
//
//            if (input == 'a') {
//                mover.move(200);
//            }
//
//            if (input == 's') {
//                mover.move(255);
//            }
        }

        ard.close();
    }

}