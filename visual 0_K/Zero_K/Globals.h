#pragma once


Point imgSize( 640 / 2, 480 / 2 ),

      frameBegin( 0, 0 ),
      frameEnd( imgSize.x, imgSize.y );

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

Mat frame,
	RGBimage,
	HSVimage;

GameObject ball;
Arkanoid robot = ArduinoBot( 1 );
