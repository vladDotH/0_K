#ifndef MOTOR_H_INCLUDED
#define MOTOR_H_INCLUDED

struct motor
{
    int mainPin;
    int reversePin;

    int currentSpeed;

    motor ( int mPin, int rPin ) {
        mainPin = mPin;
        reversePin = rPin;
    }
    motor () = default;
};

extern motor A, B;

#endif // MOTOR_H_INCLUDED
