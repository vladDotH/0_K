#include "Camera.h"

Camera::Camera(int port)
{
	capture = VideoCapture(port);
}

Camera::~Camera()
{
	frame.release();
	capture.release();
}

bool Camera::read()
{
	if (capture.isOpened())
		return capture.read(frame);
}



RoboEye::RoboEye(int port, Point size ) : Camera(port) 
{
	imgSize = size;
	frameBegin = Point(0, 0);
	frameEnd = imgSize;

	namedWindow(NAMES.RGBIMG.name);
	namedWindow(NAMES.HSVIMG.name);
	namedWindow(NAMES.CONTROL.name);

	namedWindow(NAMES.COLORS.name);
	createTrackbar(NAMES.COLORS.hue, NAMES.COLORS.name, &HSV.hueDiff, 255, barBack);
	createTrackbar(NAMES.COLORS.saturation, NAMES.COLORS.name, &HSV.MIN.saturation, 255, barBack);
	createTrackbar(NAMES.COLORS.brightness, NAMES.COLORS.name, &HSV.MIN.brigthness, 255, barBack);

	namedWindow(NAMES.BORDERS.name);
	createTrackbar(NAMES.BORDERS.high, NAMES.BORDERS.name, &frameBegin.y, imgSize.y, barBack);
	createTrackbar(NAMES.BORDERS.left, NAMES.BORDERS.name, &frameBegin.x, imgSize.x, barBack);
	createTrackbar(NAMES.BORDERS.down, NAMES.BORDERS.name, &frameEnd.y, imgSize.y, barBack);
	createTrackbar(NAMES.BORDERS.right, NAMES.BORDERS.name, &frameEnd.x, imgSize.x, barBack);
}

RoboEye::~RoboEye()
{
	RGBimage.release();
	HSVimage.release();
}

void RoboEye::makeImage()
{
	if (!read())
		return;

	resize(frame, RGBimage, imgSize);

	GaussianBlur(RGBimage, RGBimage, Size(5, 5), 0);
	cvtColor(RGBimage, HSVimage, CV_BGR2HSV);
}

void RoboEye::showBorders()
{
	if( showFrame )
		rectangle(RGBimage, frameBegin, frameEnd, Scalar(0, 0, 150), 3);
}

void RoboEye::centerMarking(GameObject &obj)
{
	if( markCenter )
		circle(RGBimage, obj.getPosition(), 6, Scalar( obj.getColor() ), -1);
}

void RoboEye::reloadWindow()
{
	imshow(NAMES.RGBIMG.name, RGBimage);
	imshow(NAMES.HSVIMG.name, HSVimage);
}

void RoboEye::setMouseOnRGB( void (*callBack) (int event, int x, int y, int flags, void *userdata))
{
	setMouseCallback(NAMES.RGBIMG.name, callBack);
}

void RoboEye::makeControlBars(Arkanoid & robot)
{
	createTrackbar(NAMES.CONTROL.proportional, NAMES.CONTROL.name, &robot.RIDE_COEFFS.prop, 100, barBack);
	createTrackbar(NAMES.CONTROL.cubic, NAMES.CONTROL.name, &robot.RIDE_COEFFS.cube, 100, barBack);
	createTrackbar(NAMES.CONTROL.integral, NAMES.CONTROL.name, &robot.RIDE_COEFFS.integral, 100, barBack);
	createTrackbar(NAMES.CONTROL.differencial, NAMES.CONTROL.name, &robot.RIDE_COEFFS.differencial, 100, barBack);
}

void RoboEye::switchHL()
{
	highLightning = !highLightning;
}

void RoboEye::switchMarking()
{
	markCenter = !markCenter;
}

void RoboEye::switchBordersVisible()
{
	showFrame = !showFrame;
}

Color RoboEye::readHSV(Point pos)
{
	return HSVimage.at<Color>( pos );
}

void RoboEye::writeRGB(Point pos, Color color)
{
	RGBimage.at<Color>(pos) = color;
}
