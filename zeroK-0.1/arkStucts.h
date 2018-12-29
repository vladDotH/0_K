#ifndef ARKSTRUCTS_H_INCLUDED
#define ARKSTRUCTS_H_INCLUDED

struct parameters
{
    int x,y;
};

struct colours
{
    int red;
    int green;
    int blue;
};

struct average
{
    int X;
    int Y;
    int counter;

    CvPoint pos;
    CvPoint old;
};

struct object
{
    parameters par;
    colours color;
    average avg;
    bool buttonDown;
};

#endif // ARKSTRUCTS_H_INCLUDED
