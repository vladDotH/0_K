#pragma once

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
void tie_metrical( function<Point2d(Point pos)>& function ) {

	thread( [&]() {
		double Height = 0.0,
			diameter = 0.0,
			visionAngle = 90.0;

		Point cam = imgSize;
		Point center = cam / 2;

		double density = visionAngle / mod(cam);

		cout << "ENTER the DIAMETER of FRAME: ";
		cin >> diameter;

		double radius = diameter / 2;

		Height = radius / tan(rad(visionAngle / 2));

		function = [=](Point pos) -> Point2d {
			pos.x -= cam.x / 2;
			pos.y = cam.y / 2 - pos.y;

			cout << pos << '\t';

			double deltaPix = mod(pos);

			double angle = deltaPix * density;
			double deltaMetric = Height * tan(rad(angle));

			cout << angle << '\t' << deltaMetric << '\t';

			return Point2d(deltaMetric * pos.x / deltaPix, deltaMetric * pos.y / deltaPix);
		};

	} ).detach();

};

