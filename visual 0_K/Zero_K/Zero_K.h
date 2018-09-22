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

inline int KEY_DOWN( int key ){
    return GetAsyncKeyState( key ) & 0x8000;
}

using Color = Vec3b;

#include "Game.h"
#include "Globals.h"
#include "mathFunctional.h"
#include "Functional.h"
#include "Camera.h"

