#include "Game.h"

void GameObject::refuse() {
	pos = Point(0, 0);
	pixelCounter = 0;
}

void GameObject::addpixel(int x, int y) {
	pos.x += x;
	pos.y += y;
	pixelCounter++;
}

void GameObject::detect() {
	if (pixelCounter != 0) {
		pos.x /= pixelCounter;
		pos.y /= pixelCounter;
	}
}

int GameObject::getCounter() {
	return pixelCounter;
}

Point GameObject::getPosition() {
	return pos;
}

Color GameObject::getColor() {
	return objColor;
}

void GameObject::setColor(Color objColor) {
	this->objColor = objColor;
}

Point GameObject::getOld() {
	return oldPos;
}

void GameObject::setOld(Point old) {
	oldPos = old;
}



void Arkanoid::kick() {
	if (clock() - kickTimer > 500)
		thread([this]() {

		kickTimer = clock();

		hammerMove(-100);
		this_thread::sleep_for(chrono::milliseconds(200));

		hammerMove(100);
		this_thread::sleep_for(chrono::milliseconds(250));

		hammerMove(0);

	}).detach();
}

void Arkanoid::switchMode() {
	handControl = !handControl;
}

bool Arkanoid::getMode() {
	return handControl;
}

void Arkanoid::switchDirection() {
	DIRECTION = -DIRECTION;
}



void LegoBot::hammerMove(int speed) {
	for (int motor : kickMotors) {
		controller.outputSpeed(motor, speed);
		controller.outputStart(motor);
	}
}

LegoBot::LegoBot(int Lmotor, int Rmotor, int kickMotor, int helpMotor) : controller(EV3(Lmotor, Rmotor)) {
	kickMotors.push_back(kickMotor);

	if (helpMotor)
		kickMotors.push_back(helpMotor);
}

void LegoBot::move(int speed) {
	controller.motors(speed * DIRECTION, speed * DIRECTION);
}

void LegoBot::connect(int port) {
	controller.connect(port);
}



void ArduinoBot::hammerMove(int speed) {
	controller.motorStart(B, speed);
}

ArduinoBot::ArduinoBot(int port) : controller(winController(port)) { }

void ArduinoBot::move(int speed) {
	controller.motorStart(A, speed * DIRECTION);
}

void ArduinoBot::connect(int port) { }