#include "Zero_K.h"

int main()
{
	GameObject ball;
	ArduinoBot robot(1);

	RoboEye vision(robot, ball, 0);

	while (true)
	{
		vision.makeImage();

		robot.refuse();
		ball.refuse();

		for (int w = vision.getBegin().x; w < vision.getEnd().x; w++)
		{
			for (int h = vision.getBegin().y; h < vision.getEnd().y; h++)
			{
				Color pixel = vision.readHSV(Point(w, h));

				if (vision.pixelCompare(robot, pixel))
				{
					robot.addpixel(w, h);
					vision.writeRGB(Point(w, h), Color(150, 20, 120));
				}

				if (vision.pixelCompare(ball, pixel))
				{
					ball.addpixel(w, h);
					vision.writeRGB(Point(w, h), Color(150, 100, 0));
				}

			}
		}

		robot.detect();
		ball.detect();

		vision.centerMarking();

		vision.showBorders();

		if (robot.getMode())
		{
			if (KEY_DOWN(VK_LEFT))
				robot.move(100);
			else if (KEY_DOWN(VK_RIGHT))
				robot.move(-100);
			else if (KEY_DOWN(VK_UP))
				robot.kick();
			else
				robot.move(0);
		}

		else
		{
			if (robot.getCounter() > robot.CONTROL_VALUES.selfMinPixels && ball.getCounter() > robot.CONTROL_VALUES.ballMinPixels) {

				Point2d difference = robot.getPosition() - ball.getPosition();

				if (abs(difference.y) < robot.CONTROL_VALUES.kickRange)
					robot.kick();

				int speed = difference.x * robot.RIDE_COEFFS.prop
					+ pow( difference.x, 3 ) * robot.RIDE_COEFFS.cube
					+ robot.CONTROL_VALUES.integralValue * robot.RIDE_COEFFS.integral;

				if (robot.CONTROL_VALUES.integralValue * difference.x < 0)
					robot.CONTROL_VALUES.integralValue += difference.x;
				
			}
		}

		vision.updateWindow();

		char key = waitKey(5);

		if (key == VK_ESCAPE)
			break;

		if (key == 'h')
			vision.switchHL();

		if (key == 'c')
			vision.switchMarking();

		if (key == 'b')
			vision.switchBordersVisible();

		if (key == 'a')
			robot.switchMode();

		if (key == 't')
			robot.switchDirection();

		if (key == 'g')
			vision.tie_metrical();

		if (key == 'f')
			vision.metricalTransform(Point(20, 20));

		if (key == 'r') {
			robot.setColor(Color(-999, -999, -999));
			ball.setColor(Color(-999, -999, -999));
		}


	}

	return 0;
}
