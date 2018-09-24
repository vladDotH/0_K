#pragma once

#include <Windows.h>
#include "opencv2/opencv.hpp"
#include <iostream>
#include <cmath>
#include <chrono>
#include <thread>
#include <functional>

#include "Lego/EV3.hpp"
#include "Arduino/wincom.h"

using namespace cv;
using namespace std;

using Color = Vec3b;