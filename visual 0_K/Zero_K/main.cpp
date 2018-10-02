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

		vision.findRobot();

		for (int w = vision.getBegin().x; w < vision.getEnd().x; w++)
		{
			for (int h = vision.getBegin().y; h < vision.getEnd().y; h++)
			{
				Color pixel = vision.readHSV(Point(w, h));

				if ( !vision.getFindMode() && vision.pixelCompare(robot, pixel))
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

		if (key == 'm')
			vision.switchCornerFinding();


	}

	return 0;
}

//#include <opencv2/opencv.hpp>
//using namespace std;
//using namespace cv;
//
//int main()
//{
//	Mat image, grey, mask;
//	VideoCapture cap(0);
//	vector<Point2f> corners;
//
//	while (true) {
//
//		cap.read(image);
//
//		cvtColor(image, grey, CV_BGR2GRAY);
//
//		goodFeaturesToTrack(grey, corners, 50, 0.01, 3, mask, true, 0.04);
//
//		for (int i = 0; i < corners.size(); i++) {
//			circle(grey, corners[i], 5, Scalar(0), -1);
//		}
//
//		imshow("win", grey);
//
//		if (waitKey(1) != -1) break;
//	}
//}

//#include <opencv2/opencv.hpp>
//
//using namespace cv;
//using namespace std;
//int main()
//{
//	VideoCapture cap(0);
//	vector <Point2f> corners;
//	Mat image, grey, mask;
//
//	while (true)
//	{
//		cap.read(image);
//
//		cvtColor(image, grey, CV_BGR2GRAY);
//
//		//goodFeaturesToTrack(grey, corners, 50, 0.01, 3, mask, 3, true, 0.04);
//		goodFeaturesToTrack(grey, corners, 50, 0.01, 3, mask, 3, true, 0.04);
//
//		for (int i = 0; i < corners.size(); i++)
//			cv::circle(grey, corners[i], 5, cv::Scalar(255), -1);
//
//		imshow("win", grey);
//
//		if (waitKey(1) != -1)
//			break;
//	}
//}