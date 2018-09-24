#include "VisualFunction.h"

void VisualFunction::MouseCallBack(int event, int x, int y, int flags, void * userdata)
{
	if (event == EVENT_LBUTTONDOWN || ( event == EVENT_MOUSEMOVE && buttonPressed ) ) {
		buttonPressed = true;
		
		x = (int) round( (double) x / 5 ) * 5;
		y = (int) round( (double) y / 5 ) * 5;
		
		dotsMap[x] = y;

		image = Vec3b(255, 255, 255);
		
		connectDots();
		
		oldCursorPoint = Point(x, y);
	}

	if (event == EVENT_LBUTTONUP)
		buttonPressed = false;

	if (event == EVENT_RBUTTONDOWN) {
		
		dotsMap.clear();
		image = Color(255, 255, 255);

		dotsMap[0] = image.rows;
		dotsMap[image.cols] = 0;

		connectDots();
	}

	if (event == EVENT_RBUTTONDBLCLK) {
		dotsMap.clear();
		image = Color(255, 255, 255);
		connectDots();
	}
}

double VisualFunction::operator()( int percent )
{
	//int cordX = round( image.cols * percent / 100 );

	//pair<int, int> error( 0, cordX ); // 1 - x; 2 - err
	//
	//for (auto dot : dotsMap) {
	//	int currentErr = abs(dot.second - cordX);
	//	if (currentErr < error.second)
	//		error = pair<int,int>(dot.first, currentErr);
	//}

	//cout << "DGG: " << error.first << "\t" << dotsMap[error.first] << endl;

	//circle(image, Point(error.first, dotsMap[error.first]), 7, Scalar(255, 0, 0));
	//return dotsMap[error.first] / image.rows * 100;

	int cordX = round(image.cols * percent / 100);

	for (int y = 0; y < image.rows; y++) {
		if (image.at<Color>(Point(cordX, y)) == Color(0, 0, 0) ) {
			 
			circle(image, Point(cordX, y), 5, Color(255, 0, 0), 1);

			return (double) pol2Dec( Point(cordX, y) ).y / image.rows * 100;
		}
	}
}

Mat & VisualFunction::getImage()
{
	return image;
}

void VisualFunction::connectDots()
{
	pair<int, int> oldDot;
	int counter = 0;
	for (auto dot : dotsMap) {
		circle(image, Point(dot.first, dot.second), 2, Scalar(0, 0, 0), -1);

		if (counter != 0)
			line(image, Point(dot.first, dot.second), Point(oldDot.first, oldDot.second), Scalar(0, 0, 0), 2);
		oldDot = dot;
		counter++;
	}
}

Point VisualFunction::pol2Dec(Point dot)
{
	return Point(dot.x, image.rows - dot.y);
}

Point VisualFunction::dec2Pol(Point dot)
{
	return Point(dot.x, abs ( -image.rows + dot.y ));
}

VisualFunction::VisualFunction(CvSize size)
{
	image = Mat(size, CV_8UC3 );
	image = Color(255, 255, 255);


	dotsMap[0] = image.rows;
	dotsMap[image.cols] = 0;
	
	connectDots();
}

void VisualFunction::getCallBack(int event, int x, int y, int flags, void* objPtr) {
	VisualFunction* ptr = (VisualFunction*)objPtr;
	ptr->MouseCallBack(event, x, y, flags, 0);
}