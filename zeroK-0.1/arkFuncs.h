#ifndef ARKFUNCS_H_INCLUDED
#define ARKFUNCS_H_INCLUDED

#include <iostream>
#include <cv.h>
#include <highgui.h>
#include <windows.h>
#include <string>
#include <cmath>
#include <ctime>
#include <thread>

#include "ev3pp.hpp"

#include "globals.h"

#define KEY_DOWN(key) ( GetAsyncKeyState(key) & 0x8000 )

using namespace std;

void TrackCallback( int nullval ) {}

bool eqPix ( int Rt, int Gt, int Bt, int Rc, int Gc, int Bc, int R, int low, int high )
{
    double radius = (double) R / 10000;

    double value1 = ( Rc * Rt + Gc * Gt + Bc * Bt ) * ( Rc * Rt + Gc * Gt + Bc * Bt );
    double value2 = ( Rc * Rc + Gc * Gc + Bc * Bc ) * ( Rt * Rt + Gt * Gt + Bt * Bt );
    double sum  = Rc + Gc + Bc;


    if ( ( value1 / value2 ) >= radius && sum >= low && sum <= high )
        return 1;
    else
        return 0;
}

bool eqPix ( colours target, colours current, int R, int low, int high )
{
    return eqPix( target.red, target.green, target.blue, current.red, current.green, current.blue, R, low, high );
}

void writePixel( IplImage *image, int x, int y, int channel, int color )
{
    *( image -> imageData + y * image -> widthStep + x * image -> nChannels + channel ) = color;
}

void writePixel( IplImage *image, int x, int y, int r, int g, int b )
{
    writePixel( image, x, y, 2, r );
    writePixel( image, x, y, 1, g );
    writePixel( image, x, y, 0, b );
}

int readPixel( IplImage *image, int x, int y,int channel )
{
    return ( unsigned char ) *( image -> imageData + y * image -> widthStep + x * image -> nChannels + channel );
}

colours readPixel ( IplImage *image, int x, int y )
{
    colours out;
    out.red = readPixel( image, x, y, 2 );
    out.green = readPixel( image, x, y, 1 );
    out.blue = readPixel( image, x, y, 0 );

    return out;
}

void mouseFunc( int event,int x, int y, int flag, void* param )
{
    switch( event )
    {
    case CV_EVENT_LBUTTONDOWN:

        ark.par.x = x;
        ark.par.y = y;
        LbuttonDown = true;
        break;

    case CV_EVENT_RBUTTONDOWN:
        ball.par.x = x;
        ball.par.y = y;
        RbuttonDown = true;
        break;

    }
}

void waitKick()
{
    arkBot.outputPower(EV3pp::MOTOR_A, 100);
    arkBot.outputStart(EV3pp::MOTOR_A);

    arkBot.outputPower(EV3pp::MOTOR_D, 100);
    arkBot.outputStart(EV3pp::MOTOR_D);

    clock_t timer = clock();
    while ( clock() - timer < 200 ) { /**/ }

    arkBot.outputPower(EV3pp::MOTOR_A, -100);
    arkBot.outputStart(EV3pp::MOTOR_A);

    arkBot.outputPower(EV3pp::MOTOR_D, -100);
    arkBot.outputStart(EV3pp::MOTOR_D);

    timer = clock();
    while ( clock() - timer < 250 ) { /**/ }

    arkBot.outputPower(EV3pp::MOTOR_A, 0);
    arkBot.outputStart(EV3pp::MOTOR_A);

    arkBot.outputPower(EV3pp::MOTOR_D, 0);
    arkBot.outputStart(EV3pp::MOTOR_D);
}

void kick()
{
    if( clock() - kickTime < 500 )
    {
        thread delayKick( waitKick );
        delayKick.detach();
    }
}

#endif // ARKFUNCS_H_INCLUDED
