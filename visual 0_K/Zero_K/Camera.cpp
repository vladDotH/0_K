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



RoboEye::RoboEye(Arkanoid &robot, GameObject &ball,
	int port, Point size) : Camera(port), ball(ball), robot(robot)
{
	imgSize = size;
	frameBegin = Point(0, 0);
	frameEnd = imgSize;

	mask = Mat(cvSize(imgSize.x, imgSize.y), CV_8U);

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

	makeControlBars();
	createMouseCallBack();
}

RoboEye::~RoboEye()
{
	RGBimage.release();
	HSVimage.release();
	capture.release();
}

bool RoboEye::makeImage()
{
	if (!read())
		return false;

	resize(frame, RGBimage, imgSize);

	GaussianBlur(RGBimage, RGBimage, Size(5, 5), 0);
	cvtColor(RGBimage, HSVimage, CV_BGR2HSV);

	return true;
}

void RoboEye::showBorders()
{
	if (showFrame)
		rectangle(RGBimage, frameBegin, frameEnd, Scalar(0, 0, 150), 3);
}

void RoboEye::centerMarking()
{
	if (markCenter) {
		circle(RGBimage, ball.getPosition(), 6, Scalar(ball.getColor()), -1);
		if (!findCorners)
			circle(RGBimage, robot.getPosition(), 6, Scalar(robot.getColor()), -1);
	}
}

void RoboEye::updateWindow()
{
	imshow(NAMES.RGBIMG.name, RGBimage);
	imshow(NAMES.HSVIMG.name, HSVimage);
}

void RoboEye::makeControlBars()
{
	createTrackbar(NAMES.CONTROL.proportional, NAMES.CONTROL.name, &robot.RIDE_COEFFS.prop, 500, barBack);
	createTrackbar(NAMES.CONTROL.cubic, NAMES.CONTROL.name, &robot.RIDE_COEFFS.cube, 100, barBack);
	createTrackbar(NAMES.CONTROL.integral, NAMES.CONTROL.name, &robot.RIDE_COEFFS.integral, 100, barBack);
	createTrackbar(NAMES.CONTROL.differencial, NAMES.CONTROL.name, &robot.RIDE_COEFFS.differencial, 100, barBack);
	createTrackbar(NAMES.CONTROL.kickRange, NAMES.CONTROL.name, &robot.CONTROL_VALUES.ballMinPixels, 50, barBack);
	createTrackbar(NAMES.CONTROL.minBall, NAMES.CONTROL.name, &robot.CONTROL_VALUES.kickRange, 50, barBack);
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

void RoboEye::switchCornerFinding()
{
	findCorners = !findCorners;
}

void RoboEye::switchCornerVisible()
{
	showCorners = !showCorners;
}

bool RoboEye::getFindMode()
{
	return findCorners;
}

Color RoboEye::readHSV(Point pos)
{
	return HSVimage.at<Color>(pos);
}

void RoboEye::writeRGB(Point pos, Color color)
{
	if (highLightning)
		RGBimage.at<Color>(pos) = color;
}

bool RoboEye::pixelCompare(GameObject & obj, Color pixel)
{
	return abs(obj.getColor()[0] - pixel[0]) < HSV.hueDiff &&
		pixel[1] >= HSV.MIN.saturation &&
		pixel[2] >= HSV.MIN.brigthness;
}

void RoboEye::createMouseCallBack()
{
	setMouseCallback(NAMES.RGBIMG.name,
		[](int event, int x, int y, int flags, void* userdata) {

		RoboEye* data = reinterpret_cast<RoboEye*>(userdata);

		switch (event)
		{
		case EVENT_LBUTTONDOWN:
			data->getRobotReference().setColor(data->readHSV(Point(x, y)));
			break;

		case EVENT_RBUTTONDOWN:
			data->getBallReference().setColor(data->readHSV(Point(x, y)));
			break;
		}
	}, (void*)this);
}

Point RoboEye::getBegin()
{
	return frameBegin;
}

Point RoboEye::getEnd()
{
	return frameEnd;
}

Arkanoid & RoboEye::getRobotReference()
{
	return robot;
}

GameObject & RoboEye::getBallReference()
{
	return ball;
}

void RoboEye::tie_metrical() {

	thread([this]() {
		double height = 0.0,
			diameter = 0.0,
			visionangle = 90.0;

		Point cam = imgSize;
		Point center = Point(cam.x / 2, cam.y / 2);

		double density = visionangle / mod(cam);

		cout << "enter the diameter of frame: ";
		cin >> diameter;

		double radius = diameter / 2;

		height = radius / tan(rad(visionangle / 2));

		metrical = [=](Point pos) -> Point2d {
			pos.x -= cam.x / 2;
			pos.y = cam.y / 2 - pos.y;

			cout << pos << '\t';

			double deltapix = mod(pos);

			double angle = deltapix * density;
			double deltametric = height * tan(rad(angle));

			cout << angle << '\t' << deltametric << '\t';

			return Point2d(deltametric * pos.x / deltapix, deltametric * pos.y / deltapix);
		};

	}).detach();

}

Point RoboEye::findRobot()
{
	if (!findCorners)
		return Point(0, 0);

	mask = Scalar(0);
	rectangle(mask, frameBegin, frameEnd, Scalar(255), -1);

	cvtColor(RGBimage, Gray, CV_BGR2GRAY);

	goodFeaturesToTrack(Gray, corners, max_corners, 0.01, 3, mask, 3, true, 0.04);

	robot.refuse();

	for (Point pos : corners) {
		if (pos.x > frameBegin.x && pos.x < frameEnd.x &&
			pos.y > frameBegin.y && pos.y < frameEnd.y)
		{
			robot.addpixel(pos.x, pos.y);
			
			if (showCorners)
				circle(RGBimage, pos, 5, Scalar(0, 0, 0), -1);
		}
	}

	robot.detect();

	if (markCenter)
		circle(RGBimage, robot.getPosition(), 9, Scalar(255, 0, 0), -1);

	return robot.getPosition();
}

void barBack(int, void*) { }