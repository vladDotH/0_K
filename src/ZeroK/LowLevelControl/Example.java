package ZeroK.LowLevelControl;

import jssc.*;

import java.util.Arrays;
import java.util.Scanner;

public class Example {

    private static SerialPort serialPort;

    public static void main(String[] args) throws InterruptedException {
        Arduino ard = new Arduino("COM5");

        Scanner cin = new Scanner(System.in);

        char input = '0';
        while (input != 'q') {
            input = cin.next().charAt(0);

            if (input == 'w') {
                ard.digitalWrite(7, Arduino.Mode.HIGH);
                ard.analogWrite(6, 50);
            }

            if (input == 'd') {
                ard.digitalWrite(7, Arduino.Mode.LOW);
                ard.analogWrite(6, 50);
            }

            if (input == 's') {
                ard.digitalWrite(7, Arduino.Mode.LOW);
                ard.analogWrite(6, 0);
            }
        }

        ard.close();
    }

}