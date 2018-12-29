#pragma once

#include "Zero_K_Core.h"

class GameObject {
private:
    Color objColor;

    Point pos;
    Point oldPos;

    int pixelCounter;

public:
	void refuse();
	void addpixel(int x, int y);
	void detect();

	int getCounter();
	Point getPosition();

	Color getColor();
	void setColor(Color objColor);

	Point getOld();
	void setOld(Point old);
};



class Arkanoid : public GameObject {
private:
    time_t kickTimer;
    bool handControl = true;


protected:
    virtual void hammerMove( int speed ) = 0;
    int DIRECTION = 1;

public:
    struct {
        int prop = 0;
        int cube = 0;
        int integral = 0;
        int differencial = 0;
    } RIDE_COEFFS;

	struct {
		int kickRange;
		int ballMinPixels;
		int selfMinPixels;

		int integralValue;
		int oldDifference;
	} CONTROL_VALUES;

    virtual void move( int speed ) = 0;
    virtual void connect( int port ) = 0;

	void kick();

	void switchMode();
	bool getMode();

	void switchDirection();
};



class LegoBot : public Arkanoid {
private:
	EV3 controller;
	vector<int> kickMotors;

protected:
	void hammerMove(int speed) override;

public:
	LegoBot(int Lmotor, int Rmotor, int kickMotor, int helpMotor = NULL);

	void move(int speed) override;

	void connect(int port) override;
};


class ArduinoBot : public Arkanoid {
private:
    winController controller;

protected:
	void hammerMove(int speed) override;

public:
	ArduinoBot(int port);

	void move(int speed) override;

	void connect(int port) override;
};
