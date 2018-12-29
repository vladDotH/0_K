#ifndef GLOBALS_H_INCLUDED
#define GLOBALS_H_INCLUDED

#include "arkStructs.h"

object ark, ball, prediction;

bool &LbuttonDown = ark.buttonDown;
bool &RbuttonDown = ball.buttonDown;

unsigned long int prTime = clock();
unsigned long int kickTime = clock();

int Radius = 9800;
int WIDTH = 320;
int HEIGHT = 240;

int beginH = 0,
    beginW = 0,
    endH = HEIGHT,
    endW = WIDTH;

int lowbord = 60;
int highbord = 500;

bool automate = false;
bool findcolor = false;
bool colorcent = false;
bool predict = false;
bool borders = false;

EV3pp arkBot(EV3pp::MOTOR_B, EV3pp::MOTOR_C);

int turn = 1;
int Pcoef = 50; // must be divided on 100
int Ccoef = 4;  // on 1000


#endif // GLOBALS_H_INCLUDED
