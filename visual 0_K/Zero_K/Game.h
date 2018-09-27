#pragma once

class GameObject {
private:
    Color objColor;

    Point pos;
    Point oldPos;

    int pixelCounter;

public:
    void refuse() {
        pos = Point( 0, 0 );
        pixelCounter = 0;
    }

    void addpixel( int x, int y ) {
        pos.x += x;
        pos.y += y;
        pixelCounter ++;
    }

    void detect() {
		if (pixelCounter != 0) {
            pos.x /= pixelCounter;
			pos.y /= pixelCounter;
		}
    }

    int getCounter() {
        return pixelCounter;
    }

    Point getPosition() {
        return pos;
    }

    Color getColor() {
        return objColor;
    }

    void setColor( Color objColor ) {
        this -> objColor = objColor;
    }

    Point getOld() {
        return oldPos;
    }

    void setOld( Point old ) {
        oldPos = old;
    }
};



class Arkanoid : public GameObject {
private:
    time_t kickTimer;
    bool handControl;


protected:
    virtual void hammerMove( int speed ) { };
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

    virtual void move( int speed ) { };
    virtual void connect( int port ) { };

    void kick() {
        if( clock() - kickTimer > 500 )
            thread( [this]() {

            kickTimer = clock();

            hammerMove( 100 );
            this_thread::sleep_for( chrono::milliseconds( 200 ) );

            hammerMove( -100 );
            this_thread::sleep_for( chrono::milliseconds( 250 ) );

			hammerMove(0);

        } ).detach();
    }

    void switchMode() {
        handControl = !handControl;
    }

    bool getMode() {
        return handControl;
    }

    void switchDirection() {
        DIRECTION = -DIRECTION;
    }
};



class LegoBot : public Arkanoid {
private:
    EV3 controller;
    vector<int> kickMotors;

protected:
    void hammerMove( int speed ) override {
        for( int motor : kickMotors ) {
//            controller.outputSpeed( motor, speed );
//            controller.outputStart( motor );
        }
    }

public:
    LegoBot( int Lmotor, int Rmotor, int kickMotor, int helpMotor = NULL ) :
        controller( EV3( Lmotor, Rmotor ) ) {
        kickMotors.push_back( kickMotor );

        if( helpMotor )
            kickMotors.push_back( helpMotor );
    }

    void move( int speed ) override {
//        controller.motors( Lspeed * DIRECTION, Rspeed * DIRECTION );
    }

    void connect( int port ) override {
        controller.connect( port );
    }
};



class ArduinoBot : public Arkanoid {
private:
//    winController controller;

protected:
    void hammerMove( int speed ) override {
//        controller.motorStart( A, speed );
    }

public:
    ArduinoBot( int port ) /*: controller( winController( port ) ) */ { }

    void move( int speed ) override {
//        controller.motorStart( B, speed * DIRECTION );
    }

    void connect() = delete;
};
