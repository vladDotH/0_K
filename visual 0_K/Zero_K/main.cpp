#include "Zero_K.h"

int main()
{
	GameObject ball;
	ArduinoBot robot(8);

	RoboEye vision(robot, ball, 0, Point(320, 240));

	while (true)
	{
		if (!vision.makeImage())
			break;

		ball.refuse();
		robot.refuse();

		vision.findRobot();

		for (int w = vision.getBegin().x; w < vision.getEnd().x; w++)
		{
			for (int h = vision.getBegin().y; h < vision.getEnd().y; h++)
			{
				Color pixel = vision.readHSV(Point(w, h));

				if (!vision.getFindMode() && vision.pixelCompare(robot, pixel))
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

		if (!vision.getFindMode())
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
			if (ball.getCounter() > robot.CONTROL_VALUES.ballMinPixels)
				robot.move(0);
			else {
				if (!vision.getMetUsing()) {

					Point2d difference = robot.getPosition() - ball.getPosition();

					if (abs(difference.y) < robot.CONTROL_VALUES.kickRange)
						robot.kick();

					//cout << abs(difference.y) << "KICK R " << robot.CONTROL_VALUES.kickRange << endl;

					float speed = (float)difference.x * (float)robot.RIDE_COEFFS.prop / (float)100
						+ pow(difference.x, 3) * (float)robot.RIDE_COEFFS.cube / (float)1000
						+ (float)robot.CONTROL_VALUES.integralValue * (float)robot.RIDE_COEFFS.integral / (float)100;

					//cout << "R" << robot.getPosition() << " B" << ball.getPosition() << endl;

					robot.move(speed);

					if (robot.CONTROL_VALUES.integralValue * difference.x < 0)
						robot.CONTROL_VALUES.integralValue += difference.x;
				}
				else {
					Point2d difference = vision.metricalTransform(robot.getPosition()) - vision.metricalTransform(ball.getPosition());
					if (abs(difference.y) < robot.CONTROL_VALUES.kickRange)
						robot.kick();

					float speed = (float)difference.x * (float)robot.RIDE_COEFFS.prop / (float)100;

					robot.move(speed);
				}
			}
		}

		vision.updateWindow();

		char key = waitKey(5);

		if (key == VK_ESCAPE)
			break;

		else if (key == 'h')
			vision.switchHL();

		else if (key == 'c')
			vision.switchMarking();

		else if (key == 'b')
			vision.switchBordersVisible();

		else if (key == 'a')
			robot.switchMode();

		else if (key == 'd')
			robot.switchDirection();

		else if (key == 'g')
			vision.tie_metrical();

		else if (key == 'f')
			vision.metricalTransform(Point(20, 20));

		else if (key == 'r') {
			robot.setColor(Color(-999, -999, -999));
			ball.setColor(Color(-999, -999, -999));
		}

		else if (key == 'm')
			vision.switchCornerFinding();

		if (key == 'n')
			vision.switchCornerVisible();


	}

	return 0;
}
