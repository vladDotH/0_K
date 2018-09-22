#pragma once

Color read( Mat img, Point pos )
{
    return img . at<Color>( pos );
}

void write( Mat img, Point pos, Color bgrColor )
{
    img . at<Color>( pos ) = bgrColor;
}

///@HSV
bool eqPix( Color target, Color current, int hue, int minSat, int minVal )
{
    return abs( target[0] - current[0] ) < hue &&
           current[1] >= minSat &&
           current[2] >= minVal;
}



void MainCallBack( int event, int x, int y, int flags, void* userdata )
{
    switch( event )
    {
    case EVENT_LBUTTONDOWN:
        robot.setColor( read( HSVimage, Point( x, y ) ) );
        break;

    case EVENT_RBUTTONDOWN:
        ball.setColor( read( HSVimage, Point( x, y ) ) );
        break;
    }
}

void barBack( int, void* ) { }

