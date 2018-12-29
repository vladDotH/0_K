#include "arkFuncs.h"

int main ()
{
    system ( "color 02 " );

    LbuttonDown = false;
    RbuttonDown = false;

    CvCapture *capture = cvCreateCameraCapture(0);
    IplImage *image = cvCreateImage( cvSize( WIDTH, HEIGHT ),IPL_DEPTH_8U,3);

    cvNamedWindow ("Camera Vision" );

    cvSetMouseCallback("Camera Vision", mouseFunc, NULL);

    cvNamedWindow( "Color Trackers : ", CV_WINDOW_FREERATIO );
    cvNamedWindow( "Size" );
    cvNamedWindow( "Arkanoid" );

    cvCreateTrackbar( "Radius", "Color Trackers : ", &Radius, 10000, TrackCallback );
    cvCreateTrackbar( "Min Sum", "Color Trackers : ", &lowbord, 255 * 3, TrackCallback );
    cvCreateTrackbar( "Max sum", "Color Trackers : ", &highbord, 255 * 3, TrackCallback );

    cvCreateTrackbar( "Begin H", "Size", &beginH, HEIGHT, TrackCallback );
    cvCreateTrackbar( "Begin W", "Size", &beginW, WIDTH, TrackCallback );
    cvCreateTrackbar( "End H", "Size", &endH, HEIGHT, TrackCallback );
    cvCreateTrackbar( "End W", "Size", &endW, WIDTH, TrackCallback );

    cvCreateTrackbar( "Prop", "Arkanoid", &Pcoef, 100, TrackCallback );
    cvCreateTrackbar( "Cubic", "Arkanoid", &Ccoef, 10, TrackCallback );

    char cvKey = -1;

//    arkBot.connect(4);

    while( cvKey != VK_RETURN )
    {
        IplImage *cam = cvQueryFrame( capture );
        cvResize( cam, image );

        if ( LbuttonDown == true )
        {
            ark.color = readPixel( image, ark.par.x, ark.par.y );
            LbuttonDown = false;
        }

        if ( RbuttonDown == true )
        {
            ball.color = readPixel( image, ball.par.x, ball.par.y );
            RbuttonDown = false;
        }

        ark.avg.X = 0;
        ark.avg.Y = 0;
        ark.avg.counter = 0;

        ball.avg.X = 0;
        ball.avg.Y = 0;
        ball.avg.counter = 0;


        for ( int H = beginH; H < endH; H++ )
        {
            for ( int W = beginW; W < endW; W++ )
            {
                colours pixel = readPixel( image, W, H );

                if ( eqPix( ark.color, pixel, Radius, lowbord, highbord ) )
                {
                    ark.avg.X += W;
                    ark.avg.Y += H;
                    ark.avg.counter ++;

                    if ( findcolor )
                        writePixel( image, W, H, 0, 250, 0 );
                }

                else if ( eqPix( ball.color, pixel, Radius, lowbord, highbord ) )
                {
                    ball.avg.X += W;
                    ball.avg.Y += H;
                    ball.avg.counter ++;

                    if ( findcolor )
                        writePixel( image, W, H, 250, 0, 0 );
                }
            }
        }

        if ( ark.avg.counter == 0 )
            ark.avg.counter ++;

        if ( ball.avg.counter == 0 )
            ball.avg.counter ++;

        ark.avg.pos.x = ark.avg.X / ark.avg.counter;
        ark.avg.pos.y = ark.avg.Y / ark.avg.counter;

        ball.avg.pos.x = ball.avg.X / ball.avg.counter;
        ball.avg.pos.y = ball.avg.Y / ball.avg.counter;

        if ( colorcent )
        {
            cvCircle( image, ark.avg.pos, 6, cvScalar( 255, 0, 0 ), -1 );
            cvCircle( image, ball.avg.pos, 6, cvScalar( 28, 128, 222 ), -1 );
        }

        if ( borders )
        {
            cvLine( image, cvPoint( beginW, beginH ), cvPoint( endW, beginH ), cvScalar(0,0,0) );
            cvLine( image, cvPoint( beginW, beginH ), cvPoint( beginW, endH ), cvScalar(0,0,0) );
            cvLine( image, cvPoint( endW, beginH ), cvPoint( endW, endH ), cvScalar(0,0,0) );
            cvLine( image, cvPoint( beginW, endH ), cvPoint( endW, endH ), cvScalar(0,0,0) );
        }

        if ( predict )
        {
            if ( clock() - prTime > 300 )
            {
//                cvLine( image, ball.avg.old, ball.avg.pos, cvScalar( 0, 0, 0 ), 9 );

                if ( abs ( ball.avg.old.x - ball.avg.pos.x ) > 30 || ball.avg.old.y - ball.avg.pos.y < -5 )
                {

                    double k = (double)( ball.avg.old.y - ball.avg.pos.y ) / (double)( ball.avg.old.x - ball.avg.pos.x );

                    if ( (double)( ball.avg.old.x - ball.avg.pos.x ) == 0 )
                        k = 99;

                    double b = (double)ball.avg.pos.y - (double)ball.avg.pos.x * k;

                    prediction.avg.pos = cvPoint( WIDTH, WIDTH * k + b );
                    prediction.avg.old = cvPoint( 0, b );

                }

                ball.avg.old = ball.avg.pos;

                prTime = clock();

            }
            cvLine( image, prediction.avg.pos, prediction.avg.old, cvScalar( 255, 255, 255 ), 6 );
        }


        if ( !automate )
        {
            if ( KEY_DOWN( VK_RIGHT ) )
            {
//                arkBot.motors(100, -100);
            }

            else if ( KEY_DOWN( VK_LEFT ) )
            {
//                arkBot.motors(-100, 100);
            }

            else
            {
//                arkBot.motors(0, 0);
            }

            if ( KEY_DOWN(VK_UP) )
            {
                //kick();
            }
        }

        else
        {
            float difX = ark.avg.pos.x - ball.avg.pos.x;
            float difY = ark.avg.pos.y - ball.avg.pos.y;

            float prop = Pcoef / 100;
            float cube = Ccoef / 1000;

            float speed = difX * prop + cube * ( difX * difX * difX );

//            arkBot.motors( speed * turn, speed * ( -turn ) );

            if ( abs( difY ) < 10 )
                kick();

        }


        cvShowImage( "Camera Vision", image );


        cvKey = cvWaitKey( 5 );

        if ( cvKey != -1 )
        {
            if ( cvKey == VK_SPACE )
                findcolor = !findcolor;

            if ( cvKey == 'c' )
                colorcent = !colorcent;

            if ( cvKey == 'p' )
                predict = !predict;

            if ( cvKey == 'a' )
                automate = !automate;

            if ( cvKey == 'b' )
                borders = !borders;

            if ( cvKey == 't' )
                turn = -turn;
        }

    }

    cvReleaseImage( &image );
    cvReleaseCapture(&capture);
    cvDestroyAllWindows();

    return 0;
}
