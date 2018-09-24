#pragma once

#include "Zero_K_LIBS.h"
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

class RoboEye : public Camera {
private:
	Point imgSize,
		frameBegin,
		frameEnd;

	struct {
		int hueDiff = 20;
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
		showFrame = true;

	Mat RGBimage,
		HSVimage;

public:
	RoboEye(int port, Point size = Point( 320, 240 ) );
	~RoboEye();

	void makeImage();
	void showBorders();
	void centerMarking(GameObject &obj);
	void reloadWindow();

	void setMouseOnRGB( void (*callBack) (int event, int x, int y, int flags, void* userdata) );
	void makeControlBars(Arkanoid &robot);

	void switchHL();
	void switchMarking();
	void switchBordersVisible();

	Color readHSV(Point pos);
	void writeRGB(Point pos, Color color);

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
				differencial = "differencial";
		} CONTROL;
	} NAMES;

};

void barBack(int, void*); ///kostûëü