#ifndef MATHFUNCTIONAL_H_INCLUDED
#define MATHFUNCTIONAL_H_INCLUDED

const double Pi = 3.14159265359;

inline double rad( double deg )
{
    return deg * Pi / 180;
}

inline double deg( double rad )
{
    return rad * 180 / Pi;
}



inline double mod( Point pos ) { return sqrt( pow( pos.x, 2 ) + pow( pos.y, 2 ) ); }

/**@field
  *A______________________B
  *|                     |
  *|           *O        |
  *|                     |
  *|_____________________|C
  *D
  */

function<Point2d( Point pos )> metrical;
function<Point2d( Point pos )> metrical_generator( Point fieldPointA, Point fieldPointC ) {

    double Height = 0.0,
           visionAngle = 90,
           fieldBorder = 1;

    Point cam = imgSize;
    Point center = cam / 2;

    double density = visionAngle / mod( cam );


    Point fieldPointB = Point( fieldPointC.x, fieldPointA.y ),
          fieldPointD = Point( fieldPointA.x, fieldPointC.y );

    double AO = mod( fieldPointA - center ) / mod( fieldPointA - fieldPointD ) * fieldBorder,
           BO = mod( fieldPointB - center ) / mod( fieldPointB - fieldPointA ) * fieldBorder,
           CO = mod( fieldPointC - center ) / mod( fieldPointC - fieldPointB ) * fieldBorder,
           DO = mod( fieldPointD - center ) / mod( fieldPointD - fieldPointC ) * fieldBorder;

	cout << AO << endl << BO << endl << CO << endl << DO << endl << "CENTERS" << endl << endl;

    double deltaPix = mod( fieldPointA - center );
    double angle = deltaPix * density;
    cout << AO / tan( rad( angle ) ) << endl ;

    deltaPix = mod( fieldPointB - center );
    angle = deltaPix * density;
    cout << BO / tan( rad( angle ) ) << endl;

    deltaPix = mod( fieldPointC - center );
    angle = deltaPix * density;
    cout << CO / tan( rad( angle ) ) << endl;

    deltaPix = mod( fieldPointD - center );
    angle = deltaPix * density;
    cout << DO / tan( rad( angle ) ) << endl << endl;


    return [=]( Point pos ) -> Point2d {
        pos.x -= cam.x / 2;
        pos.y = cam.y / 2 - pos.y;

        cout << pos << '\t';

        double deltaPix = mod( pos );

        double angle = deltaPix * density;
        double deltaMetric = Height * tan( rad( angle ) );

        cout << angle << '\t' << deltaMetric << '\t';

        return Point2d( deltaMetric * pos.x / deltaPix, deltaMetric * pos.y / deltaPix );
    };

};

#endif // MATHFUNCTIONAL_H_INCLUDED
