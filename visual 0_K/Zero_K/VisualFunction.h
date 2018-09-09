#pragma once

#include <opencv2/opencv.hpp>
#include <map>
#include <cmath>

using namespace cv;
using namespace std;

using Color = Vec3b;

class VisualFunction
{
private:
	Mat image;
	map<int, int> dotsMap;

	bool buttonPressed;
	Point oldCursorPoint;

	void connectDots();
	Point pol2Dec(Point dot);
	Point dec2Pol(Point dot);

public:
	~VisualFunction() = default;
	VisualFunction( CvSize size = CvSize( 500, 500 ) );

	void MouseCallBack(int event, int x, int y, int flags, void*);
	
	double operator() ( int percent );
	Mat& getImage();

	static void getCallBack(int event, int x, int y, int flags, void* objPtr);
};
