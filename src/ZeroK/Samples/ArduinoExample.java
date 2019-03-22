package ZeroK.Samples;

import ZeroK.LowLevelControl.Arduino.Arduino;
import jssc.*;

import java.util.Scanner;

public class ArduinoExample {

    private static SerialPort serialPort;

    public static void main(String[] args) {
        Arduino ard = new Arduino("/dev/rfcomm0");

        Scanner cin = new Scanner(System.in);

        char input = '0';
        while (input != 'q') {
            input = cin.next().charAt(0);

            if (input == 'w') {
                ard.digitalWrite(7, Arduino.Mode.HIGH);
                ard.analogWrite(6, 50);
            }

            if (input == 'r') {
                ard.servoStart(9, 30 );
            }
            if (input == 'l') {
                ard.servoStart(9, 90 );
            }

            if (input == 'd') {
                ard.servoDetach(9);
            }

            if (input == 'a') {
                ard.servoAttach(9);
            }

            if (input == 's') {
                ard.digitalWrite(7, Arduino.Mode.LOW);
                ard.analogWrite(6, 0);
            }
        }

        ard.close();
    }

}