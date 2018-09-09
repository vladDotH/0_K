#include "wincom.h"

winCom::winCom( int port )
{
    this -> defPort( port );
}

void winCom::defPort( int port )
{
    stringstream sstr;
    sstr << port;
    sstr >> this -> port;

    string sPortName = "\\\\.\\COM";
    sPortName += this -> port;

    hSerial = CreateFile( sPortName.c_str(), GENERIC_READ | GENERIC_WRITE, 0, 0, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, 0 );

    DCB dcbSerialParams = { 0 };
    dcbSerialParams.DCBlength = sizeof( dcbSerialParams );
    if( !GetCommState(hSerial, &dcbSerialParams) )
    {
        cout << "getting state error\n";
        return;
    }

    dcbSerialParams.BaudRate = CBR_9600;
    dcbSerialParams.ByteSize = 8;
    dcbSerialParams.StopBits = ONESTOPBIT;
    dcbSerialParams.Parity = NOPARITY;

    if( !SetCommState( hSerial, &dcbSerialParams ) )
    {
        cout << "error setting serial port state\n";
        return;
    }

    cout << "connected to " << this -> port;
}

void winCom::send( string str )
{
    DWORD iSize = 0;

    if ( ! WriteFile( hSerial, str.c_str(), str.size(), &iSize, 0 ) )
        cout << "WRITE ERR\n";

    FlushFileBuffers( hSerial );
}

/*												*/

winController::winController( int port )
{
    controlPort.defPort( port );
}

void winController::setLR ( motor* Lmotor, motor* Rmotor )
{
    this -> Lmotor = Lmotor;
    this -> Rmotor = Rmotor;
}

void winController::ride( int Lval, int Rval )
{
    motorStart( *Lmotor, Lval );
    motorStart( *Rmotor, Rval );
}

void winController::motorStart ( motor &currentMotor, int val )
{
    if ( currentMotor.currentSpeed == val )
        return;

    currentMotor.currentSpeed = val;

    if ( val > 127 )
        val = 127;
    if ( val < -127 )
        val = -127;

    string message;

    message += (char) ( currentMotor.mainPin << 4 ) | currentMotor.reversePin;
    message += (char) val;

    controlPort.send( message );
}

