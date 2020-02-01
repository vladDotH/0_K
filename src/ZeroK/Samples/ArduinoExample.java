package ZeroK.Samples;

import ZeroK.LowLevelControl.Arduino.Arduino;
import ZeroK.LowLevelControl.Arduino.L298Motor;
import ZeroK.LowLevelControl.Arduino.Motor;
import jssc.SerialPort;

import java.util.Scanner;


public class ArduinoExample {

    private static SerialPort serialPort;

    public static void main(String[] args) throws InterruptedException {
        Arduino ard = new Arduino("COM11");

        System.out.println("connected");

        L298Motor move = new L298Motor(ard, 5, 4, 3);
        L298Motor kick = new L298Motor(ard, 6, 7, 8);

        System.out.println(Arduino.Mode.HIGH.ordinal());

        Scanner cin = new Scanner(System.in);

        int upTime = 150, downTime = 1;
        int speed = 255;

        int input = 0;
        while (input != 'q') {

            input = cin.nextInt();

            if (input == 666) {
                kick.move(speed);
                Thread.currentThread().sleep(upTime);
                kick.move(-100);
                Thread.currentThread().sleep(downTime);
                kick.move(0);
            } else
                move.move(input);

////
//            if (input == 'w') {
//                R.move(0);
//            }
//
//            if (input == 'd') {
//                R.move(-255);
//            }
//
//            if (input == 'r') {
//                R.move(-200);
//            }
//
//            if (input == 'l') {
//                R.move(10);
//            }
//
//            if (input == 'a') {
//                R.move(200);
//            }
//
//            if (input == 's') {
//                R.move(255);
//            }
        }

        ard.close();
    }

}