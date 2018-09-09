#ifndef WINCOM_H
#define WINCOM_H

#include <string>
#include <iostream>
#include <windows.h>
#include <sstream>

#include "motor.h"

using namespace std;

class winCom {
private:
    HANDLE hSerial;
    string port;

public:
    winCom() = default;
    ~winCom() { CloseHandle( hSerial ); }

    winCom( int port );

    void defPort( int port );

    void send( string str );
};

class winController {
private:
    motor *Lmotor,
          *Rmotor;

    winCom controlPort;

public:
    winController( int port );
    ~winController() = default;

    void setLR( motor* Lmotor, motor* Rmotor );
    void ride( int Lval, int Rval );
    void motorStart( motor &currentMotor, int val );
};


#endif // WINCOM_H
