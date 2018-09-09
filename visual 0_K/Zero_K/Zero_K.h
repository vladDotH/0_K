#ifndef ZERO_K_H_INCLUDED
#define ZERO_K_H_INCLUDED

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

#include "Classes.h"
#include "Globals.h"
#include "mathFunctional.h"
#include "Functional.h"

#endif // ZERO_K_H_INCLUDED
