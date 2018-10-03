#pragma once

#include "Zero_K_Core.h"
#include "Game.h"


class Camera
{
protected:
	VideoCapture capture;
	Mat frame;

public:
	Camera(int port);
	~Camera();

	bool read();
};

class MathModule {
protected:
	function<Point2d(Point pos)> metrical;

public:
	const double Pi = 3.14159265359;

	double rad(double deg) {
		return deg * Pi / 180;
	}

	double deg(double rad) {
		return rad * 180 / Pi;
	}

	double mod(Point pos) {
		return sqrt(pow(pos.x, 2) + pow(pos.y, 2));
	}

	Point2d metricalTransform(Point pos) {
		return metrical(pos);
	}

	virtual void tie_metrical() = 0;
};

class RoboEye : public Camera, public MathModule {
private:
	Point imgSize,
		frameBegin,
		frameEnd;

	struct {
		int hueDiff = 6;
		struct {
			int saturation = 40,
				brigthness = 40;
		} MIN;

		struct {
			int saturation = 255,
				brigthness = 255;
		} MAX;
	} HSV;

	bool highLightning = false,
		markCenter = false,
		showFrame = true,
		findCorners = true,
		showCorners = true;

	Mat RGBimage,
		HSVimage,
		Gray,
		mask;

	vector <Point2f> corners;

	const int max_corners = 50;

	struct {
		struct {
			const string name = "rgb";
		} RGBIMG;

		struct {
			const string name = "hsv";
		} HSVIMG;

		struct {
			const string name = "color detecting";
			const string hue = "hue",
				saturation = "satur.",
				brightness = "bright.";
		} COLORS;

		struct {
			const string name = "borders";
			const string high = "high",
				left = "left",
				down = "down",
				right = "right";
		} BORDERS;

		struct {
			const string name = "robot control";
			const string proportional = "proportional",
				cubic = "cubic",
				integral = "integral",
				differencial = "differencial",
				minBall = "min ball pixels",
				kickRange = "kick range";
		} CONTROL;

		struct {
			const string name = "gray";
		} GRAY;
	} NAMES;

	GameObject &ball;
	Arkanoid &robot;

public:
	RoboEye( Arkanoid &robot, GameObject &ball, 
		int port, Point size = Point( 640, 480 ) );
	~RoboEye();

	bool makeImage();
	void showBorders();
	void centerMarking();
	void updateWindow();

	void makeControlBars();

	void switchHL();
	void switchMarking();
	void switchBordersVisible();
	void switchCornerFinding();
	void switchCornerVisible();

	bool getFindMode();

	Color readHSV(Point pos);
	void writeRGB(Point pos, Color color);

	bool pixelCompare(GameObject &obj, Color pixel);
	void createMouseCallBack();

	Point getBegin();
	Point getEnd();

	Arkanoid& getRobotReference();
	GameObject& getBallReference();

	void tie_metrical();

	Point findRobot();
};

void barBack(int, void*);