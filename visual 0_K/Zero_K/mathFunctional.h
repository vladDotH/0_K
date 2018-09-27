#pragma once
//
//class MathModule {
//public:
//	const double Pi = 3.14159265359;
//
//	double rad(double deg){
//		return deg * Pi / 180;
//	}
//
//	double deg(double rad){
//		return rad * 180 / Pi;
//	}
//	
//	double mod(Point pos) {
//		return sqrt(pow(pos.x, 2) + pow(pos.y, 2)); 
//	}
//
//	function<Point2d(Point pos)> metrical;
//
//	virtual void tie_metrical();
//};
//
//
//




/**@field
  *A______________________B
  *|                     |
  *|           *O        |
  *|                     |
  *|_____________________|C
  *D
  */

  //function<Point2d( Point pos )> metrical;
  //void tie_metrical( function<point2d(point pos)>& function ) {
  //
  //	thread( [&]() {
  //		double height = 0.0,
  //			diameter = 0.0,
  //			visionangle = 90.0;
  //
  //		point cam = imgsize;
  //		point center = point( cam.x / 2, cam.y / 2 );
  //
  //		double density = visionangle / mod(cam);
  //
  //		cout << "enter the diameter of frame: ";
  //		cin >> diameter;
  //
  //		double radius = diameter / 2;
  //
  //		height = radius / tan(rad(visionangle / 2));
  //
  //		function = [=](point pos) -> point2d {
  //			pos.x -= cam.x / 2;
  //			pos.y = cam.y / 2 - pos.y;
  //
  //			cout << pos << '\t';
  //
  //			double deltapix = mod(pos);
  //
  //			double angle = deltapix * density;
  //			double deltametric = height * tan(rad(angle));
  //
  //			cout << angle << '\t' << deltametric << '\t';
  //
  //			return point2d(deltametric * pos.x / deltapix, deltametric * pos.y / deltapix);
  //		};
  //
  //	} ).detach();
  //
  //};

